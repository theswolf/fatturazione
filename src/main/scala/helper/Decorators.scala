package helper

object Decorators {
  
  private val inputpattern = """<div class="form-group">
							 
							<label for="_pholder_">
								_pholder_
							</label>
							<input type="text" class="form-control" id="_pholder_" value="_value_">
						</div>"""
  
  implicit class InputDecorator(x:String)  {
    // def times[A](f: => A): Unit = {
    def draw(value:String=""):String = { 
       val regex = "_pholder_".r
       val valRegex = "_value_".r
       regex.replaceAllIn(valRegex.replaceAllIn(inputpattern, value), x)
    }
  }
  
  
}