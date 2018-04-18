package models

import com.datastax.driver.core.exceptions.{NoHostAvailableException, QueryValidationException}
import services.User

import scala.collection.JavaConverters._


class UserModel {
  val session = new CassandraRepo().session
  def getUsers : Set[User] = {
    val usersTmep= Set.empty[User]
      try{
        val rows = session.execute("SELECT * FROM users").all().asScala
        val usersTmep = rows.map({ row=>
          User(row.get("name", classOf[String]), row.get("city", classOf[String]))
        }).toSet
        usersTmep
      }catch{
        case e:QueryValidationException => println(e)
          usersTmep
      }
  }
}
