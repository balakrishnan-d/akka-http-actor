name := "akka-http-actor-system"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
mainClass in Compile := Some("BookMain")

version := "0.1"
scalaVersion := "2.13.4"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.apache.kafka" %% "kafka" % "2.7.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.2.3",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  "org.scalactic" %% "scalactic" % "3.2.2",
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
)