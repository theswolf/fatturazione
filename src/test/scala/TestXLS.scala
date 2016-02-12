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
import com.google.gson.GsonBuilder
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import helper.Serializer

class TestXLS extends FunSuite with BeforeAndAfter with MockFactory with Serializer{
  
  val baseUrl = "http://localhost:5555/"
    

  
  before {
      Fatturazione main Array("") 
  }
  
  after {
    //Fatturazione break
  }
  
  test("Rendering data") {
    
    val dfUrl = url(baseUrl+"data/datiFatturazione")
      dfUrl.setContentType("application/json", "UTF-8")
      val df = DatiFatturazione(DateTime.now.toDate(),1,"rif",8,"scaduta")
      df.insertPrestazioni(Seq(Prestazione(DateTime.now.toDate,"rif Prest","description",10,210)))
      //println(gson.toJson(df))
      val post = dfUrl << gson.toJson(df)
      
      val result = Http(post OK as.String)
      val body = result()
      println("response of post is")
      println(body)
      
      val dfUrlP = url(baseUrl+"ui/form/datiFatturazione/1")
      dfUrlP.setContentType("application/json", "UTF-8")
      //val dfp = DatiFatturazione(DateTime.now.toDate(),1,"rif",8,"scaduta")
      //dfp.insertPrestazioni(Seq(Prestazione(DateTime.now.toDate,"rif Prest","description",10,210)))
      //println(gson.toJson(df))
      //val post2 = dfUrlP << gson.toJson(dfp)
      
      val result2 = Http(dfUrlP OK as.String)
      val body2 = result2()
      println("response of post2 is")
      println(body2)
    
  }
  
   test("Prestazioni in datiFattura should not be null") {
               val session = HibernateUtil.sessionFactory.openSession
     
      val dfUrl = url(baseUrl+"data/datiFatturazione")
      dfUrl.setContentType("application/json", "UTF-8")
      val df = DatiFatturazione(DateTime.now.toDate(),1,"rif",8,"scaduta")
      df.insertPrestazioni(Seq(Prestazione(DateTime.now.toDate,"rif Prest","description",10,210)))
      //println(gson.toJson(df))
      val post = dfUrl << gson.toJson(df)
      
      val result = Http(post OK as.String)
      val body = result()
      println("response of post is")
      println(body)
      
      val dfGETUrl = url(baseUrl+"data/datiFatturazione")
      val get =  Http(dfGETUrl OK as.String)
      val bodyGet = get()
       println("response of get is")
      println(bodyGet)
      
       val dfGETUrlP = url(baseUrl+"data/prestazione")
      val getP =  Http(dfGETUrlP OK as.String)
      val bodyGetP = getP()
       println("response of get p is")
      println(bodyGetP)
      
        
      
      //Http(dfUrl OK as.String)
      
    
      
   }
   
    test("OneToManyIsMapped") {
               val session = HibernateUtil.sessionFactory.openSession
     
      val dfUrl = url(baseUrl+"data/datiFatturazione")
      dfUrl.setContentType("application/json", "UTF-8")
      val df = DatiFatturazione(DateTime.now.toDate(),1,"rif",8,"scaduta")
      df.insertPrestazioni(Seq(Prestazione(DateTime.now.toDate,"rif Prest","description",10,210)))
      //println(gson.toJson(df))
      val post = dfUrl << gson.toJson(df)
      
      val result = Http(post OK as.String)
      val body = result()
      println("response of post is")
      println(body)
      
      val dfGETUrl = url(baseUrl+"data/datiFatturazione/1/prestazione")
      val get =  Http(dfGETUrl OK as.String)
      val bodyGet = get()
       println("response of get is")
      println(bodyGet)
     
      
        
      
      //Http(dfUrl OK as.String)
      
    
      
   }
  
  
}