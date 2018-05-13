package ch.timo_schmid.i18n.data

case class TranslationNode(key: String, strings: Seq[TranslationString], childNodes: Seq[TranslationNode])
