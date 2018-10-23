lazy val root = (project in file("."))
  .settings(
    scalaVersion := "2.12.7",
    name := "sbt-i18n-demo",
    description := "A typesafe i18n generator for sbt",
    organization := "ch.timo-schmid",
    i18nPackageName := "demo.i18n",
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.9" % "test",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
  )
  .enablePlugins(I18nPlugin)