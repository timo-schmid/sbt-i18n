sys.props.get("plugin.version") match {
  case Some(version) => addSbtPlugin("ch.timo-schmid" % "sbt-i18n" % version)
  case _             => sys.error("""|The system property 'plugin.version' is not defined.
                                     |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}