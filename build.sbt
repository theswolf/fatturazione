name := "Fatturazione"
version := "1.0"
scalaVersion := "2.11.7"
logLevel := Level.Debug
retrieveManaged := true

libraryDependencies  ++= Seq(
  "org.apache.poi" % "poi" % "3.13",
  "org.apache.poi" % "poi-ooxml" % "3.13",
  "com.github.nscala-time" %% "nscala-time" % "2.8.0",
  "com.sparkjava" % "spark-core" % "1.1.1",
  "junit" % "junit" % "4.8.1" % "test"
)
