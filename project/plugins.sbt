logLevel := Level.Warn
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.3")
// Git Plugin https://blog.softwaremill.com/meaningful-docker-image-tags-made-with-build-tools-c8877cd21da9
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")
// Docker Plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.4")