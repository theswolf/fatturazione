package webapp.controller

import helper.DBSession
import helper.Router
import helper.Serializer
import javax.persistence.MappedSuperclass
import spark.Request
import spark.Response
import spark.Spark.delete
import spark.Spark.get
import spark.Spark.post
import spark.Spark.put
import webapp.transformer.JSONTransformer
import helper.RESTUtils



object RESTController extends RESTUtils{
  
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
  
 
  
  
  
}