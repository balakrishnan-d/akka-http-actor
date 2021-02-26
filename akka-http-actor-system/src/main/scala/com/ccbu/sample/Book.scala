package com.ccbu.sample
import scala.collection.immutable

final case class Book(id: Option[String] = None, name: String, author: String)
final case class Books(books: immutable.Seq[Book])