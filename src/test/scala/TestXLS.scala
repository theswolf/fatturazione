import org.hibernate.Session
import org.joda.time.DateTime
import org.scalamock.scalatest.proxy.MockFactory
import org.scalatest._
import com.google.gson.Gson
import dispatch._
import Defaults._
import helper.HibernateUtil
import model.DatiFatturazione
import webapp.Fatturazione
import model.Prestazione
import scala.collection.mutable.Buffer
import webapp.transformer.LocalDateSerializer
import com.google.gson.GsonBuilder
import webapp.transformer.LocalTimeSerializer
import org.joda.time.LocalDate
import org.joda.time.LocalTime

class TestXLS extends FunSuite with BeforeAndAfter with MockFactory{
  
  val baseUrl = "http://localhost:5555/"
    
  val gson: Gson = (new GsonBuilder())
       .registerTypeAdapter(classOf[LocalDate], new LocalDateSerializer())
       .registerTypeAdapter(classOf[LocalTime], new LocalTimeSerializer())
       .create()
  
  before {
      Fatturazione main Array("") 
  }
  
  after {
    //Fatturazione break
  }
  
   test("Prestazioni in datiFattura should not be null") {
        val session = HibernateUtil.sessionFactory.openSession
     
      val dfUrl = url(baseUrl+"data/datiFatturazione")
      dfUrl.setContentType("application/json", "UTF-8")
      val df = DatiFatturazione(DateTime.now,1,"rif",8,"scaduta")
      df.insertPrestazioni(Seq(Prestazione(DateTime.now,"rif Prest","description",10,210)))
      println(gson.toJson(df))
      val post = dfUrl << gson.toJson(df)
      
      val response = Http(post)
      println(response().getResponseBody)
      //Http(dfUrl OK as.String)
      
      assert(1 == scala.collection.JavaConversions.asScalaBuffer(session.createCriteria(classOf[DatiFatturazione]).list()).size)
      
   }
  
  
}