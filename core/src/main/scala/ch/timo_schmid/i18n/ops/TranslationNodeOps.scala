package ch.timo_schmid.i18n.ops

import ch.timo_schmid.i18n.StringUtil.indent
import ch.timo_schmid.i18n.data.TranslationNode

class TranslationNodeOps(node: TranslationNode) {

  def show: String =
    _show(node)

  private def _show(node: TranslationNode, spaces: Int = 0): String =
    indent(spaces) {
      s"""- ${node.key} = ${node.strings.map(_.toScalaCode).mkString(" | ")}
         |${node.childNodes.sortBy(_.key).map(n => _show(n, spaces + 2)).mkString("\n")}
         |""".stripMargin
    }

}
