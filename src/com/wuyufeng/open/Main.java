package com.wuyufeng.open;

import com.wuyufeng.open.client.FCGIClient;
import com.wuyufeng.open.response.FCGIResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/21
 * Time: 下午10:29
 * Email: wuyunfeng@126.com
 */
public class Main {

    public static void main(String[] args) {
        FCGIClient client = new FCGIClient("127.0.0.1",
                9000, false, 3000);
        String content = "name=john&address=beijing";
        String uri = "/echo.php";
        Map<String, String> params = new HashMap<String, String>();
        String documentRoot = "/Users/baidu/php_workspace";
        params.put("GATEWAY_INTERFACE", "FastCGI/1.0");
        params.put("REQUEST_METHOD", "POST");
        params.put("SCRIPT_FILENAME", documentRoot + uri);
        params.put("SCRIPT_NAME", uri);
        params.put("QUERY_STRING", "");
        params.put("REQUEST_URI", uri);
        params.put("DOCUMENT_ROOT", documentRoot);
        params.put("REMOTE_ADDR", "127.0.0.1");
        params.put("REMOTE_PORT", "9985");
        params.put("SERVER_ADDR", "127.0.0.1");
        params.put("SERVER_NAME", "localhost");
        params.put("SERVER_PORT", "80");
        params.put("SERVER_PROTOCOL", "HTTP/1.1");
        params.put("CONTENT_TYPE", "application/x-www-form-urlencoded");
        params.put("CONTENT_LENGTH", content.length() + "");
        FCGIResponse response = client.request(params, content);
        System.out.println(response);
    }
}
