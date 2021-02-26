package com.cisco.ccbu

import akka.Done
import akka.actor.{Actor, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.OverflowStrategy
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Future, Promise}

trait WsCommand
case class wsDisconnect(ref: ActorRef) extends WsCommand
case class wsConnect() extends WsCommand

//sealed trait AgentInstruction
//case object Init      extends AgentInstruction
//case object Ack       extends AgentInstruction
//case object HeartBeat extends AgentInstruction
//case object Logout
//case class message(s: String) extends AgentInstruction

class childActor(val wsURL: String) extends Actor {
//  val parentRef: ActorRef = actorRef
//  val incoming: Sink[Message, Future[Done]] =
//    Sink.foreach[Message] {
//      case message: TextMessage.Strict =>
//        println("received message")
//      case _ =>
//      // ignore other message types
//    }

//  def onMsg(msg: String) = {
//    actorRef ! onMessage(msg)
//  }

  override def receive: Receive = {
//    case Init =>
//      sender ! Ack
//    case TextMessage.Strict(s) =>
//      println(s"received message from socket $s")
//      self ! message(s)
//      sender ! Ack
//    case wsConnect => {
//      println("______ start command")
//      implicit val system = context.system
//      implicit val ec = context.dispatcher
//      val flow: Flow[Message, Message, Promise[Option[Message]]] =
//        Flow.fromSinkAndSourceMat(
//          Sink.foreach[Message](msg => {
//
//            println("received message")
//            //        println(msg.asTextMessage)
////            onMsg(msg.asTextMessage.getStrictText)
//          }),
//          Source.maybe[Message])(Keep.right)
//      val (upgradeResponse, promise) =
//        Http().singleWebSocketRequest(
//          WebSocketRequest(wsURL),
//          flow)
//      val msg = flow.collect {
//        case TextMessage.Strict(text) =>
//          Future.successful(text)
//      }.mapAsync(1)(identity)
//        .groupedWithin(1000, 1 second)
//        .map(messages =>
//          messages.foreach( msg =>
//            actorRef ! onMessage(msg)
//          )
//        )
//      val connected = upgradeResponse.flatMap { upgrade =>
//        if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
//          Future.successful(Done)
//        } else {
//          throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
//        }
//      }
//      // in a real application you would not side effect here
//      connected.onComplete(println)
//    val queue = Source
//      .queue[TextMessage](Int.MaxValue, OverflowStrategy.fail)
//      .via(Http().webSocketClientFlow(WebSocketRequest(wsURL)))
//      .to(Sink.actorRefWithAck(self, Init, Ack, Logout))
//      .run()
//    }
    case wsDisconnect(ref) => {
      println("called on child")
      ref ! test
//      replyTo ! test("suceess")
//      shut.success(None)
    }
  }
}
