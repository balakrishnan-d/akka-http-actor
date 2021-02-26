package com.cisco.ccbu

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{TextMessage, WebSocketRequest}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}

import scala.concurrent.ExecutionContext
sealed trait AgentInstruction
case object Init      extends AgentInstruction
case object Ack       extends AgentInstruction
case object HeartBeat extends AgentInstruction
case object Logout
case class message(s: String) extends AgentInstruction

class B1(ref: ActorRef) extends Actor {

  implicit val system: ActorSystem = context.system
  implicit val ex: ExecutionContext = context.dispatcher
  private var queue: SourceQueueWithComplete[TextMessage] = _
  override def receive: Receive = {
    case Init =>
      println("receving msg")
      sender ! Ack
    case TextMessage.Strict(s) =>
      sender ! Ack
      ref ! C(s)
      queue.complete()
    case AA =>
      println("AA case")
      queue = Source
        .queue[TextMessage](Int.MaxValue, OverflowStrategy.fail)
        .via(Http().webSocketClientFlow(WebSocketRequest("ws://0.0.0.0:9900")))
        .to(Sink.actorRefWithAck(self, Init, Ack, Logout))
        .run()
      ref ! C("some dummy")
    case BB =>
      println("BB case")
  }
}
