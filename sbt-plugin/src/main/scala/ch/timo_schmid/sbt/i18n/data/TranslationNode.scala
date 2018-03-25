package ch.timo_schmid.sbt.i18n.data

case class TranslationNode(key: String, strings: Seq[TranslationString], childNodes: Seq[TranslationNode])
