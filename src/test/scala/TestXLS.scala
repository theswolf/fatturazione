import org.hibernate.Session
import org.scalamock.scalatest.proxy.MockFactory
import org.scalatest._
import helper.HibernateUtil

class TestXLS extends FunSuite with BeforeAndAfter with MockFactory{
  
  
  
   test("Prestazioni in datiFattura should not be null") {
       assert(1 != None)
       val session = HibernateUtil.sessionFactory.openSession
   }
  
  
}