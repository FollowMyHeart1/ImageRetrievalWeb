package team.culturecomputing.bupt.exception;

public class NumExceedLimitException extends ApiException{
		private static final long serialVersionUID = 1L;
	 
		public NumExceedLimitException(String msg) {
			super(ApiExceptionCode.NOT_IS_IMAGE.getValue(), msg);

	 
		}
}