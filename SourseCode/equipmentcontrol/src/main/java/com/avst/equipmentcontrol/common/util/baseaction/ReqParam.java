package com.avst.equipmentcontrol.common.util.baseaction;

/**
 * 请求的参数集合
 * @author wb
 *
 */
public class ReqParam<T> {


	private String version ;

	/**
	 * 请求的参数
	 */
	private T param;

	/**
	 * 请求时间
	 */
	private String reqtime;


	/**
	 * 请求的加密码
	 */
	private String token;



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getReqtime() {
		return reqtime;
	}

	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}

}
