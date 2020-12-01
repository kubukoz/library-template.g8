inThisBuild(
  List(
    organization := "com.kubukoz",
    homepage := Some(url("https://github.com/kubukoz/$name$")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "kubukoz",
        "Jakub Kozłowski",
        "kubukoz@gmail.com",
        url("https://kubukoz.com")
      )
    )
  ))

def crossPlugin(x: sbt.librarymanagement.ModuleID) = compilerPlugin(x cross CrossVersion.full)
val compilerPlugins = List(
  crossPlugin("org.typelevel" % "kind-projector" % "0.11.2"),
  crossPlugin("com.github.cb372" % "scala-typed-holes" % "0.1.1"),
  compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

val commonSettings = Seq(
  scalaVersion := "$scalaVersion$",
  scalacOptions --= Seq("-Xfatal-warnings"),
  name := "$name$",
  updateOptions := updateOptions.value.withGigahorse(false),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.1.0" % Test
  ) ++ compilerPlugins
)

val core = project.settings(commonSettings).settings(name += "-core")

val $projectVariableName$ =
  project
    .in(file("."))
    .settings(commonSettings)
    .settings(skip in publish := true)
    .dependsOn(core)
    .aggregate(core)
