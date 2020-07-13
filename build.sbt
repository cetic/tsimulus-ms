name := "tsimulus-ms"
version := "1.6"
scalaVersion := "2.11.8"

enablePlugins(JavaAppPackaging, DockerPlugin, GitVersioning)

git.formattedShaVersion := git.gitHeadCommit.value map { sha =>
			s"$sha".substring(0, 8)
  }
 // docker info
dockerRepository :=  Some("ceticasbl/tsimulus-ms")
dockerBaseImage := "openjdk"
dockerUpdateLatest := true
dockerAlias := DockerAlias(dockerRepository.value, dockerUsername.value, name.value, git.formattedShaVersion.value)
dockerUsername := Some("ceticasbl")

libraryDependencies ++= Seq(
   "com.typesafe.akka" %% "akka-http-core" % "2.4.7",
   //"com.typesafe.akka" %% "akka-http-experimental" % "2.4.7",
   "com.github.scopt" %% "scopt" % "3.5.0",
   "com.github.nscala-time" % "nscala-time_2.11" % "2.14.0",
   "org.apache.commons" % "commons-math3" % "3.6.1",
   "io.spray" %%  "spray-json" % "1.3.2",
   "org.scalactic" %% "scalactic" % "3.0.0",
   "org.scalatest" %% "scalatest" % "3.0.0" % "test",
   "ch.megard" %% "akka-http-cors" % "0.2.2"
)

libraryDependencies += "be.cetic" %% "tsimulus" % "0.1.18"

// Create a package with sbt package
mainClass in Compile := Some("be.cetic.tsimulus.ws.GeneratorWebServer")

// Create a fat jar with sbt assembly
mainClass in assembly := Some("be.cetic.tsimulus.ws.GeneratorWebServer")