package ch.timo_schmid.sbt.i18n.parser

import atto._
import Atto._
import atto.compat.stdlib._
import ch.timo_schmid.sbt.i18n.data._
import ch.timo_schmid.sbt.i18n.ops._
import java.io.File
import scala.io.Source

class TranslationFileParser(onWeirdFileName: String => String) {

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

  private val parseKeySegment: Parser[String] =
    for {
      key <- many1(letterOrDigit)
    } yield (List(key._1) ++ key._2).mkString

  private val parseKey: Parser[String] =
    for {
      _     <- skipWhitespace
      first <- parseKeySegment
      rest  <- many(dot ~> parseKeySegment)
      _     <- skipWhitespace
    } yield (List(first) ++ rest).mkString(".")

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

  private val parseTranslationString: Parser[List[TranslationStringPart]] =
    many(parseTranslation)

  private val parseLine: Parser[(String, List[TranslationStringPart])] =
    for {
      key               <- parseKey
      _                 <- equal
      translationString <- parseTranslationString
    } yield (key, translationString)

  def parseFile(file: File): TranslationSet =
    Source
      .fromFile(file)
      .getLines()
      .filterNot(_.isEmpty)
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
