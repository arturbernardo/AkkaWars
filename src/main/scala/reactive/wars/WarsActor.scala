package reactive.wars

import reactive.websocket.WebSocket
import akka.actor.{Actor, ActorLogging, Props}
import reactive.scan.ScanPlanetActor
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import net.liftweb.json._
import reactive.people.GroupSpecies
import reactive.wars.WarsActor.Person


object WarsActor {
  sealed trait HideMessage
  case object Clear extends HideMessage
  case class Rescue(msg : String) extends HideMessage
  case class Unregister(ws : WebSocket) extends HideMessage
  case class Person(name: String,height: String,mass: String,hair_color: String,skin_color: String,eye_color: String,birth_year: String,gender: String,species: List[String],created: String,edited: String,url: String)
  case class PeoplePage(count : String, next : String, previous : String, results : List[Person])
}

class WarsActor extends Actor with ActorLogging {
  val ScanPlanet = context.actorOf(Props[ScanPlanetActor])
  val GroupSpecies = context.actorOf(Props[GroupSpecies])
  implicit val timeout = Timeout(5 seconds)
  implicit val formats = net.liftweb.json.DefaultFormats

  val clients = mutable.ListBuffer[WebSocket]()
  override def receive = {
    case RescueActor.Rescue(msg) => {
      val future = ScanPlanet ? msg
      future onSuccess {
        case json: String => {
          self ! json
        }
      }
    }
    case WebSocket.Open(ws) =>
      if (null != ws) {
        clients += ws
        ws.send("Resgate!")
      }
    case WarsActor.Rescue(msg) => {
    }
    case msg: String => {
      println("msg to client " + msg)
      val future = GroupSpecies ? parse(msg).extract[WarsActor.PeoplePage].results
      future onSuccess {
        case grouped : Map[String, List[Person]] => {
          println("future success")
          for (client <- clients) client.send(grouped.toString())
        }
        case grouped2: List[Person] => {
          println("future success")
          for (client <- clients) client.send("RESPONDEU ?????")
        }
      }
    }
  }
}

