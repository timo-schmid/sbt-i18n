package ch.timo_schmid.i18n.parser

import java.io.File

import atto._
import atto.Atto._
import atto.compat.stdlib._
import ch.timo_schmid.i18n.data._
import ch.timo_schmid.i18n.ops._

import scala.io.{Codec, Source}

class TranslationFileParser(onWeirdFileName: String => String) {

  import TranslationFileParser._

  def parseFile(file: File): TranslationSet =
    Source
      .fromFile(file)(Codec.UTF8)
      .getLines()
      .filterNot(_.trim.isEmpty)
      .filterNot(_.startsWith("#"))
      .map(line => parseLine.parseOnly(line).either match {
        case Left(l) => sys.error(l)
        case Right(r) => r
      })
      .foldLeft(TranslationSet(Nil))(append(getLang(file.getName)))

  private val FILE_NAME_SUFFIX = ".properties"

  private def getLang(fileName: String): String =
    if(fileName.endsWith(FILE_NAME_SUFFIX))
      fileName.substring(0, fileName.length - FILE_NAME_SUFFIX.length)
    else
      onWeirdFileName(fileName)

  private def append(lang: String)(set: TranslationSet, kv: (String, List[TranslationStringPart])): TranslationSet =
    set.append(TranslationString(lang, kv._1, kv._2))

}

object TranslationFileParser {

  private val dot: Parser[Char] = char('.') namedOpaque "dot"

  private val equal: Parser[Char] = char('=') namedOpaque "equal"

  private val dollar: Parser[Char] = char('$') namedOpaque "dollar"

  private val parseJavaIdentifierStart: Parser[Char] =
    satisfy(Character.isJavaIdentifierStart) namedOpaque "java identifier start"

  private val parseJavaIdentifierPart: Parser[Char] =
    satisfy(Character.isJavaIdentifierPart) namedOpaque "java identifier part"

  private val parseJavaIdentifier: Parser[String] =
    (for {
      firstChar <- parseJavaIdentifierStart
      rest <- many(parseJavaIdentifierPart)
    } yield firstChar + rest.mkString) namedOpaque "java identifier"

  private [parser] val parseKey: Parser[String] =
    for {
      _     <- skipWhitespace
      first <- parseJavaIdentifier
      rest  <- many(dot ~> parseJavaIdentifier)
      _     <- skipWhitespace
    } yield (first :: rest).mkString(".")

  private val parseEscapeDollar: Parser[TranslationStringPart] =
    for {
      _ <- dollar
      _ <- dollar
    } yield Literal("$$")

  private val parseVariable: Parser[TranslationStringPart] =
    for {
      _ <- dollar
      c <- parseJavaIdentifier
    } yield Variable(c)

  private val parseLiteral: Parser[TranslationStringPart] =
    many1(noneOf("$")).map(l => Literal((List(l._1) ++ l._2).mkString))

  private val parseTranslation: Parser[TranslationStringPart] =
    parseEscapeDollar |
    parseVariable |
    parseLiteral |
    err[TranslationStringPart]("Unable to parse a TranslationStringPart")

  private [parser] val parseTranslationString: Parser[List[TranslationStringPart]] =
    many(parseTranslation)

  private val parseLine: Parser[(String, List[TranslationStringPart])] =
    for {
      key               <- parseKey
      _                 <- equal
      translationString <- parseTranslationString
    } yield (key, translationString)

}
