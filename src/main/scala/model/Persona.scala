package model

import javax.persistence.Entity
import org.hibernate.Session

@Entity
case class Persona(
     sigla:String,
     denominazione:String,
     via:String,
     indirizzo:String,
     cap:String,
     pIva:String,
     codFiscale:String=""

  ) extends BaseORM{
  
  private def this() = this("","","","","","")
  
   def save(implicit session:Session):Persona = {
        session.save(this)
        this
    }
   
}