package serializers

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import services.UserRegistryActor.ActionPerformed
import services.{User, Users}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  //# import the default encoders for primitive types (Int, String, Lists etc)
  //import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat2(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
//#json-support