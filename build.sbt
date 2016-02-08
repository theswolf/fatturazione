name := "Fatturazione"
version := "1.0"
scalaVersion := "2.11.7"
logLevel := Level.Debug
//retrieveManaged := true

libraryDependencies  ++= Seq(
  "org.apache.poi" % "poi" % "3.13",
  "org.apache.poi" % "poi-ooxml" % "3.13",
  "com.github.nscala-time" %% "nscala-time" % "2.8.0",
  "com.sparkjava" % "spark-core" % "2.3",
  "org.hibernate" % "hibernate-core" % "5.0.7.Final" ,
  "com.h2database" % "h2" % "1.4.190",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "com.google.code.gson" % "gson" % "2.5",
  "io.jsonwebtoken" % "jjwt" % "0.6.0",
  
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "junit" % "junit" % "4.8.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
 // "org.mockito" % "mockito-core" % "2.0.41-beta" % "test"

)

val h2 = "com.h2database" % "h2" % "1.4.190"

resolvers +="Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers +="Sonatype OSS Dist" at "https://oss.sonatype.org/content/repositories"
resolvers += "Central Repository" at "https://repo1.maven.org/maven2"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)

