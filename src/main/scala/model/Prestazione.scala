package model

import org.joda.time.DateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.FetchType


@Entity
case class Prestazione (
   
     data:DateTime,
     rif:String,
     prestazione:String,
     gg:Int,
     euroUnit:Int
    
) extends BaseORM{
  
  @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "datiFatturaId", nullable = false)
   var datiFattura = DatiFatturazione()
 
   def euro():Double = {
     euroUnit*gg
   }
}