package reactive.people


import akka.actor.{Actor, ActorSystem, Props}
import reactive.wars.WarsActor
import reactive.wars.WarsActor.Person

import scala.language.postfixOps

class Other extends Actor {
  val WarsActor = context.actorOf(Props[WarsActor])

  def receive = {
    case peopleBySpecies: Map[String, List[Person]] => {
      println("5")
      // val c = x map {case (key, value) => value.sortBy(p => p.birth_year) tail}
      //WarsActor ! peopleBySpecies
      context.actorSelection("/user/wars") ! peopleBySpecies

    }
  }
}
