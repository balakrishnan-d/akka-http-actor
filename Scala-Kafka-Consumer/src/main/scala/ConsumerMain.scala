import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import java.util.Properties
import scala.jdk.CollectionConverters.IterableHasAsScala

object ConsumerMain {

  val kafkaTopic: String = "test-pub-sub";
  val consumerProperties: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:19092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    props
  }

  def main(args: Array[String]): Unit = {
    messageConsumer()
  }

  def messageConsumer() = {

    val consumer : KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProperties)
    consumer.subscribe(util.Arrays.asList(kafkaTopic))
    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator)
        println(data.value())
    }
  }
}
