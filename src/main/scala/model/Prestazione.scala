package model

import java.util.Date

import org.hibernate.Session
import org.joda.time.DateTime

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.FetchType


@Entity
case class Prestazione (
   
     @Column
     @Temporal(TemporalType.TIMESTAMP)
     data:Date,
     rif:String,
     prestazione:String,
     gg:Int,
     euroUnit:Int
    
) extends BaseORM{
  
  private def this() = this(DateTime.now.toDate(),"","",1,1)
  
  @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "datiFatturaId", nullable = false)
   var datiFattura = DatiFatturazione()
 
   def euro():Double = {
     euroUnit*gg
   }
  
     def save(implicit session:Session):Prestazione = {
        session.save(this)
        this
    }
}