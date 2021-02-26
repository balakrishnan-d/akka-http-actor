package com.cisco.ccbu

import akka.actor.{Actor, Props}
trait testMsg
case object A extends testMsg
case object B extends testMsg
case class C(s: String) extends testMsg
case class D(s: String) extends testMsg
trait testMsg1
case object AA extends testMsg1
case object BB extends testMsg1
class A1 extends Actor {

  override def receive:Receive = {
    case A =>
      println(s"received string A")
      val childB = context.actorOf(Props(new B1(self)), "childA")
      context.watch(childB)
      childB ! AA
    case B =>
      println(s"received B string B")
      val childC = context.actorOf(Props(new C1(self)), "childB")
      context.watch(childC)
      childC ! BB
    case C(s) =>
      println(s"Msg from Child A is - $s")
    case D(s) =>
      println(s"Msg from Child B is - $s")
  }
}
