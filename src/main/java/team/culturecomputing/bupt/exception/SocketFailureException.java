package team.culturecomputing.bupt.exception;

public class SocketFailureException extends ApiException{
	private static final long serialVersionUID = 1L;
	public SocketFailureException(String msg) {
		super(ApiExceptionCode.SOCKET_FAILURE.getValue(), msg);

 
	}
}
