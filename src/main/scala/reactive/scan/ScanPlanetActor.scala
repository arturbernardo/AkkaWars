package reactive.scan

import akka.actor.{Actor, ActorLogging}
import reactive.websocket.WebSocket


object ScanPlanetActor {
  sealed trait FindMessage
  case object Get extends FindMessage
}

class ScanPlanetActor extends Actor with ActorLogging {
  override def receive = {
    case WebSocket.Open(ws) => {
      println("inicia 2")
      sender() ! "OI"
    }
    case msg : String => {
      println("inicia 2")
      println("msg2"+ msg)
      sender() ! msg
    }
  }
}
