package reactive.wars

import reactive.websocket.WebSocket
import akka.actor.{Actor, ActorLogging, Props}
import reactive.scan.ScanPlanetActor
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable


object WarsActor {
  sealed trait HideMessage
  case object Clear extends HideMessage
  case class Rescue(msg : String) extends HideMessage
  case class Unregister(ws : WebSocket) extends HideMessage

}

class WarsActor extends Actor with ActorLogging {
  val ScanPlanet = context.actorOf(Props[ScanPlanetActor])
  implicit val timeout = Timeout(5 seconds)
  val clients = mutable.ListBuffer[WebSocket]()

  override def receive = {
    case RescueActor.Rescue(msg) => {
      println("inicia")
      println("msg " + msg)
      val future = ScanPlanet ? msg
        future onSuccess {
          case x => self ! x
          //retornar pelo websocket
        }
      }
    case WebSocket.Open(ws) =>
      if (null != ws) {
        println("WebSocket OPEN?")

        clients += ws
        ws.send("PARTIU")
        log.debug("registered monitor for url {}", ws.path)
      }
      case WarsActor.Rescue(msg) => {
        //log.debug("move marker {} {} websocket to ({},{})",marker.id,(if(null==client)"without"else"with"),lng,lat)
        //context.actorSelection("/user/find") ! ""
       // if (null!=client) client.send("OK")
      }
      case msg : String => {
       // val msg1 = message(move)
        println("msg to client " + msg)

        for (client <- clients) client.send(msg)
      }
    }
  //private def message(move : WarsActor.Rescue) = s"""{"wars":{"nave":"${move.msg}","idx":"A"}}"""
}

