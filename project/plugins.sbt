logLevel := Level.Warn
// Git Plugin https://blog.softwaremill.com/meaningful-docker-image-tags-made-with-build-tools-c8877cd21da9
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")
// Docker Plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.25")