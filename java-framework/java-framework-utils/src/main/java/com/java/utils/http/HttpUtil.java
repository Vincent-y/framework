package com.java.utils.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>同步HTTP CLIENT
 * <p>@author dragon
 * <p>2015年8月24日
 * <p>@version 1.0
 */
public class HttpUtil {


		private static int CONNECTION_TIME = 1000 * 30;//30 seconds
		private static String APPLICATION_JSON = "application/json";

		private static CloseableHttpClient httpClientWithAuth(String username, String password) {
			// 创建HttpClientBuilder
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			// 设置BasicAuth
			CredentialsProvider provider = new BasicCredentialsProvider();
			// Create the authentication scope
			AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
			// Create credential pair，在此处填写用户名和密码
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			// Inject the credentials
			provider.setCredentials(scope, credentials);
			// Set the default credentials provider
			httpClientBuilder.setDefaultCredentialsProvider(provider);
			//HttpClient
			CloseableHttpClient httpClient = httpClientBuilder.build();
			return httpClient;
		}


		private static CloseableHttpClient httpClient() {
			// 创建HttpClientBuilder
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			//HttpClient
			CloseableHttpClient httpClient = httpClientBuilder.build();
			return httpClient;
		}


		public static String doGetWithAuth(String username, String password, String url, int timeOut, Map<String, String> headers) {
			String result = "";
			CloseableHttpClient httpClient = null;
			HttpGet httpGet = null;
			HttpEntity entity = null;
			CloseableHttpResponse httpResponse = null;

			if (timeOut == 0) {
				timeOut = CONNECTION_TIME;
			}

			try {
				httpClient = httpClientWithAuth(username, password);
				httpGet = new HttpGet(url);

				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
				httpGet.setConfig(requestConfig);

				if (headers != null) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext(); ) {
						String key = it.next().toString();
						httpGet.addHeader(key, headers.get(key));
					}
				}

				httpResponse = httpClient.execute(httpGet);

				if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpGet.abort();
					httpClient.close();
					System.out.println(EntityUtils.toString(httpResponse.getEntity()));
					return result;
				}


				entity = httpResponse.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (httpClient != null) {
					try {
						httpResponse.close();
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}



		/**
		 * get请求
		 * @param url
		 * @param timeOut
		 * @return
		 */
		public static String doGet(String url, int timeOut,Map<String, String> headers) {
			String result = "";
			HttpGet httpGet = null;
			CloseableHttpResponse response = null;
			HttpEntity entity = null;
			CloseableHttpClient httpClient = null;
			try {
				if (timeOut == 0) {
					timeOut = CONNECTION_TIME;
				}
				if (null != headers) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext();) {
						String key = it.next().toString();
						httpGet.addHeader(key, headers.get(key));
					}
				}

				httpClient = httpClient();
				httpGet = new HttpGet(url);

				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
				httpGet.setConfig(requestConfig);


				response = httpClient.execute(httpGet);

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpGet.abort();
					httpClient.close();
					return result;
				}

				entity = response.getEntity();
				if(entity != null){
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (httpClient != null) {
					try {
						httpClient.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
				if (response != null) {
					try {
						response.close();
					} catch (IOException e) {
						System.out.println(e);
					}
				}
			}
			return result;
		}


		/**
		 * post json
		 * @param url
		 * @param json
		 * @param timeOut
		 * @return
		 * @throws Exception
		 */
		public static String doPostWithJSONWithAuth(String username, String password, String url, String json , int timeOut,Map<String, String> headers) {
			String result = "";

			CloseableHttpResponse response = null;
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			HttpEntity entity = null;


			try {
				httpClient = httpClientWithAuth(username,password);
				httpPost = new HttpPost(url);

				if (timeOut == 0) {
					timeOut = CONNECTION_TIME;
				}
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
				httpPost.setConfig(requestConfig);

				httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
				if (null != headers) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext();) {
						String key = it.next().toString();
						httpPost.addHeader(key, headers.get(key));
					}
				}

				//内容处理
				StringEntity se = new StringEntity(json,Charset.defaultCharset());//处理中文乱码
				se.setContentType(APPLICATION_JSON);
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,APPLICATION_JSON));
				httpPost.setEntity(se);


				//发出请求
				response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpPost.abort();
					httpClient.close();
					return result;
				}

				entity = response.getEntity();
				if(entity != null){
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return result;
		}


		/**
		 * post 表单
		 * @param url
		 * @param params
		 * @param timeOut
		 * @return
		 */
		public static String doPost(String username, String password, String url,Map<String, String> params,int timeOut,Map<String, String> headers){
			String result = "";

			CloseableHttpResponse response = null;
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			HttpEntity entity = null;

			try {

				httpClient = httpClientWithAuth(username,password);
				httpPost = new HttpPost(url);

				//超时处理
				if (timeOut == 0) {
					timeOut = CONNECTION_TIME;
				}

				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
				httpPost.setConfig(requestConfig);

				if (null != headers) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext();) {
						String key = it.next().toString();
						httpPost.addHeader(key, headers.get(key));
					}
				}

				// 创建参数队列
				List<NameValuePair> formparams = null;
				if (null !=params && params.size()>0) {
					formparams = new ArrayList<NameValuePair>();
					for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
						formparams.add(new BasicNameValuePair(iterator.next(), params.get(iterator.next())));
					}
				}

				entity  = new UrlEncodedFormEntity(formparams,  Charset.defaultCharset());
				httpPost.setEntity(entity);


				response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpPost.abort();
					httpClient.close();
					return result;
				}

				entity = response.getEntity();
				if(entity != null){
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return result;
		}




		/**
		 * post json
		 * @param url
		 * @param json
		 * @param timeOut
		 * @return
		 * @throws Exception
		 */
		public static String doPostWithJSON(String url, String json , int timeOut,Map<String, String> headers) {
			String result = "";

			CloseableHttpResponse response = null;
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			HttpEntity entity = null;


			try {
				httpClient = httpClient();
				httpPost = new HttpPost(url);

				if (timeOut == 0) {
					timeOut = CONNECTION_TIME;
				}
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
				httpPost.setConfig(requestConfig);

				httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
				if (null != headers) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext();) {
						String key = it.next().toString();
						httpPost.addHeader(key, headers.get(key));
					}
				}

				//内容处理
				StringEntity se = new StringEntity(json,Charset.defaultCharset());//处理中文乱码
				se.setContentType(APPLICATION_JSON);
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,APPLICATION_JSON));
				httpPost.setEntity(se);


				//发出请求
				response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpPost.abort();
					httpClient.close();
					return result;
				}

				entity = response.getEntity();
				if(entity != null){
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(response != null){
						response.close();
					}
					if(httpClient != null){
						httpClient.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return result;
		}


		/**
		 * post 表单
		 * @param url
		 * @param params
		 * @param timeOut
		 * @return
		 */
		public static String doPost(String url,Map<String, String> params,int timeOut,Map<String, String> headers){
			String result = "";

			CloseableHttpResponse response = null;
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			HttpEntity entity = null;

			try {

				httpClient = httpClient();
				httpPost = new HttpPost(url);

				//超时处理
				if (timeOut == 0) {
					timeOut = CONNECTION_TIME;
				}

				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
				httpPost.setConfig(requestConfig);

				if (null != headers) {
					for (Iterator<String> it = headers.keySet().iterator(); it.hasNext();) {
						String key = it.next().toString();
						httpPost.addHeader(key, headers.get(key));
					}
				}

				// 创建参数队列
				List<NameValuePair> formparams = null;
				if (null !=params && params.size()>0) {
					formparams = new ArrayList<NameValuePair>();
					for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
						formparams.add(new BasicNameValuePair(iterator.next(), params.get(iterator.next())));
					}
				}

				entity  = new UrlEncodedFormEntity(formparams,  Charset.defaultCharset());
				httpPost.setEntity(entity);


				response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					httpPost.abort();
					httpClient.close();
					return result;
				}

				entity = response.getEntity();
				if(entity != null){
					result = EntityUtils.toString(entity, Charset.defaultCharset());
					result.replaceAll("\r", "");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return result;
		}


	}



