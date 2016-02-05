package model

import org.joda.time.DateTime

import javax.persistence.Entity


@Entity
case class Prestazione (
   
     data:DateTime,
     rif:String,
     prestazione:String,
     gg:Int,
     euroUnit:Int,
     datiFatturaId:Long
    
) extends BaseORM{
 
   def euro():Double = {
     euroUnit*gg
   }
}