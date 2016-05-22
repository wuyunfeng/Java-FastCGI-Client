package com.wuyufeng.open.request;

import java.io.OutputStream;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/21
 * Time: 下午7:55
 * Email: wuyunfeng@126.com
 */
public interface FCGIRequestBody {

    int contentLength();

    void writeTo(OutputStream os);
}
