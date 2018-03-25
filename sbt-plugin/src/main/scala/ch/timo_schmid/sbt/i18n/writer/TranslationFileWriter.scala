package ch.timo_schmid.sbt.i18n.writer

import java.io.File

import ch.timo_schmid.sbt.i18n.StringUtil.indent
import ch.timo_schmid.sbt.i18n.data._
import ch.timo_schmid.sbt.i18n.ops._
import sbt.IO

class TranslationFileWriter {

  def write(file: File, packageName: String, node: TranslationNode): File = {
    IO.write(
      file,
      Seq(
        createPackageCode(packageName),
        createNodeCode(node)
      ).mkString("\n")
    )
    file
  }

  private def createPackageCode(packageName: String): String =
    s"package $packageName"

  private def createNodeCode(node: TranslationNode): String =
    s"""
       |object ${createObjectName(node)} {
       |
       |  def apply(${createObjectParams(node)})(implicit lang: Language): String = lang.code match {
       |${indent(4)(createMatchCode(node))}
       |    case _ => "${node.key}"
       |  }
       |${indent(2)(node.childNodes.map(createNodeCode).mkString("\n"))}
       |}""".stripMargin

  private def createObjectName(node: TranslationNode): String =
    node.key.substring(node.key.lastIndexOf(".") + 1)

  private def createObjectParams(node: TranslationNode): String =
    node.strings.flatMap(str =>
      str.parts.collect { case Variable(name) =>
        name
      }
    ).map(name => s"$name: String").distinct.mkString(", ")

  private def createMatchCode(node: TranslationNode): String =
    node.strings.map(createStringMatchCode).mkString("\n")

  private def createStringMatchCode(string: TranslationString): String =
    s"""case "${string.lang}" => s"${string.toScalaCode}""""

}
