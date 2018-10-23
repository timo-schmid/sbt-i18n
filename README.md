# sbt-i18n

This sbt plugin lets you create i18n files and publish them as scala functions.

## Quick start

Enable **sbt-i18n** in **plugins.sbt**:

```scala
addSbtPlugin("ch.timo-schmid" % "sbt-i18n" % "{ latest_version }")
```

Set the package for the generated classes:

```scala
lazy val `acme-server` = (project in file("."))
  .settings(i18nPackageName := "corp.acme.i18n")
  .enablePlugins(I18nPlugin)
```

Create a file called **src/main/i18n/en_US.properties**:

```scala
user=User
user.id=Id
user.name=Username
user.firstName=First name
user.lastName=Last name
user.age=Age
user.email=Email-Address
user.ui=Manage users
user.ui.add=Create user
user.ui.edit=Edit user '$userName'
user.ui.delete=Delete user '$userName'
```

You can now use the generated classes in your scala code:

```scala
implicit val de: Language = Language("en_US")
println(corp.acme.i18n.user())                 // => User
println(corp.acme.i18n.user.id())              // => Id
println(corp.acme.i18n.user.name())            // => Username
println(corp.acme.i18n.user.firstName())       // => First name
println(corp.acme.i18n.user.lastName())        // => Last name
println(corp.acme.i18n.user.age())             // => Email-Address
println(corp.acme.i18n.user.email())           // => Email-Address
println(corp.acme.i18n.user.ui())              // => Manage users
println(corp.acme.i18n.user.ui.add())          // => Create user
println(corp.acme.i18n.user.ui.edit("timo"))   // => Edit user 'timo'
println(corp.acme.i18n.user.ui.delete("timo")) // => Delete user 'timo'
```


