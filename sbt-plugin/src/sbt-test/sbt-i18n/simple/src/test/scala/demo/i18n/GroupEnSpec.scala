package demo.i18n

import org.specs2._

class GroupEnSpec extends Specification { def is = s2"""

  This is the specification for the english language strings in the "group" section:

  Test key "group"            $testGroup
  Test key "group.id"         $testId
  Test key "group.name"       $testName
  Test key "group.members"    $testMembers
  Test key "group.ui"         $testUi
  Test key "group.ui.add"     $testUiAdd
  Test key "group.ui.edit"    $testUiEdit
  Test key "group.ui.delete"  $testUiDelete

  """

  implicit val en: Language = Language("en_US")

  private def testGroup =
    group() must equalTo("Group")

  private def testId =
    group.id() must equalTo("Id")

  private def testName =
    group.name() must equalTo("Name")

  private def testMembers =
    group.members() must equalTo("Members")

  private def testUi =
    group.ui() must equalTo("Manage groups")

  private def testUiAdd =
    group.ui.add() must equalTo("Create group")

  private def testUiEdit =
    group.ui.edit("Admin") must equalTo("Edit group 'Admin'")

  private def testUiDelete =
    group.ui.delete("Admin") must equalTo("Delete group 'Admin'")

}
