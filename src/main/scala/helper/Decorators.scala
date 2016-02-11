package helper

object Decorators {
  
  val pattern = """"<div class="form-group">
							 
							<label for="_pholder_">
								_pholder_
							</label>
							<input type="text" class="form-control" id="_pholder_">
						</div>""""
  
  implicit class StringDecorator(x:String)  {
    def draw[A](f:A):String = { 
       val regex = "_pholder_".r
       regex.replaceAllIn(pattern, x)
    }
  }
  
  
}