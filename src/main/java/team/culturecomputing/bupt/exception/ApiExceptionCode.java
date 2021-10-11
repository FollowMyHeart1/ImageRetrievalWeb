package team.culturecomputing.bupt.exception;

/*
 * 枚举错误码
 */
public enum ApiExceptionCode {
 
	NUM_EXCEED_LIMIT(991,"设置的topn参数不在0-200之间"),
	IMAGE_NOT_FOUND(992,"没有接收到待检索的图片"),
	NOT_IS_IMAGE(993,"传输的文件不是图片或不支持该格式的图片"),
	IMAGE_SIZE_LIMIT(994,"传输的图片太大"),
	SOCKET_FAILURE(995,"socket通信失败，请检查图片检索服务是否开启以及socket端口占用情况");
	private Integer value;
 
	private String desc;
 
	private ApiExceptionCode(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}
 
	public Integer getValue() {
		return value;
	}
 
	public void setValue(Integer value) {
		this.value = value;
	}
 
	public String getDesc() {
		return desc;
	}
 
	public void setDesc(String desc) {
		this.desc = desc;
	}
 
}