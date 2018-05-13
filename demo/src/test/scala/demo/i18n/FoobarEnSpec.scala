package demo.i18n

import org.specs2._

class FoobarEnSpec extends Specification { def is = s2"""

  This is the specification for the german language strings

  The code for "blah.blah" must exist                     $testBlahBlah
  The code for "foo" must exist                           $testFoo
  The code for "foo.bar" must not have an apply method    $testFooBar
  The code for "foo.bar.baz" must exist                   $testFooBarBaz

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

}
