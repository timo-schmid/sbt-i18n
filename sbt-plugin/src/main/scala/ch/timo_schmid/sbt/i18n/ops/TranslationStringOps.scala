package ch.timo_schmid.sbt.i18n.ops

import ch.timo_schmid.sbt.i18n.data._

class TranslationStringOps(s: TranslationString) {

  def toScalaCode: String =
    s.parts.map(toScalaPart).mkString

  private def toScalaPart(part: TranslationStringPart): String =
    part match {
      case Literal(text) => literalToScala(text)
      case Variable(name) => variableToScala(name)
    }

  private def literalToScala(text: String): String =
    text.replace("\n", "\\n")

  private def variableToScala(name: String): String =
    "${" + name + "}"

}
