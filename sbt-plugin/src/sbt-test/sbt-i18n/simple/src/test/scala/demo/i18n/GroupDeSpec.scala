package demo.i18n

import org.specs2._

class GroupDeSpec extends Specification { def is = s2"""

  This is the specification for the german language strings in the "group" section:

  Test key "group"            $testGroup
  Test key "group.id"         $testId
  Test key "group.name"       $testName
  Test key "group.members"    $testMembers
  Test key "group.ui"         $testUi
  Test key "group.ui.add"     $testUiAdd
  Test key "group.ui.edit"    $testUiEdit
  Test key "group.ui.delete"  $testUiDelete

  """

  implicit val de: Language = Language("de_DE")

  private def testGroup =
    group() must equalTo("Gruppe")

  private def testId =
    group.id() must equalTo("Id")

  private def testName =
    group.name() must equalTo("Name")

  private def testMembers =
    group.members() must equalTo("Mitglieder")

  private def testUi =
    group.ui() must equalTo("Gruppen verwalten")

  private def testUiAdd =
    group.ui.add() must equalTo("Gruppe erstellen")

  private def testUiEdit =
    group.ui.edit("Admin") must equalTo("Gruppe 'Admin' bearbeiten")

  private def testUiDelete =
    group.ui.delete("Admin") must equalTo("Gruppe 'Admin' löschen")

}
