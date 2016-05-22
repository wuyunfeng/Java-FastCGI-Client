package com.wuyufeng.open.request;

import java.io.IOException;
import java.io.OutputStream;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/21
 * Time: 下午7:58
 * Email: wuyunfeng@126.com
 */
public class FCGIBeginRequestBody implements FCGIRequestBody {

    private int roleB1;
    private int roleB0;
    private int flags;// keepalive  flags & FCGI_KEEP_CONN
    private byte[] reserverd = {0, 0, 0, 0, 0};


    private FCGIBeginRequestBody() {

    }

    private FCGIBeginRequestBody(FCGIBeginRequestBody.Builder builder) {
        this.roleB1 = (byte) (builder.role >> 8 & 0xFF);
        this.roleB0 = (byte) (builder.role & 0xFF);
        this.flags = (byte) builder.flags;
    }

    @Override
    public int contentLength() {
        return 8;
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            os.write((byte) roleB1);
            os.write((byte) roleB0);
            os.write((byte) flags);
            os.write(reserverd, 0, reserverd.length);
        } catch (IOException e) {
        }
    }

    public static class Builder {
        private int role;
        private int flags;

        public FCGIBeginRequestBody.Builder role(int role) {
            this.role = role;
            return this;
        }

        public FCGIBeginRequestBody.Builder flag(int flags) {
            this.flags = flags;
            return this;
        }

        public FCGIBeginRequestBody build() {
            return new FCGIBeginRequestBody(this);
        }

    }
}
