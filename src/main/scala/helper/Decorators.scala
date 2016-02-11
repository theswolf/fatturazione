package helper

object Decorators {
  
  private val inputpattern = """<div class="form-group">
							 
							<label for="_pholder_">
								_pholder_
							</label>
							<input type="text" class="form-control" id="_pholder_">
						</div>"""
  
  implicit class inputDecorator(x:String)  {
    // def times[A](f: => A): Unit = {
    def draw(value:String=""):String = { 
       val regex = "_pholder_".r
       regex.replaceAllIn(inputpattern, x)
    }
  }
  
  
}