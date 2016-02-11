package helper

import spark.Route
import spark.Request
import spark.Response
import model.BaseORM
import model.Prestazione
import model.DatiFatturazione
import model.Persona
import org.eclipse.jetty.util.Fields
import java.lang.reflect.Field
import java.util.Collection


trait Reflect {
  
  def extractFields[T<:Product:Manifest]:Array[Field] = {
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields
  }
  

  

   
   def getFields(dataModel:String):Array[Field] = {
    dataModel  match {
      case "datiFatturazione" => extractFields[DatiFatturazione]
      case "prestazione" => extractFields[Prestazione]
      case "persona" => extractFields[Persona]
    }
  }
  
}