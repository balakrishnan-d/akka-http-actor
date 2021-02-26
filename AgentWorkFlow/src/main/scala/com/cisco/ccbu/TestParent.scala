package com.cisco.ccbu

import akka.actor.{Actor, ActorRef, Props}
object someName {
  case object A
  case object B
}
trait one
case object callingParent extends one
trait two
case object calledParent extends two

object ChildMessage {
  case class callingChild()
}
//case class calledChild(ref: ActorRef)

class TestParent extends Actor {
  override def postRestart(reason: Throwable): Unit = {
    println(s"RESTARTING")
    println(reason)
    super.postRestart(reason)
  }
  override def receive: Receive = {
    case calledParent =>
      println("called from child. Reached")
    case callingParent =>
      println("you have reached parent")
      val child = context.actorOf(Props[TestChild], "child")
      child ! ChildMessage.callingChild
    case _ =>
      println("some msg")
  }
}
