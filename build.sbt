name := "ts-generator-ws"
version := "1.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
   "com.typesafe.akka" %% "akka-http-core" % "2.4.7",
   "com.typesafe.akka" %% "akka-http-experimental" % "2.4.7",

   "com.github.scopt" %% "scopt" % "3.5.0",

   "com.github.nscala-time" % "nscala-time_2.11" % "2.12.0",
   "org.apache.commons" % "commons-math3" % "3.6.1",
   "io.spray" %%  "spray-json" % "1.3.2",
   "org.scalactic" %% "scalactic" % "3.0.0",
   "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

libraryDependencies += "be.cetic" %% "rts-gen" % "0.1.10"

// Create a package with sbt package
mainClass in Compile := Some("be.cetic.rtsgen.genservice.GeneratorWebServer")

// Create a fat jar with sbt assembly
mainClass in assembly := Some("be.cetic.rtsgen.genservice.GeneratorWebServer")