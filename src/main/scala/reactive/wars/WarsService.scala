package reactive.wars

import akka.actor.{ActorRef, ActorSystem}
import reactive.Configuration
import reactive.api.ApplicationJsonFormats
import reactive.websocket.WebSocket
import spray.http.StatusCodes
import spray.routing.Directives

class WarsService(val wars : ActorRef)(implicit system : ActorSystem) extends Directives with ApplicationJsonFormats {
  lazy val route =
    pathPrefix("wars") {
      val dir = "wars/"
      pathEndOrSingleSlash {
        get {
          getFromResource(dir + "index.html")
        } ~
        post {
          handleWith {
            move : WarsActor.HideMessage =>
              //hide ! move
              "hidden"
          }
        }
      } ~
      path("ws") {
        requestUri { uri =>
          val wsUri = uri.withPort(Configuration.portWs)
          system.log.debug("redirect {} to {}", uri, wsUri)
          redirect(wsUri, StatusCodes.PermanentRedirect)
        }
      } ~
      getFromResourceDirectory(dir)
    }
  lazy val wsroute = 
    pathPrefix("wars") {
      path("ws") {
        implicit ctx =>
          ctx.responder ! WebSocket.Register(ctx.request, wars, true)
      }
    }
}
