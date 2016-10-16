package com.blue.sky.h5.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class HttpRequestAction
{

	// private final static String HTTP_SYNC_URL =
	// "http://10.0.2.2/facejob/1/common/DataAccess/SyncService.php";

	private final static String HTTP_SYNC_URL = "http://facejob.sinaapp.com/common/DataAccess/SyncService.php";

	private final static String HTTP_USER_SERVICE_URL = "http://facejob.sinaapp.com/common/DataAccess/UserService.php";

	private static String httRequest(String url, List<NameValuePair> params) throws Exception
	{
		// HttpPost连接对象
		HttpPost httpRequest = new HttpPost(url);

		// 设置字符集
		HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
		// 请求httpRequest
		httpRequest.setEntity(httpentity);
		// 取得默认的HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// 取得HttpResponse
		HttpResponse httpResponse = httpclient.execute(httpRequest);
		// HttpStatus.SC_OK表示连接成功
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			// 取得返回的字符串
			return EntityUtils.toString(httpResponse.getEntity());
		}
		else
		{
			String errMsg = httpResponse.getStatusLine().getReasonPhrase();
			throw new Exception("同步请求失败:" + errMsg);
		}
	}
}
