package ch.timo_schmid.sbt_i18n.writer

import java.io.File

import ch.timo_schmid.i18n.StringUtil.indent
import ch.timo_schmid.i18n.data._
import ch.timo_schmid.i18n.ops._
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
    s"package $packageName\n"

  private def createNodeCode(node: TranslationNode): String =
    s"""object ${createObjectName(node)} {
       |${indent(2)(createApplyFunction(node))}
       |${indent(2)(node.childNodes.map(createNodeCode).mkString("\n"))}
       |}""".stripMargin + "\n"

  private def createApplyFunction(node: TranslationNode): String =
    if(node.strings.isEmpty)
      ""
    else
      s"""
         |def apply(${createObjectParams(node)})(implicit lang: Language): String = lang.code match {
         |${indent(2)(createMatchCode(node))}
         |  case _ => "${node.key}"
         |}""".stripMargin + "\n"

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
