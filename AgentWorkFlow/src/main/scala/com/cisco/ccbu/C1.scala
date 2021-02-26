package com.cisco.ccbu

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{TextMessage, WebSocketRequest}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.ExecutionContext

class C1 (ref: ActorRef) extends Actor {
  implicit val system: ActorSystem = context.system
  implicit val ex: ExecutionContext = context.dispatcher

  override def receive: Receive = {
    case Init =>
      println("receving msg")
      sender ! Ack
    case TextMessage.Strict(s) =>
      sender ! Ack
      ref ! D(s)
    case BB =>
      println("AA case")
      Source
        .queue[TextMessage](Int.MaxValue, OverflowStrategy.fail)
        .via(Http().webSocketClientFlow(WebSocketRequest("ws://0.0.0.0:9900")))
        .to(Sink.actorRefWithAck(self, Init, Ack, Logout))
        .run()
      ref ! D("some dummy from C")

  }
}
