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
    sbtPlugin := true,
    name := "sbt-i18n",
    description := "A typesafe i18n generator for sbt",
    organization := "ch.timo-schmid",
    libraryDependencies += "org.tpolecat" %% "atto-core" % "0.5.2",
    libraryDependencies += {
      val sbtV   = (sbtBinaryVersion in pluginCrossBuild).value
      val scalaV = (scalaBinaryVersion in update).value
      Defaults.sbtPluginExtra("org.portable-scala" %% "sbt-scalajs-crossproject" % "0.4.0", sbtV, scalaV)
    },
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := true,
    publishTo := Some("Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
//    GPG signed publishing isn't working (yet) - the gpg plugin doesn't seem to be compatible with my version of the library.
//    useGpg := true,
//    pgpPublicRing := file("/path/to/.gnupg/pubring.gpg"),
//    pgpSecretRing := file("/path/to/.gnupg/secring.gpg"),
//    usePgpKeyHex("2673B174C4071B0E"),
//    pgpPassphrase := Some(sys.env.getOrElse("GPG_PASSPHRASE", "")).map(_.toCharArray)
  )
  .dependsOn(core)
  .aggregate(core)

lazy val demo = (project in file("demo"))
  .settings(
    name := "sbt-i18n-demo",
    description := "A typesafe i18n generator for sbt",
    organization := "ch.timo-schmid",
    i18nPackageName := "demo.i18n",
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.9" % "test",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
  )
  .enablePlugins(I18nPlugin)

