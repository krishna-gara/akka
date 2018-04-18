name := "akka-examples"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-xml" % "10.0.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.11",
  "com.typesafe.akka" %% "akka-remote" % "2.5.11",
  "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "0.1",
  "com.sksamuel.elastic4s" %% "elastic4s-http" % "5.4.2"
)
