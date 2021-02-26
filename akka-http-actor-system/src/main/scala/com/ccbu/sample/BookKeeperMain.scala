package com.ccbu.sample

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.util.{Failure, Success}

object BookKeeperMain {

  def startHTTPServer(routes: Route)(implicit system: ActorSystem[_]) {
    import system.executionContext
    val bindingFuture = Http().newServerAt("0.0.0.0", 9080).bind(routes)
    bindingFuture.onComplete {
      case Success(binding) => {
        printf("Completed Start http server. Running at %s on port %s", binding.localAddress.getHostString, binding.localAddress.getPort)
      }
      case Failure(ex) => {
        println("Failed to start server due to an exception", ex)
        system.terminate()
      }
    }
  }

  def main(args: Array[String]) {

    val mainActor = Behaviors.setup[Nothing] { context =>
      val bookKeeperActor = context.spawn(BookKeepActor(), "BookKeeperActor")
      context.watch(bookKeeperActor)
      val bookRouter = new BookRouter(bookKeeperActor)(context.system)
      startHTTPServer(bookRouter.bookRoute)(context.system)
      Behaviors.empty
    }
    ActorSystem[Nothing](mainActor, "httpServerActor")
  }
}
