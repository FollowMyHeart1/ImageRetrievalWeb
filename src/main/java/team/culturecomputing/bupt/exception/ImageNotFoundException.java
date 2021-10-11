package team.culturecomputing.bupt.exception;

public class ImageNotFoundException extends ApiException{
	private static final long serialVersionUID = 1L;
	 
	public ImageNotFoundException(String msg) {
		super(ApiExceptionCode.NUM_EXCEED_LIMIT.getValue(), msg);

 
	}
}
