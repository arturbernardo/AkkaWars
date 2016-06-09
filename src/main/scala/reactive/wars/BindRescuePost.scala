package reactive.wars

import akka.actor.{Actor, ActorLogging}
import reactive.websocket.WebSocket

object BindRescuePost {
  sealed trait PostMessage
  case class Rescue(message : String) extends PostMessage
  case object Stop extends PostMessage
  case class Start(ws : WebSocket, marker : String) extends PostMessage
  case class Move(longitude : String, latitude : String) extends PostMessage
}
class BindRescuePost extends Actor with ActorLogging {
  import BindRescuePost._

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