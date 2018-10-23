package demo.i18n

import org.specs2._

class UserDeSpec extends Specification { def is = s2"""

  This is the specification for the german language strings in the "user" section:

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

  implicit val de: Language = Language("de_DE")

  private def testGroup =
    user() must equalTo("Benutzer")

  private def testId =
    user.id() must equalTo("Id")

  private def testName =
    user.name() must equalTo("Benutzername")

  private def testFirstName =
    user.firstName() must equalTo("Vorname")

  private def testLastName =
    user.lastName() must equalTo("Nachname")

  private def testAge =
    user.age() must equalTo("Alter")

  private def testEmail =
    user.email() must equalTo("Email-Adresse")

  private def testUi =
    user.ui() must equalTo("Benutzer verwalten")

  private def testUiAdd =
    user.ui.add() must equalTo("Benutzer erstellen")

  private def testUiEdit =
    user.ui.edit("timo") must equalTo("Benutzer 'timo' bearbeiten")

  private def testUiDelete =
    user.ui.delete("timo") must equalTo("Benutzer 'timo' löschen")

}
