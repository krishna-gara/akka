import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.ElasticDsl.indexInto
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.typesafe.config.ConfigFactory
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy

import scala.concurrent.Future
// you must import the DSL to use the syntax helpers
import com.sksamuel.elastic4s.http.ElasticDsl._

//# extending the error handling class to current method
object HttpClientExampleApp extends ErrorHandling with App {
    val config = ConfigFactory.load()
    this.pusherror(config.getString("elasticsearch.hostname"),config.getString("elasticsearch.port"),"DbEorr","[error] [C:\\\\Users\\\\kgara\\\\COE\\\\Akka\\\\akka-examples\\\\build.sbt]:17: identifier expected but something found.")
}

class ErrorHandling {
  //# method to push all data to elasticsearch
  def pusherror(ipAddres:String,port:String,errtype:String,message:String):String = {
    //# elasticsearch connection
    val client = HttpClient(ElasticsearchClientUri(ipAddres, port.toInt))
    try{
      //# elasticsearch insertion
      client.execute {
        bulk(
          indexInto("custom_errors" / "mytype1").fields("type" -> errtype, "message" -> message)
        ).refresh(RefreshPolicy.WAIT_UNTIL)
      }.await
      "Pushed to database"
    }catch{
      case e: Exception => e.printStackTrace()
      "Not pushed to database"
    } finally {
      client.close()
    }
  }
}