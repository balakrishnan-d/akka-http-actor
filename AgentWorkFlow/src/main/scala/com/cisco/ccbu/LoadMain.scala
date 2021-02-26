package com.cisco.ccbu

import akka.actor.{ActorSystem, Props}
import akka.Done
import akka.http.scaladsl.Http
import akka.stream.scaladsl._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws._

import java.util.UUID
import scala.concurrent.{Future, Promise}

object LoadMain extends App {
//  testActors
  testParent
  def testParent() = {
    val system = ActorSystem("A1")
    val p = system.actorOf(Props[A1], "parent")
    p ! A
    p ! B
//    p ! B
  }
  def testActors() = {
    val system = ActorSystem("ParentChild")
    val p = system.actorOf(Props[TestParent], "parent")
    p ! callingParent
  }

//  def loadActor() = {
//    val system = ActorSystem("ParentChild")
//    import system.dispatcher
//    val parentActor = system.actorOf(Props[ParentActor], name = "parent")
//    parentActor ! start
//  }
//  def anotherLoad() = {
//    implicit val system = ActorSystem()
//    import system.dispatcher
//
//    // Future[Done] is the materialized value of Sink.foreach,
//    // emitted when the stream completes
//    val incoming: Sink[Message, Future[Done]] =
//    Sink.foreach[Message] {
//      case message: TextMessage.Strict =>
//        println("received message")
//        println(message.text)
//      case _ =>
//      // ignore other message types
//    }
//
//    // send this as a message over the WebSocket
//    val outgoing = Source.single(TextMessage("hello world Hi HI HI!"))
//
//    // flow to use (note: not re-usable!)
//    val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest("ws://0.0.0.0:9900"))
//
//    // the materialized value is a tuple with
//    // upgradeResponse is a Future[WebSocketUpgradeResponse] that
//    // completes or fails when the connection succeeds or fails
//    // and closed is a Future[Done] with the stream completion from the incoming sink
//    val (upgradeResponse, closed) =
//    outgoing
//      .viaMat(webSocketFlow)(Keep.right) // keep the materialized Future[WebSocketUpgradeResponse]
//      .toMat(incoming)(Keep.both) // also keep the Future[Done]
//      .run()
//
//    // just like a regular http request we can access response status which is available via upgrade.response.status
//    // status code 101 (Switching Protocols) indicates that server support WebSockets
//    val connected = upgradeResponse.flatMap { upgrade =>
//      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
//        Future.successful(Done)
//      } else {
//        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
//      }
//    }
//
//    // in a real application you would not side effect here
//    connected.onComplete(println)
//    closed.foreach(_ => println("closed"))
//  }
//  def startLoad(): Unit = {
//
//    implicit val sys = ActorSystem()
//    implicit val ex = sys.dispatcher
//    val flow: Flow[Message, Message, Promise[Option[Message]]] =
//      Flow.fromSinkAndSourceMat(
//        Sink.foreach[Message](msg => println(msg.asTextMessage)),
//        Source.maybe[Message])(Keep.right)
//
//    val (upgradeResponse, promise) =
//      Http().singleWebSocketRequest(
//        WebSocketRequest("ws://0.0.0.0:9900"),
//        flow)
//    val connected = upgradeResponse.flatMap { upgrade =>
//      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
//        Future.successful(Done)
//      } else {
//        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
//      }
//    }
//
//    // in a real application you would not side effect here
//    connected.onComplete(println)
////    promise.success(None)
//  }

}
