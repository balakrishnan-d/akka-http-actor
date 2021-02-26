package com.ccbu.sample

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait BookFormatter extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val bookFormat = jsonFormat3(Book);
}
