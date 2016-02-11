package helper

import org.hibernate.Session
import javax.persistence.Transient

trait DBSession {
  @Transient
  var session = () => HibernateUtil.sessionFactory.openSession
  
  def inSesssion(body:AnyRef)(implicit session:Session):AnyRef = {
    session.getTransaction.begin()
    val t = body
    session.getTransaction.commit()
    session.close()
    t
  }
}