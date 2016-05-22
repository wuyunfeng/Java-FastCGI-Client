package com.wuyufeng.open.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/21
 * Time: 下午10:02
 * Email: wuyunfeng@126.com
 */
public class FCGIContentBody implements FCGIRequestBody {
    ByteArrayOutputStream out;

    public FCGIContentBody(byte[] content) {
        out = new ByteArrayOutputStream();
        out.write(content, 0, content.length);
    }

    @Override
    public int contentLength() {
        return out.size();
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            out.writeTo(os);
        } catch (IOException e) {
            //nothing
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                //nothing
            }
        }
    }
}
