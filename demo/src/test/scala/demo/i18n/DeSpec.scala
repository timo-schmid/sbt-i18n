package demo.i18n

import org.specs2._

class DeSpec extends Specification { def is = s2"""

  This is the specification for the german language strings

  The generated strings must match     $testDeStrings

  """

  implicit val de: Language = Language("de_DE")

  private def testDeStrings =
    foo() must equalTo("blah")

}
