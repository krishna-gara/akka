import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.ElasticDsl.indexInto
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.typesafe.config.ConfigFactory
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy

import scala.concurrent.Future
// you must import the DSL to use the syntax helpers
import com.sksamuel.elastic4s.http.ElasticDsl._


object HttpClientExampleApp extends App {


  val config = ConfigFactory.load()
  val client = HttpClient(ElasticsearchClientUri(config.getString("elasticsearch.hostname"), config.getInt("elasticsearch.port")))

  client.execute {
    bulk(
      //indexInto("myindex" / "mytype").fields("country" -> "Mongolia", "capital" -> "Ulaanbaatar"),
      //indexInto("myindex" / "mytype").fields("country" -> "Namibia", "capital" -> "Windhoek"),
      //indexInto("myindex" / "mytype").fields("country" -> "India", "capital" -> "Delhi"),
      //indexInto("myindex" / "mytype1").fields("country" -> "India", "capital" -> "Delhi"),
      indexInto("custom_errors" / "mytype1").fields("type" -> "AppError", "message" -> "[error] [C:\\Users\\kgara\\COE\\Akka\\akka-examples\\build.sbt]:17: identifier expected but something found.")
    ).refresh(RefreshPolicy.WAIT_UNTIL)
  }.await

  val result: SearchResponse = client.execute {
    search("myindex1").matchQuery("capital", "Delhi")
  }.await

  val resp = client.execute { search("custom_errors") matchQuery("message","expected") }.await
  println(resp.hits.hits foreach println )
  //prints out the original json

  client.close()
}