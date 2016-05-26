package com.wuyufeng.open.client;

import com.wuyufeng.open.core.FCGIConstant;
import com.wuyufeng.open.core.FCGIEngine;
import com.wuyufeng.open.core.FCGIRequest;
import com.wuyufeng.open.request.FCGIBeginRequestBody;
import com.wuyufeng.open.request.FCGIContentBody;
import com.wuyufeng.open.request.FCGINameValueRequestBody;
import com.wuyufeng.open.request.FCGIRequestBody;
import com.wuyufeng.open.response.FCGIResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/22
 * Time: 下午2:59
 * Email: wuyunfeng@126.com
 */
public class FCGIClient {

    private Socket mClient;

    public FCGIClient(String host, int port, boolean keepAlive, int timeout) {
        try {
            mClient = new Socket(host, port);
            mClient.setReuseAddress(true);
            mClient.setKeepAlive(keepAlive);
            mClient.setSoTimeout(timeout);
        } catch (IOException e) {
            //nothing
        }
    }

    public FCGIResponse request(Map<String, String> params, String postBody) {

        Random rand = new Random();
        int requestId = rand.nextInt(((1 << 16) - 1));
        FCGIEngine fcgiEngine = FCGIEngine.newInstance(mClient);

        //begin request
        FCGIRequestBody beginRequestBody = new FCGIBeginRequestBody.Builder()
                .role(FCGIConstant.FCGI_ROLE_RESPONSER)
                .flag(0)
                .build();
        FCGIRequest beginRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_BEGIN_REQUEST)
                .requestId(requestId)
                .content(beginRequestBody)
                .build();
        fcgiEngine.execute(beginRequest);

        FCGINameValueRequestBody paramsRequestBody = new FCGINameValueRequestBody.Builder()
                .addParams(params)
                .build();
        FCGIRequest paramsRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_PARAMS)
                .requestId(requestId)
                .content(paramsRequestBody)
                .build();
        fcgiEngine.execute(paramsRequest);

        // params request
        FCGIRequest endParamsRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_PARAMS)
                .requestId(requestId)
                .content(null)
                .build();
        fcgiEngine.execute(endParamsRequest);

        //contentData request
        if (postBody != null && postBody.length() > 0) {
            FCGIContentBody bodyRequestBody = new FCGIContentBody(postBody.getBytes());
            FCGIRequest bodyRequest = new FCGIRequest.Builder()
                    .version(FCGIConstant.FCGI_VERSION)
                    .type(FCGIConstant.FCGI_STDIN)
                    .requestId(requestId)
                    .content(bodyRequestBody)
                    .build();
            fcgiEngine.execute(bodyRequest);
        }
        FCGIRequest endBodyRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_STDIN)
                .requestId(requestId)
                .content(null)
                .build();
        fcgiEngine.execute(endBodyRequest);

        return fcgiEngine.waitForResponse();
    }
}
