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
import scala.reflect.runtime.{universe => ru}


trait Reflect {
  
  def extractFields[T<:Product:Manifest]:Array[Field] = {
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields
  }
  

  

   
   def getFields(dataModel:String):Array[Field] = {
    dataModel  match {
      case "datiFatturazione" => extractFields[DatiFatturazione]
      case "prestazione" => extractFields[Prestazione]
      case "persona" => extractFields[Persona]
      case _ => throw new Exception("No mapped model for "+dataModel)
    }
  }
   
    def getType[T](clazz: Class[T]): ru.Type = {
      val runtimeMirror = ru.runtimeMirror(clazz.getClassLoader)
    runtimeMirror.classSymbol(clazz).toType
  }
  
}