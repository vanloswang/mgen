
// Remember, sbt needs empty lines between active settings

name := "mgen-api"

organization := "se.culvertsoft"

version := scala.util.Properties.envOrElse("MGEN_BUILD_VERSION", "SNAPSHOT")

isSnapshot := version.value.contains("SNAPSHOT")

crossPaths := false

retrieveManaged := true

EclipseKeys.withSource := true

EclipseKeys.relativizeLibs := false

publishArtifact in (Compile, packageDoc) := false