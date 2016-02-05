package model

import javax.persistence.MappedSuperclass
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.GenerationType



@MappedSuperclass
class BaseORM() {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  var id:Long=0

}