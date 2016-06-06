package reactive.wars

import reactive.websocket.WebSocket
import akka.actor.{Actor, ActorLogging, Props}
import reactive.scan.ScanPlanetActor
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._


object WarsActor {
  sealed trait HideMessage
  case object Clear extends HideMessage
  case class Unregister(ws : WebSocket) extends HideMessage
}

class WarsActor extends Actor with ActorLogging {
  val ScanPlanet = context.actorOf(Props[ScanPlanetActor])
  implicit val timeout = Timeout(5 seconds)

  override def receive = {
    case RescueActor.Rescue(msg) => {
      println("inicia")
      println("msg " +msg)
      val future = ScanPlanet ? "hello"
      //future pipeTo ScanPlanet
    }
  }
}
