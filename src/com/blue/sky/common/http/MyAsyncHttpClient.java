package com.blue.sky.common.http;

import com.blue.sky.common.utils.SystemUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Administrator on 2014/8/22.
 */
public class MyAsyncHttpClient extends AsyncHttpClient {

    public  MyAsyncHttpClient(){

    }

    public RequestHandle post(String url, MyRequestParams params, ResponseHandlerInterface responseHandler) {
        params.put("imei", SystemUtil.getDeviceId());
        params.put("ver",  SystemUtil.getApkVersionCode());
        params.put("model", android.os.Build.MODEL);
        params.put("sdk",  android.os.Build.VERSION.RELEASE);
        params.put("time", System.currentTimeMillis());
        params.put("sign", params.sign());
        return super.post(url, params, responseHandler);
    }
}
