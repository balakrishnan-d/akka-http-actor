import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.apache.kafka.clients.producer._

import java.util.{Properties, UUID}
import scala.concurrent.ExecutionContext
import scala.io.Source

object BookMain extends App with BookFormatter {

  val URL = getClass.getResource("application.properties")
  val props: Properties = new Properties()
  props.load(Source.fromURL(URL).bufferedReader())
  val host = props.getOrDefault("host", "0.0.0.0").asInstanceOf[String]
  val port = props.getOrDefault("port", 8080).toString.toInt
  implicit val system: ActorSystem = ActorSystem("BookAPI")
  implicit val executor: ExecutionContext = system.dispatcher

  val kafkaTopic: String = props.getOrDefault("kafka-topic", "test-pub-sub").toString
  var bookList: List[Book] = List[Book]()
  implicit val producerProps : Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:19092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  lazy val producer: KafkaProducer[String, String] = {
    new KafkaProducer[String, String](producerProps)
  }

  val bookRoute: Route = pathPrefix("book") {
    pathEnd {
      get {
        complete(bookList)
      } ~
      post {
        entity(as[Book]) { book =>
          val formattedBook = formatBook(book)
          writeToKafka(formattedBook.toString)
          addBook(formattedBook)
        }
      }
    } ~
    path(Segment) { id =>
      get {
        println(s"GET request with id - $id and book list is $bookList")
        val bookR : Option[Book] = bookList.find(_.id.get == id)
        println(s"found book is $bookR")
        complete(bookR)
      } ~
      delete {
        println(s"Del req received with id - $id")
        bookList = bookList.filterNot(_.id.get == id)
        complete("success")
      }
    }
  }
  def writeToKafka(msg: String): Unit = {
    val record = new ProducerRecord[String, String](kafkaTopic, "book", msg)
    producer.send(record)
  }

  def formatBook(book: Book): Book = {
    book.copy(id = book.id.orElse(Some(UUID.randomUUID().toString)))
  }

  def addBook(book: Book) = {
    bookList = bookList.:+(book)
    println(bookList.toString)
    complete("success")
  }

  Http().newServerAt(host, port).bind(bookRoute)
}
