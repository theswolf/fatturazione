package model

object DatiFattura {
  def apply(prestazioni:Set[Prestazione]) = {
    val d = new DatiFattura
    d.prestazioni=prestazioni
    d
  }
}

class DatiFattura() {
  var prestazioni:Set[Prestazione] = Set()
}