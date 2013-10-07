package com.weixin.basic;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 证书类
 * 用于https请求
 * 
 */
public class Weixin_TrustManager implements X509TrustManager {
	
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}