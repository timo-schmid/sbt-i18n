package ch.timo_schmid.i18n

import ch.timo_schmid.i18n.data.{TranslationNode, TranslationSet, TranslationString}

import scala.language.implicitConversions

package object ops {

  implicit def toTranslationStringOps(x: TranslationString): TranslationStringOps =
    new TranslationStringOps(x)

  implicit def toTranslationSetOps(x: TranslationSet): TranslationSetOps =
    new TranslationSetOps(x)

  implicit def toTranslationNodeOps(x: TranslationNode): TranslationNodeOps =
    new TranslationNodeOps(x)

}
