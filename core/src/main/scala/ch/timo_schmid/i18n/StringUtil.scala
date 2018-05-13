package ch.timo_schmid.i18n

// TODO Could probably be private
object StringUtil {

  def indent(spaces: Int)(str: String): String =
    str
      .split("\n")
      .mkString(
        _spaces(spaces),
        "\n" + _spaces(spaces),
        ""
      )

  private def _spaces(spaces: Int): String =
    if(spaces == 0)
      ""
    else
      " " + _spaces(spaces - 1)

}
