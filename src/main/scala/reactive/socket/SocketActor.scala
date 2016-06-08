package reactive.socket

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.Tcp
import reactive.scan.ScanPlanetActor
import reactive.wars.{RescueActor, WarsActor}

class SocketActor(val connection : ActorRef) extends Actor with ActorLogging {
  val marker = context.actorOf(Props[WarsActor])
  //marker ! RescueActor.Start(null, "C")
  val coords = "(-?\\d+\\.\\d+)".r
  override def receive = {
    case Tcp.Received(data) =>
      println("SocketActor data" + data)
      data.utf8String.trim match {
        case coords(msg) =>
          println("cords" + msg)
          marker ! ScanPlanetActor.Get
        case msg => println("qualquer coisa " + msg)
      }
    case x => println("qualquer coisa mesmo ")
    case Tcp.PeerClosed      => stop()
    case Tcp.ErrorClosed     => stop()
    case Tcp.Closed          => stop()
    case Tcp.ConfirmedClosed => stop()
    case Tcp.Aborted         => stop()
    case RescueActor.Stop =>
      context stop self
  }
  private def stop() = {
    marker ! RescueActor.Stop
  }
}
