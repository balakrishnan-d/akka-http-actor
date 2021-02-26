package com.ccbu.sample
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.Behavior

object BookKeepActor {
  //sealed enforces all the classes extended to handled in behaviours
  sealed trait Op

  final case class GetBooks(replyTo: ActorRef[Books]) extends Op
  final case class AddBook(book: Book, replyTo: ActorRef[String]) extends Op
  final case class GetBook(bookID: String, replyTo: ActorRef[Option[Book]]) extends Op
  final case class DeleteBook(bookID: String, replyTo: ActorRef[String]) extends Op

  def apply(): Behavior[Op] = keeper(Set.empty)

  def keeper(books: Set[Book]): Behavior[Op]  = {
    Behaviors.receiveMessage {
      case GetBooks(replyTo) =>
        replyTo ! Books(books.toSeq)
        Behaviors.same
      case GetBook(bookID, replyTo) =>
        replyTo ! books.find(_.id.get == bookID)
        Behaviors.same
      case AddBook(book, replyTo) =>
        replyTo ! "Book Add Success"
        keeper(books + book)
      case DeleteBook(bookID, replyTo) =>
        replyTo ! "Book Delete Success"
        keeper(books.filterNot(_.id.get == bookID))
    }
  }
}
