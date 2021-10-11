package team.culturecomputing.bupt.exception;

public class NotIsImageException extends ApiException{
	private static final long serialVersionUID = 1L;
	 
	public NotIsImageException(String msg) {
		super(ApiExceptionCode.NUM_EXCEED_LIMIT.getValue(), msg);

 
	}
}
