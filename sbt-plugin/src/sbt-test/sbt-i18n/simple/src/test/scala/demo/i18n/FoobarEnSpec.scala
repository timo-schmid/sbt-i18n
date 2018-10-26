package demo.i18n

import org.specs2._

class FoobarEnSpec extends Specification { def is = s2"""

  This is the specification for the german language strings

  The code for "blah.blah" must exist                     $testBlahBlah
  The code for "foo" must exist                           $testFoo
  The code for "foo.bar" must not have an apply method    $testFooBar
  The code for "foo.bar.baz" must exist                   $testFooBarBaz

  Special cases:
  There can be multiple variables in a sentence           $testQuickBrownFox
  There can be named arguments                            $testQuickBrownFoxNamed
  There can be the same variable twice                    $testVariableTwice
  There can be a dollar sign, by escaping it with $$$$    $testEscapeDollar

  """

  implicit val en: Language = Language("en_US")

  private def testBlahBlah =
    blah.blah() must equalTo("blah blah en")

  private def testFoo =
    foo() must equalTo("foo en")

  private def testFooBar =
    foo.bar.getClass.getDeclaredMethods must equalTo(Array())

  private def testFooBarBaz =
    foo.bar.baz() must equalTo("foo.bar.baz en")

  private def testQuickBrownFox =
    quick_brown_fox("quick", "brown", "fox", "lazy", "dog") === "The quick brown fox jumps over the lazy dog"

  private def testQuickBrownFoxNamed =
    quick_brown_fox(
      adjective1 = "quick",
      color = "brown",
      animal1 = "fox",
      adjective2 = "lazy",
      animal2 = "dog"
    ) === "The quick brown fox jumps over the lazy dog"

  private def testVariableTwice =
    same_variable_twice("ice", "baby") === "ice ice baby"

  private def testEscapeDollar =
    escape_dollar() === "$"

}
