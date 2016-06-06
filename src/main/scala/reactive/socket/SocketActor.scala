package reactive.socket

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.Tcp
import reactive.wars.RescueActor

class SocketActor(val connection : ActorRef) extends Actor with ActorLogging {
  val marker = context.actorOf(Props[RescueActor])
  marker ! RescueActor.Start(null, "C")
  val coords = "(-?\\d+\\.\\d+)".r
  override def receive = {
    case Tcp.Received(data) =>
      data.utf8String.trim match {
        case coords(msg) =>
          marker ! RescueActor.Rescue(msg)
        //case msg => log.info(msg)
      }
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
