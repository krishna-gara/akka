package models

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.policies.RoundRobinPolicy
import com.typesafe.config.ConfigFactory
import com.datastax.driver.core.Cluster.Builder

class CassandraRepo {
  val config = ConfigFactory.load()
  val hosts = config.getString("db.session.contactPoints").split(",")
  val port = config.getInt("db.session.port")
  val keySpace = config.getString("db.keyspace")

  val builder: Builder = Cluster.builder().withPort(port)
  hosts.foreach(e => {
    builder.addContactPoint(e)
  })
  //implicit val session = builder.withPort(port).withLoadBalancingPolicy(new RoundRobinPolicy).withClusterName("BioIngine").build.connect(keySpace)
  implicit val session = builder.withPort(port).build.connect(keySpace)
}
