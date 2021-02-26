package com.cisco.ccbu

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{TextMessage, WebSocketRequest}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}

//sealed trait AgentInstruction
//case object Init      extends AgentInstruction
//case object Ack       extends AgentInstruction
//case object HeartBeat extends AgentInstruction
//case object Logout
//case class message(s: String) extends AgentInstruction

class TestChild(ref: ActorRef) extends Actor {
//  implicit val system = context.system
//  implicit val ec = system.dispatcher

  override def receive: Receive = {
    case ChildMessage.callingChild() =>
      println("received message from parent")
      ref ! calledParent
  }
//   def wsMessage( queue: SourceQueueWithComplete[TextMessage]): Receive = {
//    case Init =>
//      println("receving msg")
//      sender ! Ack
//    case TextMessage.Strict(s) =>
//      println(s"received message from socket $s")
//      context.parent.tell(calledParent, self)
//      msg += s
//      println(msg)
//      sender ! Ack
//  }
//
//  override def receive: Receive = {
//
//    case callingChild(ref) =>
//      val queue = Source
//        .queue[TextMessage](Int.MaxValue, OverflowStrategy.fail)
//        .via(Http().webSocketClientFlow(WebSocketRequest("ws://0.0.0.0:9900")))
//        .to(Sink.actorRefWithAck(self, Init, Ack, Logout))
//        .run()
//      context.become(wsMessage(queue))
//  }
}
