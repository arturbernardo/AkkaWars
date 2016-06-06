package reactive.wars

import reactive.websocket.WebSocket
import akka.actor.{Actor, ActorLogging, PoisonPill}
import java.util.UUID

object RescueActor {
  sealed trait MarkerMessage
  case class Rescue(message : String) extends MarkerMessage
  case object Stop extends MarkerMessage
  case class Start(ws : WebSocket, marker : String) extends MarkerMessage
  case class Move(longitude : String, latitude : String) extends MarkerMessage
}
class RescueActor extends Actor with ActorLogging {
  import RescueActor._

  var client : WebSocket = _
  override def receive = {
    case Rescue(message) => {
      if (null!=client) client.send("OK")
    }
    case Stop => {
      sender ! Stop
      context stop self
    }
    case Start(ws, idx) => {
      if (null!=client) client.send("OK")
    }
    case Move(lng, lat) => {
      if (null!=client) client.send("OK")
    }
  }
}


