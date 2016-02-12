package helper

import java.util.Collection
import java.lang.reflect.Field
import helper.Decorators._
import scala.reflect.runtime.{universe => ru}
import model.BaseORM

object UIDrawer {
  
  val form = """<div class="row">
				          <div class="col-md-6">
					          <form role="form">
                      _theform_
                      <button type="submit" class="btn btn-default">
							          Submit
						          </button>
					        </form>
				        </div>
              </div>"""
  
  implicit class FieldDrawer(a:Array[Field]) {
    def draw(data:Option[AnyRef]):Array[String]={
        a.map( field => field.getType match {
         case c if getType(c).<:<(getType(classOf[Collection[Any]]))  => "coll of "+field.getName 
         case _ => { 
            data match {
              case None =>  field.getName draw ""
              case d if classOf[BaseORM].isAssignableFrom(d.get.getClass) => {
                try {
                  val method = d.get.getClass.getDeclaredMethod(field.getName)
                  val value = method.invoke(d.get).toString()
                  field.getName draw value 
                }
                catch {
                  case t: Throwable => {
                    t.printStackTrace() 
                    field.getName draw ""
                  }// TODO: handle error
                }
                 
              }
              case _ => field.getName draw ""
           }
          
         }
       })
    }
    
    
    def renderForm(implicit data:Option[AnyRef]):String = {
      val regex = "_theform_".r
       regex.replaceAllIn(form,  (a draw data).mkString("\n"))
    }
    
   }
  
  
    
   def getType[T](clazz: Class[T]): ru.Type = {
      val runtimeMirror = ru.runtimeMirror(clazz.getClassLoader)
    runtimeMirror.classSymbol(clazz).toType
  }
}