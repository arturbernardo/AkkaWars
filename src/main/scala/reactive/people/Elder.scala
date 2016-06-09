package reactive.people
import akka.actor.{Actor, ActorSystem, Props}
import reactive.wars.WarsActor.Person

class Elder extends Actor {
  val Other = context.actorOf(Props[Other])

  def receive = {
    case peopleBySpecies : Map[String, List[Person]] =>
      println("4")
      val rescue = peopleBySpecies map {case (key, value) => value.sortBy(p => p.birth_year) head}
      Other ! peopleBySpecies
  }
}