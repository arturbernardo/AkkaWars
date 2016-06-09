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
  implicit val timeout = Timeout(5 seconds)
  implicit val formats = net.liftweb.json.DefaultFormats
  val clients = mutable.ListBuffer[WebSocket]()

  override def receive = {
    case BindRescuePost.Rescue(msg) => {
      println("1")
      ScanPlanet ! msg
    }
    case WebSocket.Open(ws) => {
      if (null != ws) {
        clients += ws
        ws.send("Resgate!")
      }
    }

    case spaceShip: Map[String, List[Person]] => {
      println("6")
      for (client <- clients) client.send(spaceShip.toString())
    }
  }
}