resolvers +="Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers +="Sonatype OSS Dist" at "https://oss.sonatype.org/content/repositories"
resolvers += "Central Repository" at "https://repo1.maven.org/maven2"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)
