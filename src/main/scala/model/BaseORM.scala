package model

import javax.persistence.MappedSuperclass
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.GenerationType
import helper.DBSession
import org.hibernate.Session



@MappedSuperclass
abstract class BaseORM(){
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  var id:Long=0

  def save(implicit session:Session):BaseORM
}