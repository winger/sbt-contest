import sbt._
import complete.Parser.repeat
import complete.DefaultParsers._
import Process._
import Keys._

object MyBuild extends Build {
  lazy val project = Project("main", file("."))

  val genTimusProject = InputKey[Unit]("gen-timus-project", "*desc*")
  
  val genTimusProjectParser = { (state: State) =>
    Space ~> repeat(Digit, 4, 4).string ~ (Space ~> ID).? map {
      case s1 ~ Some(s2) => (s1, s2)
      case s ~ None => (s, s)
    }
  }

  val genTimusProjectDef = InputTask(genTimusProjectParser) { (argTask: TaskKey[(String, String)]) =>
    argTask map { args =>
      println(args)
    }
  }

  override def settings = super.settings ++ Seq(
    genTimusProject <<= genTimusProjectDef,
    libraryDependencies += "org.jsoup" % "jsoup" % "1.7.1"
  )
}
