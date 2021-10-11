package team.culturecomputing.bupt.exception;

public class ImageSizeLimitException extends ApiException{
	private static final long serialVersionUID = 1L;
	 
	public ImageSizeLimitException(String msg) {
		super(ApiExceptionCode.IMAGE_SIZE_LIMIT.getValue(), msg);

 
	}
}
