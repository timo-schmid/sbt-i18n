package demo.i18n

import org.specs2._

class UserEnSpec extends Specification { def is = s2"""

  This is the specification for the english language strings in the "user" section:

  Test key "user"             $testGroup
  Test key "user.id"          $testId
  Test key "user.name"        $testName
  Test key "user.firstName"   $testFirstName
  Test key "user.lastName"    $testLastName
  Test key "user.age"         $testAge
  Test key "user.email"       $testEmail
  Test key "user.ui"          $testUi
  Test key "user.ui.add"      $testUiAdd
  Test key "user.ui.edit"     $testUiEdit
  Test key "user.ui.delete"   $testUiDelete

  """

  implicit val en: Language = Language("en_US")

  private def testGroup =
    user() must equalTo("User")

  private def testId =
    user.id() must equalTo("Id")

  private def testName =
    user.name() must equalTo("Username")

  private def testFirstName =
    user.firstName() must equalTo("First name")

  private def testLastName =
    user.lastName() must equalTo("Last name")

  private def testAge =
    user.age() must equalTo("Age")

  private def testEmail =
    user.email() must equalTo("Email-Address")

  private def testUi =
    user.ui() must equalTo("Manage users")

  private def testUiAdd =
    user.ui.add() must equalTo("Create user")

  private def testUiEdit =
    user.ui.edit("timo") must equalTo("Edit user 'timo'")

  private def testUiDelete =
    user.ui.delete("timo") must equalTo("Delete user 'timo'")

}
