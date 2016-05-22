
#Java FastCGI Client

#####A `Java` FastCGI Client for directly access PHP web resource through `FastCGI`


AUTHOR & Email
================

wuyunfeng
    - wyfsky888@126.com


How use?(You should start your PHP-FPM)
========================

        FCGIClient client = new FCGIClient("127.0.0.1",
                9000, false, 3000);
        String content = "name=john&address=beijing";
        String uri = "/echo.php";
        Map<String, String> params = new HashMap<String, String>();
        String documentRoot = "/path/to/workspace";
        params.put("GATEWAY_INTERFACE", "FastCGI/1.0");
        params.put("REQUEST_METHOD", "POST");
        params.put("SCRIPT_FILENAME", documentRoot + uri);
        params.put("SCRIPT_NAME", uri);
        params.put("QUERY_STRING", "");
        params.put("REQUEST_URI", uri);
        params.put("DOCUMENT_ROOT", documentRoot);
        params.put("REMOTE_ADDR", "127.0.0.1");
        params.put("REMOTE_PORT", "any port");
        params.put("SERVER_ADDR", "127.0.0.1");
        params.put("SERVER_NAME", "localhost");
        params.put("SERVER_PORT", "80");
        params.put("SERVER_PROTOCOL", "HTTP/1.1");
        params.put("CONTENT_TYPE", "application/x-www-form-urlencoded");
        params.put("CONTENT_LENGTH", content.length() + "");
        client.request(params, content);
        
