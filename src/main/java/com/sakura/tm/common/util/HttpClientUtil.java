package com.sakura.tm.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李七夜
 */
public class HttpClientUtil {
	private static final String charSet = "UTF-8";
	/** https协议的请求客户端 */
	private static CloseableHttpClient httpsClient = null;

	/** http协议的请求客户端 */
	private static CloseableHttpClient httpClient = null;

	private static final Log log = LogFactory.getLog(HttpClientUtil.class);

	private static final Lock lockHttpsClient = new ReentrantLock();

	private static final Lock lockHttpClient = new ReentrantLock();

	static {
		// 初始化客户端
		getHttpClient();
		getHttpsClient();
	}

	/**
	 * 获取https的请求客户端、多服务不做任务验证
	 * @return
	 */
	private static CloseableHttpClient getHttpsClient() {
		if (httpsClient == null) {
			try {
				lockHttpsClient.lock();
				if (httpsClient == null) {
					SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
						// 信任所有
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							return true;
						}
					}).build();
					HostnameVerifier hostnameVerifier = new HostnameVerifier() {
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							// 对服务器不做验证
							return true;
						}
					};
					SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
					RequestConfig.Builder builder = RequestConfig.custom();
					// 单位毫秒
					builder.setConnectTimeout(5000);
					builder.setSocketTimeout(5000);
					httpsClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
				}
			} catch (KeyManagementException e) {
				log.error(e);
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			} catch (KeyStoreException e) {
				log.error(e);
			} finally {
				lockHttpsClient.unlock();
			}
		}
		return httpsClient;
	}

	/**
	 * 获取http的客户端
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			try {
				lockHttpClient.lock();
				if (httpClient == null) {
					Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
							.register("http", PlainConnectionSocketFactory.INSTANCE)
							.build();
					PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
					connManager.setMaxTotal(200);
					connManager.setDefaultMaxPerRoute(20);

					httpClient = HttpClients.custom()
							.setConnectionManager(connManager)
							.setDefaultRequestConfig(getRequestConfig(300000))
							.build();
				}
			} finally {
				lockHttpClient.unlock();
			}
		}
		return httpClient;
	}

	private static RequestConfig getRequestConfig(int timeout) {
		return RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.build();
	}

	/**
	 * 获取http的客户端
	 * @return
	 */
	private static CloseableHttpClient getHttpClient(int timeout) {
		RequestConfig.Builder builder = RequestConfig.custom();
		// 单位毫秒 默认3分钟
		builder.setConnectTimeout(timeout);
		builder.setSocketTimeout(timeout);
		HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultRequestConfig(builder.build());
		return clientBuilder.build();
	}

	/**
	 * 执行post请求
	 * @param nameValuePairs 请求参数、不能为空
	 * @param url 请求url 不能为空
	 * @param charSet 请求字符编码 不能为空
	 * @return 返回HttpResponseDTO {request :true表示http请求成功、false 表示http请求失败。reponseCode：表示请求响应的http代码如200，reponseResult：响应数据}
	 */
	public static HttpResponseDTO executePost(final List<NameValuePair> nameValuePairs, List<Header> headers, final String url, final String charSet) {
		Assert.notNull(nameValuePairs, "nameValuePairs must not null");
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPost postMethod = new HttpPost(url);

			StringBuffer stringBuffer = new StringBuffer();
			for (NameValuePair nameValuePair : nameValuePairs) {
				stringBuffer.append(nameValuePair.getName()).append("=").append(URLEncoder.encode(nameValuePair.getValue(), "utf-8"));
			}
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, charSet);
			// urlEncodedFormEntity.setContentEncoding(charSet);
			postMethod.setEntity(urlEncodedFormEntity);
			// postMethod.setHeader(new BasicHeader("Content-Type", "application/json"));
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					postMethod.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(postMethod);
			} else {
				response = getHttpClient().execute(postMethod);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// TODO 去掉
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	public static HttpResponseDTO executeGet(List<Header> headers, final String url, final String charSet) {
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					httpGet.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(httpGet);
			} else {
				response = getHttpClient().execute(httpGet);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			// TODO 去掉
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	public static HttpResponseDTO executeGet(List<Header> headers, final String url, final String charSet, int timeout) {
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					httpGet.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(httpGet);
			} else {
				response = getHttpClient(timeout).execute(httpGet);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// TODO 去掉
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	public static HttpResponseDTO executeGet(List<Header> headers, final String url) {
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					httpGet.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(httpGet);
			} else {
				response = getHttpClient().execute(httpGet);
			}
			result = executeResponse(response, "utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			// TODO 去掉
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}


	public static HttpResponseDTO executePut(List<NameValuePair> nameValuePairs, List<Header> headers, final String url, final String charSet) {
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPut httpPut = new HttpPut(url);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					httpPut.addHeader(header);
				}
			}
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, charSet);
			httpPut.setEntity(urlEncodedFormEntity);
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(httpPut);
			} else {
				response = getHttpClient().execute(httpPut);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}


	/**
	 * 执行post请求(直接提交字符串数据)
	 * @param content 请求数据
	 * @param url 请求url 不能为空
	 * @param charSet 请求字符编码 不能为空
	 * @return 返回HttpResponseDTO {request :true表示http请求成功、false 表示http请求失败。reponseCode：表示请求响应的http代码如200，reponseResult：响应数据}
	 */
	public static HttpResponseDTO executePost(final String content, List<Header> headers, final String url, final String charSet) {
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPost postMethod = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(content, charSet);
			stringEntity.setContentType("application/json;charset=utf-8");
			postMethod.setEntity(stringEntity);
			//客户端使用短连接
			postMethod.setProtocolVersion(HttpVersion.HTTP_1_0);
			postMethod.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					postMethod.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(postMethod);
			} else {
				response = getHttpClient().execute(postMethod);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	private static RequestConfig getRequestConfig1(int timeout) {
		return RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.build();
	}

	public static HttpResponseDTO executePostForTax(final String content, List<Header> headers, final String url, final String charSet) {
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPost postMethod = new HttpPost(url);
			postMethod.setConfig(getRequestConfig1(5000));
			StringEntity stringEntity = new StringEntity(content, charSet);
			stringEntity.setContentType("application/json;charset=utf-8");
			postMethod.setEntity(stringEntity);
			//客户端使用短连接
			postMethod.setProtocolVersion(HttpVersion.HTTP_1_0);
			postMethod.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					postMethod.addHeader(header);
				}
			}
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(postMethod);
			} else {
				response = getHttpClient().execute(postMethod);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	public static HttpResponseDTO postHttpsRestfulAPI(String url, String json, final String charSet) {
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPost postMethod = new HttpPost(url);

			postMethod.addHeader("Content-Type", "application/json;charset=utf-8");
			HttpEntity item = new ByteArrayEntity(json.getBytes("UTF-8"));
			postMethod.setEntity(item);
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(postMethod);
			} else {
				response = getHttpClient().execute(postMethod);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;

	}

	public static HttpResponseDTO postHttpsRestfulAPI(String url, List<Header> headers, String json) {
		Assert.notNull(url, "url must not null");
		Assert.notNull(charSet, "charSet must not null");
		HttpResponseDTO result = new HttpResponseDTO();
		CloseableHttpResponse response = null;
		try {
			HttpPost postMethod = new HttpPost(url);
			postMethod.addHeader("Content-Type", "application/json;charset=utf-8");
			if (!CollectionUtils.isEmpty(headers)) {
				for (Header header : headers) {
					postMethod.addHeader(header);
				}
			}
			HttpEntity item = new ByteArrayEntity(json.getBytes(charSet));
			postMethod.setEntity(item);
			// 根据url 区分请求协议
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				response = getHttpsClient().execute(postMethod);
			} else {
				response = getHttpClient().execute(postMethod);
			}
			result = executeResponse(response, charSet);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;

	}

	private static HttpResponseDTO executeResponse(CloseableHttpResponse response, String charSet) {
		HttpResponseDTO result = new HttpResponseDTO();
		result.setReponseCode(response.getStatusLine().getStatusCode());
		HttpEntity responseBody = response.getEntity();
		try {
			String respon = EntityUtils.toString(responseBody, charSet);
			result.setReponseResult(respon);
			EntityUtils.consume(responseBody);
			result.setRequestSuccess(true);
			Header[] tokenHeader = response.getAllHeaders();
		} catch (ParseException | IOException e) {
			log.error(e);
		}
		return result;
	}

	public static class HttpResponseDTO {

		/** 请求是否成功 */
		private boolean requestSuccess = false;

		/** 响应结果 */
		private String reponseResult;

		/** http响应代码 */
		private int reponseCode;


		public boolean isRequestSuccess() {
			return requestSuccess;
		}

		public void setRequestSuccess(boolean request) {
			this.requestSuccess = request;
		}

		public String getReponseResult() {
			return reponseResult;
		}

		public void setReponseResult(String reponseResult) {
			this.reponseResult = reponseResult;
		}

		public int getReponseCode() {
			return reponseCode;
		}

		public void setReponseCode(int reponseCode) {
			this.reponseCode = reponseCode;
		}

	}

	public static void main(String[] args) {
		String url = "https://api-test.weifengqi18.com/wuneng-web-api/api/digital/getTaxVatDeclarationList?interface_code=e559ce0a20a44ed99bc7321c33d85ee25urq8&taxpayer_id=91330106MA2CFGY2XC&notice_id=weifengqi_notice_02_3896&secret_key=2TT0lDD5gB7mxxI5mDi6iSCH5Nl6aPRElFCmks%2Bc%2F2M3XZQTt2XwSH5hvtjrqWdv";
		HttpResponseDTO httpResponseDTO = executeGet(null, url);
		System.out.println(JSON.toJSON(httpResponseDTO.reponseResult));
	}


}
