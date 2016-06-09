package reactive.people

import akka.actor.{Actor, Props}
import reactive.wars.WarsActor.Person


class GroupSpecies extends Actor {
  val Elder = context.actorOf(Props[Elder])

  def receive = {
    case people : List[Person] => {
      println("3")
      Elder ! people.groupBy(_.species(0))
    }
  }
}