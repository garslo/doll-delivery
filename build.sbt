/** Project */
name := "dolldelivery"

organization := "garslo"

scalaVersion := "2.10.2"

/** Shell */
shellPrompt := { state => System.getProperty("user.name") + "> " }

/** Dependencies */
resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                  "releases" at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies <++= version { version =>
  Seq(
    "org.specs2"      %% "specs2"       % version,
    "org.scala-lang"  % "scala-reflect" % "2.10.2"
  )
}

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions,reflectiveCalls,postfixOps,higherKinds,existentials,experimental.macros")

scalacOptions in Test ++= Seq("-Yrangepos")

logBuffered := false

/** Console */
initialCommands in console := "import org.specs2._"
