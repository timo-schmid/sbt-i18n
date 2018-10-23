package demo.i18n

import org.specs2._

class FoobarDeSpec extends Specification { def is = s2"""

  This is the specification for the german language strings

  The code for "blah.blah" must exist                     $testBlahBlah
  The code for "foo" must exist                           $testFoo
  The code for "foo.bar" must not have an apply method    $testFooBar
  The code for "foo.bar.baz" must exist                   $testFooBarBaz

  """

  implicit val de: Language = Language("de_DE")

  private def testBlahBlah =
    blah.blah() must equalTo("blah blah de")

  private def testFoo =
    foo() must equalTo("foo de")

  private def testFooBar =
    foo.bar.getClass.getDeclaredMethods must equalTo(Array())

  private def testFooBarBaz =
    foo.bar.baz() must equalTo("foo.bar.baz de")

}
