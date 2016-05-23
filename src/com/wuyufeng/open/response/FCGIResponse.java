package com.wuyufeng.open.response;

import java.io.ByteArrayOutputStream;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/23
 * Time: 上午10:55
 * Email: wuyunfeng@126.com
 */
public class FCGIResponse {

    private int state;
    private FCGIEndResponse endResponse;
    private ByteArrayOutputStream outputStream;

    public FCGIResponse() {
        outputStream = new ByteArrayOutputStream();
    }

    public FCGIEndResponse getEndResponse() {
        return endResponse;
    }

    public void setEndResponse(FCGIEndResponse endResponse) {
        this.endResponse = endResponse;
    }

    public void appendRespContent(byte[] contentData) {
        if (contentData != null) {
            outputStream.write(contentData, 0, contentData.length);
        }
    }

    public byte[] getResponseContent() {
        return outputStream.toByteArray();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FCGIResponse{" +
                "\r\n       state=" + state +
                "\r\n       endResponse=" + endResponse +
                "\r\n       responseContent = " + new String(outputStream.toByteArray()) +
                "\r\n}";
    }
}
