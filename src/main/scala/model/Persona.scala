package model

import javax.persistence.Entity

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
   
}