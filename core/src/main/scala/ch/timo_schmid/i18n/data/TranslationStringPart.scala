package ch.timo_schmid.i18n.data

sealed trait TranslationStringPart
final case class Literal(text: String) extends TranslationStringPart
final case class Variable(name: String) extends TranslationStringPart
