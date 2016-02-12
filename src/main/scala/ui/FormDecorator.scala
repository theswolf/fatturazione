package ui

import helper.Reflect
import model.BaseORM
import java.lang.reflect.Field
import java.util.Collection
import ui.InputDecorator._

object FormDecorator extends Reflect{
    implicit class FormDrawer(a:Array[Field]) {
    
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
}