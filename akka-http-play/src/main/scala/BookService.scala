import scala.concurrent.{ExecutionContext, Future}

class BookService(implicit val ec: ExecutionContext) {

  var bookList: List[Book] = List[Book]()

  def deleteBook(id: String): Unit = {
    bookList = bookList.filterNot(_.id.get == id)
  }
}
