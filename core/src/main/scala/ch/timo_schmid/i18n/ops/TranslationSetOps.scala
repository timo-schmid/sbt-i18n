package ch.timo_schmid.i18n.ops

import ch.timo_schmid.i18n.data
import ch.timo_schmid.i18n.data.{TranslationNode, TranslationSet, TranslationString}

class TranslationSetOps(set: TranslationSet) {

  def byKey: Map[String, Seq[TranslationString]] =
    set.strings.groupBy(_.key)

  def byLanguage: Map[String, Seq[TranslationString]] =
    set.strings.groupBy(_.lang)

  def asTree: Seq[TranslationNode] =
    rootNodes()

  def append(translationString: TranslationString): TranslationSet =
    data.TranslationSet(set.strings :+ translationString)

  def merge(other: TranslationSet): TranslationSet =
    TranslationSet(set.strings ++ other.strings)

  private def rootKeys: Seq[String] =
    byKey.map { case (key, _) =>
      if(key.contains('.'))
        key.split('.').head
      else
        key
    }.toSeq.distinct

  private def rootNodes(): Seq[TranslationNode] =
    rootKeys.map { key =>
      data.TranslationNode(key, byKey.getOrElse(key, Nil), childNodes(key))
    }

  private def childNodes(prefix: String): Seq[TranslationNode] =
    childKeys(prefix).map { key =>
      data.TranslationNode(key, byKey.getOrElse(key, Nil), childNodes(key))
    }

  private def childKeys(prefix: String): List[String] =
    byKey.filter { case (key, _) =>
      key.startsWith(s"$prefix.")
    }.map { case (key, _) =>
      key.replaceFirst(s"$prefix.", "")
    }.map { key =>
      if(key.contains("."))
        key.substring(0, key.indexOf("."))
      else
        key
    }.map { key =>
      s"$prefix.$key"
    }.toList.distinct

}
