package com.ccbu.sample

import akka.actor.typed.ActorSystem
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.ccbu.sample.BookKeepActor._

import scala.concurrent.Future
import java.util.UUID
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration

class BookRouter(bookKeeper: ActorRef[BookKeepActor.Op]) (implicit val system: ActorSystem[_]) extends BookFormatter {

  private implicit val timeout = Timeout(Duration.create(3, TimeUnit.SECONDS))

  def getBooks(): Future[Books] =
    bookKeeper.ask(GetBooks(_))
  def getBook(bookID: String) : Future[Option[Book]] =
    bookKeeper.ask(GetBook(bookID, _))
  def addBook(book: Book) : Future[String] =
    bookKeeper.ask(AddBook(book, _))
  def deleteBook(bookID: String): Future[String] =
    bookKeeper.ask(DeleteBook(bookID, _))
  def formatBook(book: Book): Book = {
    book.copy(id = book.id.orElse(Some(UUID.randomUUID().toString)))
  }

  val bookRoute: Route = pathPrefix("book") {
    pathEnd {
      get {
        onSuccess(getBooks()) { response =>
          complete(response.books)
        }
      } ~
        post {
          entity(as[Book]) { book =>
            val formattedBook = formatBook(book)
            onSuccess(addBook(formattedBook)) { performed =>
              complete((StatusCodes.Created, performed))
            }
          }
        }
    } ~
      path(Segment) { id =>
        get {
          rejectEmptyResponse {
            onSuccess(getBook(id)) { response =>
              complete(response)
            }
          }
        } ~
          delete {
            onSuccess(deleteBook(id)) { response =>
              complete((StatusCodes.Created, response))
            }
          }
      }
  }
}
