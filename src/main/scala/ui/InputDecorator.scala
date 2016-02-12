package ui

object InputDecorator {
    
    implicit class FormInputDrawer(x:String)  {
      val inputpattern = """<div class="form-group">
							 
							<label for="_pholder_">
								_pholder_
							</label>
							<input type="text" class="form-control" id="_pholder_" value="_value_">
						</div>"""
    // def times[A](f: => A): Unit = {
    def draw(value:String=""):String = { 
       val regex = "_pholder_".r
       val valRegex = "_value_".r
       regex.replaceAllIn(valRegex.replaceAllIn(inputpattern, value), x)
    }
  }
  
}