package reactive.people

import akka.actor.Actor
import reactive.wars.WarsActor.Person


class GroupSpecies extends Actor {

  def receive = {
    case x : List[Person] => {
      sender() ! x.groupBy(_.species(0))
    }
  }
}
