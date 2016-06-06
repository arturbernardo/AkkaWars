package reactive.scan

import akka.actor.{Actor, ActorLogging}
import reactive.websocket.WebSocket


object ScanPlanetActor {
  sealed trait FindMessage
  case object Get extends FindMessage
}

class ScanPlanetActor extends Actor with ActorLogging {
  override def receive = {
    case WebSocket.Open(ws) =>
        sender() ! "OI"
      }
}
