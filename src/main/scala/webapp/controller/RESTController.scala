package webapp.controller

import spark.Spark._
import webapp.Fatturazione.setRoute
import helper.HibernateUtil
import model.BaseORM
import model.DatiFatturazione
import model.Prestazione
import model.Persona
import spark.Request
import spark.Response
import webapp.transformer.JSONTransformer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import helper.Serializer
import org.hibernate.Session
import helper.DBSession



object RESTController extends Serializer with DBSession{
  
  
  def mapModelToURL {
    
    get("/data/:model", setRoute { (req:Request,res:Response) => 
       withGet( req.params(":model"))}, new JSONTransformer)
       
    get("/data/:model/:id", setRoute { (req:Request,res:Response) => 
       withGet( req.params(":model"),req.params(":id"))}, new JSONTransformer)
    
    put("/data/:model", setRoute { (req:Request,res:Response) => 
       withPut( req.params(":model"), req)}, new JSONTransformer)
       
    post("/data/:model", setRoute { (req:Request,res:Response) => 
       withPost( req.params(":model"), req)}, new JSONTransformer)
       
    delete("/data/:model", setRoute { (req:Request,res:Response) => 
       withDelete( req.params(":model"), req)}, new JSONTransformer)
  }
  
  /*def getMapping(dataModel:String):Class = {
    dataModel value match {
      case datiFatturazione => classOf[DatiFatturazione]
      case prestazione => classOf[Prestazione]
      case persona => classOf[Persona]
    }[T <: BaseORM] 
  }*/
  
  def getMapping(dataModel:String):Class[_ <:BaseORM] = {
    dataModel  match {
      case "datiFatturazione" => classOf[DatiFatturazione]
      case "prestazione" => classOf[Prestazione]
      case "persona" => classOf[Persona]
    }
  }
  
  def withGet(dataModel:String):AnyRef = {
    
    implicit val sess = session()
    inSesssion(sess.createCriteria(getMapping {dataModel}).list())
    
  }
  
  def withGet(dataModel:String,id:String):AnyRef = {
    implicit val sess = session()
    inSesssion(sess.get(getMapping {dataModel}, id toLong))
  }
  
  def withPut(dataModel:String,req:Request):AnyRef = {
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
    inSesssion{sess.save {data} 
     data}
    //session.createCriteria(getMapping(dataModel)).list()
  }
  
  def withPost(dataModel:String,req:Request):AnyRef = {
    println("Received -> "+req.body)
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
     inSesssion{
      //sess.save{data} 
      data.save
      //sess.persist{data} 
    data}
    //session.createCriteria(getMapping(dataModel)).list()
  }
  
  def withDelete(dataModel:String,req:Request):AnyRef = {
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
    inSesssion{sess.delete {data} 
    data}
  
    //session.createCriteria(getMapping(dataModel)).list()
  }
  
}