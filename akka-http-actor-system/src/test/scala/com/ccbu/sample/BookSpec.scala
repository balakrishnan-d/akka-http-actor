package com.ccbu.sample

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.DurationInt

class BookSpec extends AnyWordSpec with Matchers with ScalaFutures with ScalatestRouteTest with BookFormatter {

  lazy val testKitInstance = ActorTestKit()
  implicit def sys = testKitInstance.system
  val bookActor = testKitInstance.spawn(BookKeepActor())
  lazy val route =  new BookRouter(bookActor).bookRoute

  "BookPath" should {
    "return no books if not present" in {
      Get(uri = "/book") ~> route ~> check {
        status shouldBe StatusCodes.OK
        entityAs[String] shouldBe "[]"
      }
    }
    "add book to the list" in {
      val book: Book = Book(Some("1234"), "PeppaPig", "NotFound")
      val entity = Marshal(book).to[MessageEntity].futureValue
      Post("/book").withEntity(entity) ~> route ~> check {
        status shouldBe StatusCodes.Created
      }
    }
    "get book with id returning a book" in {
      HttpRequest(uri = "/book/1234") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[Book].author shouldBe "NotFound"
        responseAs[Book].name shouldBe "PeppaPig"
      }
    }
  }
}
