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
 * Time: 下午9:22
 * Email: wuyunfeng@126.com
 */
public class FCGINameValueRequestBody implements FCGIRequestBody {

    private ByteArrayOutputStream out;
    private String name;
    private String value;

    private FCGINameValueRequestBody(FCGINameValueRequestBody.Builder builder) {
        out = new ByteArrayOutputStream();
        name = builder.name;
        value = builder.value;
        buildNameValuePairs();
    }

    private void buildNameValuePairs() {
        writeNameValue(out, this.name);
        writeNameValue(out, this.value);
        out.write(this.name.getBytes(), 0, this.name.length());
        out.write(this.value.getBytes(), 0, this.value.length());
    }

    private void writeNameValue(ByteArrayOutputStream out, String value) {
        int valueLength = value.length();
        if (valueLength < 128) {
            out.write(valueLength);
        } else {
            out.write((valueLength >> 24) | 0x80);
            out.write((valueLength >> 16) & 0xFF);
            out.write((valueLength >> 8) & 0xFF);
            out.write(valueLength & 0xFF);
        }
    }

    @Override
    public int contentLength() {
        return out.size();
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            out.writeTo(os);
            out.close();
        } catch (IOException e) {
            //nothing
        }
    }


    public static class Builder {
        private String name;
        private String value;

        public Builder() {
        }

        public FCGINameValueRequestBody.Builder addParam(String name, String value) {
            this.name = name;
            this.value = value;
            return this;
        }

        public FCGINameValueRequestBody build() {
            return new FCGINameValueRequestBody(this);
        }
    }
}
