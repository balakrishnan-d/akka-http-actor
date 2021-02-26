package com.cisco.ccbu

import akka.actor.{Actor, ActorRef, Props}

trait command
case class start() extends command
case class stop() extends command
case class onMessage(msg: String) extends command
case class test() extends command
class ParentActor extends Actor {


  override def receive: Receive = {
    case start => {
      println("received start command")
      val child = context.actorOf(Props(new childActor("ws://0.0.0.0:9900")), "child")
      child ! wsDisconnect(self)
    }
    case test =>
      println(s"received from child")
    case stop => {
      println("received stop command")
      // call child to stop
//      child ! wsDisconnect
    }
    case onMessage(msg) => {

      print("process command")
      println(s"message received from child is $msg")
      // process
    }
  }
}
