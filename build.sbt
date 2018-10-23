inThisBuild(
  List(
    scalaVersion := "2.12.7",
    organization := "ch.timo-schmid",
    homepage := Some(url("https://github.com/timo-schmid/sbt-i18n")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    resolvers += Resolver.sonatypeRepo("releases"),
    developers := List(
      Developer(
        "timo-schmid",
        "Timo Schmid",
        "timo.schmid@gmail.com",
        url("https://github.com/timo-schmid")
      )
    )
  )
)

lazy val core = (project in file("core"))
  .settings(
    name := "i18n-core",
    description := "A typesafe i18n generator",
    libraryDependencies += "org.tpolecat" %% "atto-core" % "0.5.2"
  )

lazy val plugin = (project in file("sbt-plugin"))
  .settings(
    name := "sbt-i18n",
    description := "A typesafe i18n generator for sbt",
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
