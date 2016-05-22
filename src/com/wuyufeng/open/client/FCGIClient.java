package com.wuyufeng.open.client;

import com.wuyufeng.open.request.FCGIBeginRequestBody;
import com.wuyufeng.open.request.FCGIContentBody;
import com.wuyufeng.open.request.FCGINameValueRequestBody;
import com.wuyufeng.open.request.FCGIRequestBody;
import com.wuyufeng.open.core.FCGIConstant;
import com.wuyufeng.open.core.FCGIEngine;
import com.wuyufeng.open.core.FCGIRequest;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * FastCGI Client
 *
 * @author wuyunfeng
 * @date 16/5/20.
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
            System.out.println("create socket failure");
        }
    }

    public void request(Map<String, String> params, String postBody) {

        Random rand = new Random();
        int requestId = rand.nextInt(((1 << 16) - 1));
        System.out.println("requestId = " + requestId);
        FCGIEngine fcgiEngine = FCGIEngine.newInstance(mClient);

        FCGIRequestBody beginRequestBody = new FCGIBeginRequestBody.Builder()
                .role(FCGIConstant.FCGI_ROLE_RESPONSER)
                .flag(0)
                .build();
        FCGIRequest request = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_BEGIN_REQUEST)
                .requestId(requestId)
                .content(beginRequestBody)
                .build();
        System.out.println("begin request");
        fcgiEngine.execute(request);
        System.out.println("end begin request");
        Iterator it = params.entrySet().iterator();
        System.out.println("begin params");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if (it.hasNext()) {
                FCGINameValueRequestBody paramsRequestBody = new FCGINameValueRequestBody.Builder()
                        .addParam(entry.getKey(), entry.getValue())
                        .build();
                FCGIRequest paramsRequest = new FCGIRequest.Builder()
                        .version(FCGIConstant.FCGI_VERSION)
                        .type(FCGIConstant.FCGI_PARAMS)
                        .requestId(requestId)
                        .content(paramsRequestBody)
                        .build();
                fcgiEngine.execute(paramsRequest);
            }
        }

        FCGIRequest paramsRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_PARAMS)
                .requestId(requestId)
                .content(null)
                .build();
        fcgiEngine.execute(paramsRequest);
        System.out.println("end params");
        System.out.println("begin body");

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
        FCGIRequest bodyRequest = new FCGIRequest.Builder()
                .version(FCGIConstant.FCGI_VERSION)
                .type(FCGIConstant.FCGI_STDIN)
                .requestId(requestId)
                .content(null)
                .build();
        fcgiEngine.execute(bodyRequest);
        System.out.println("end body");
        System.out.println("*********start***************");
        System.out.println(fcgiEngine.waitForResponse());
        System.out.println("***********end*************");
    }
}
