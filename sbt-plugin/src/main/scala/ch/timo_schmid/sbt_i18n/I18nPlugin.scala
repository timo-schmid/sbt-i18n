package ch.timo_schmid.sbt_i18n

import ch.timo_schmid.i18n.data.{TranslationNode, TranslationSet}
import ch.timo_schmid.i18n.ops._
import ch.timo_schmid.i18n.parser.TranslationFileParser
import ch.timo_schmid.sbt_i18n.writer.TranslationFileWriter
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin
import sbt.Keys.{baseDirectory, crossVersion, sourceGenerators, sourceManaged}
import sbt._

import scala.language.implicitConversions

object I18nPlugin extends AutoPlugin {

  object autoImport {

    lazy val i18nDirectory: SettingKey[File] =
      settingKey[File]("The directory from which to read translations")

    lazy val i18nPackageName: SettingKey[String] =
      settingKey[String]("The top level package name for translations")

    lazy val generateI18n: TaskKey[Seq[File]] =
      taskKey[Seq[File]]("Creates the scala-code from i18n files")

  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    i18nDirectory := baseDirectory.value / "src/main/i18n",
    i18nPackageName := "i18n",
    generateI18n := genI18n(i18nDirectory.value, sourceManaged.value / "main", i18nPackageName.value),
    sourceGenerators in Compile += generateI18n.taskValue
  )

  def genI18n(i18nDir: File, destDir: File, packageName: String): Seq[File] =
    if (i18nDir.isDirectory)
      genI18n(i18nDir.listFiles().toSeq, destDir, packageName)
    else
      sys.error(s"The directory ${i18nDir.getAbsolutePath} is not a directory")

  private val parser = new TranslationFileParser(onWeirdFileName)

  private val writer = new TranslationFileWriter()

  def genI18n(i18nFiles: Seq[File], destDir: File, packageName: String): Seq[File] =
    Seq(
      writeLanguageClass(destDir, packageName)
    ) ++ parseI18nFiles(i18nFiles).asTree.map(writeI18nFile(destDir, packageName))

  // TODO (timo) There should be an external dependency "sbt-i18n-core" which contains this file.
  // This way, we could use it in the sbt-plugin as well.
  private def writeLanguageClass(destDir: File, packageName: String): File = {
    val file = destDir / "Language.scala"
    IO.write(
      file,
      s"""package $packageName
         |
         |final case class Language(code: String)
       """.stripMargin
    )
    file
  }


  private def parseI18nFiles(i18nFiles: Seq[File]): TranslationSet =
    i18nFiles
      .map(parser.parseFile)
      .fold(TranslationSet(Nil))(_.merge(_))

  private def writeI18nFile(destDir: File, packageName: String)(node: TranslationNode): File =
    writer.write(destDir / s"${node.key}.scala", packageName, node)

  // TODO (timo) Better name. Could also be an sbt key
  private def onWeirdFileName(fileName: String): String =
    sys.error(s"Cannot parse file name: $fileName")

}
