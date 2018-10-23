import sbt.Keys.licenses

lazy val core = (project in file("core"))
  .settings(
    name := "i18n-core",
    description := "A typesafe i18n generator",
    organization := "ch.timo-schmid",
    libraryDependencies += "org.tpolecat" %% "atto-core" % "0.5.2",
    publishMavenStyle := true,
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishTo := Some("Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"),
  )

lazy val plugin = (project in file("sbt-plugin"))
  .settings(
    name := "sbt-i18n",
    description := "A typesafe i18n generator for sbt",
    organization := "ch.timo-schmid",
    libraryDependencies += "org.tpolecat" %% "atto-core" % "0.5.2",
    libraryDependencies += {
      val sbtV   = (sbtBinaryVersion in pluginCrossBuild).value
      val scalaV = (scalaBinaryVersion in update).value
      Defaults.sbtPluginExtra("org.portable-scala" %% "sbt-scalajs-crossproject" % "0.4.0", sbtV, scalaV)
    },
    scriptedLaunchOpts += "-Xmx1024M",
    scriptedLaunchOpts += "-Dplugin.version=" + version.value,
    scriptedBufferLog := false
  )
  .enablePlugins(SbtPlugin)
  .dependsOn(core)
  .aggregate(core)
