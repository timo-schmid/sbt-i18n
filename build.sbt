import sbt.Keys.licenses

lazy val plugin = (project in file("sbt-plugin"))
  .settings(
    sbtPlugin := true,
    name := "sbt-i18n",
    description := "A typesafe i18n generator for sbt",
    organization := "ch.timo-schmid",
    libraryDependencies += "org.tpolecat" %% "atto-core" % "0.5.2",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := true,
    publishTo := Some("Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  )

/*
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
*/

