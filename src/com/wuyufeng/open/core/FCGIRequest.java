package com.wuyufeng.open.core;

import com.wuyufeng.open.request.FCGIRequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * File: FCGIRequest.java
 * Author: wuyunfeng
 * Date: 16/5/20
 * Time: 下午9:47
 * Email: wuyunfeng@126.com
 */
public class FCGIRequest {

    private int version;
    private int type;
    private int requestIdB1;
    private int requestIdB0;
    private int contentLengthB1;
    private int contentLengthB0;
    private int paddingLength;
    private int reserved;
    private FCGIRequestBody contentData;
    private byte[] paddingData;

    private ByteArrayOutputStream out;

    private FCGIRequest(FCGIRequest.Builder builder) {
        this.version = builder.version;
        this.type = builder.type;
        this.requestIdB1 = (byte) ((builder.requestId >> 8) & 0xFF);
        this.requestIdB0 = (byte) (builder.requestId & 0xFF);
        this.contentLengthB1 = (byte) ((builder.contentLength >> 8) & 0xFF);
        this.contentLengthB0 = (byte) builder.contentLength;
        this.paddingLength = (byte) builder.paddingLength;
        this.reserved = (byte) builder.reserved;
        if (builder.paddingData != null) {
            this.paddingData = builder.paddingData;
        }
        this.contentData = builder.contentData;
        out = new ByteArrayOutputStream();
        this.transform2Stream();
    }

    private void transform2Stream() {
        out.write(this.version);
        out.write(this.type);
        out.write(this.requestIdB1);
        out.write(this.requestIdB0);
        out.write(this.contentLengthB1);
        out.write(this.contentLengthB0);
        out.write(0);
        out.write(0);
//        if (this.paddingLength > 0) {
//            out.write(this.paddingData, 0, this.paddingLength);
//        }
        if (this.contentData != null) {
            this.contentData.writeTo(out);
        }
    }


    public boolean writeTo(OutputStream os) {
        System.out.println(Arrays.toString(out.toByteArray()));
        try {
            out.writeTo(os);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static class Builder {
        private int version;
        private int type;
        private int requestId;
        private int contentLength;
        private int paddingLength;
        private int reserved;
        private FCGIRequestBody contentData;
        private byte[] paddingData;
        private Map<String, String> fcgiParams;

        public Builder() {
            this.version = FCGIConstant.FCGI_VERSION;
            this.reserved = (byte) 0;
        }

        public FCGIRequest.Builder version(int version) {
            this.version = (byte) version;
            return this;
        }

        public FCGIRequest.Builder requestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public FCGIRequest.Builder type(int type) {
            this.type = (byte) type;
            if (FCGIConstant.FCGI_PARAMS == type) {
                fcgiParams = new HashMap<String, String>();
            }
            return this;
        }

        public FCGIRequest.Builder content(FCGIRequestBody contentData) {
            if (contentData != null) {
                this.contentData = contentData;
                this.contentLength = contentData.contentLength();
            } else {
                this.contentLength = 0;
            }
            // 8 byte aligned
//            this.paddingLength = ((this.contentLength + 7) & ~7) - this.contentLength;
//            this.paddingData = new byte[this.paddingLength];
            return this;
        }

        public FCGIRequest build() {
            return new FCGIRequest(this);
        }
    }
}
