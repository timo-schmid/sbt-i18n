package ch.timo_schmid.i18n.parser

import atto._
import atto.Atto._
import atto.compat.stdlib._
import ch.timo_schmid.i18n.data.{Literal, Variable}
import org.specs2.Specification

class ParserSpec extends Specification { def is = s2"""

    Parse the key "foo"                           $testParseKeyFoo
    Parse the key "foo1"                          $testParseKeyFoo1
    Parse the key "foo.bar"                       $testParseKeyFooBar
    Parse the key "foo1.bar2"                     $testParseKeyFoo1Bar2

    Parse the value "The quick brown fox"         $testParseValueQuickBrownFox
    Parse the value "jumps over the lazy dog"     $testParseValueJumpsOverDog

    Parse a simple variable                       $testParseSimpleVariable
    Parse two simple variables                    $testParseTwoSimpleVariables
    Escape a variable using $$                    $testParseEscapeDollar

    """

  import TranslationFileParser._

  def testParseKeyFoo =
    parseKey.parseOnly("foo").either === Right("foo")

  def testParseKeyFoo1 =
    parseKey.parseOnly("foo1").either === Right("foo1")

  def testParseKeyFooBar =
    parseKey.parseOnly("foo.bar").either === Right("foo.bar")

  def testParseKeyFoo1Bar2 =
    parseKey.parseOnly("foo1.bar2").either === Right("foo1.bar2")

  def testParseValueQuickBrownFox =
    parseTranslationString.parseOnly(
      "The quick brown fox"
    ).either === Right(List(Literal("The quick brown fox")))

  def testParseValueJumpsOverDog =
    parseTranslationString.parseOnly(
      "jumps over the lazy dog"
    ).either === Right(List(Literal("jumps over the lazy dog")))

  def testParseSimpleVariable =
    parseTranslationString.parseOnly(
      "$foobar123"
    ).either === Right(List(Variable("foobar123")))

  def testParseTwoSimpleVariables =
    parseTranslationString.parseOnly(
      "$foo and $bar"
    ).either === Right(List(Variable("foo"), Literal(" and "), Variable("bar")))

  def testParseEscapeDollar =
    parseTranslationString.parseOnly(
      "$$"
    ).either === Right(List(Literal("$$")))

}
