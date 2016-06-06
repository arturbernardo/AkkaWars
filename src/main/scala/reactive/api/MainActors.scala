package reactive.api

import akka.actor.Props
import reactive.wars.WarsActor

trait MainActors {
  this : AbstractSystem =>

  lazy val wars = system.actorOf(Props[WarsActor], "wars")
}
