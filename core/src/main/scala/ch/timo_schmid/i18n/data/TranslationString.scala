package ch.timo_schmid.i18n.data

final case class TranslationString(lang: String, key: String, parts: List[TranslationStringPart])
