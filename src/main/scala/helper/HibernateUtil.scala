package helper

import scala.beans.BeanProperty

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {

  @BeanProperty
  val sessionFactory:SessionFactory = buildSessionFactory()

  private def buildSessionFactory(): SessionFactory = {
    new Configuration().configure().buildSessionFactory()
  }

  def shutdown() {
    getSessionFactory.close()
  }
}