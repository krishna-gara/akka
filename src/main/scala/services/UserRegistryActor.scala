package services

//#user-registry-actor
import akka.actor.{Actor, ActorLogging, Props}
import models.UserModel

//#user-case-classes
final case class User(name: String, city: String)
final case class Users(users: Set[User])
//#user-case-classes

object UserRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetUsers
  def props: Props = Props[UserRegistryActor]
  val userModl:UserModel = new UserModel
}

class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._

  var users = Set.empty[User]
  def receive: Receive = {
    case GetUsers =>
      val users = userModl.getUsers
      sender() ! Users(users)
  }
}
//#user-registry-actor