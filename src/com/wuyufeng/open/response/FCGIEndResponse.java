package com.wuyufeng.open.response;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/22
 * Time: 上午10:05
 * Email: wuyunfeng@126.com
 */
public class FCGIEndResponse {
    public int appStatus;
    public int protocolStatus;

    public FCGIEndResponse(byte[] header) {
        if (header.length != 8) {
            throw new IllegalArgumentException("header length must be 8 !");
        }

        appStatus = (header[0] << 16) + (header[1] << 8) + header[2];
    }

    @Override
    public String toString() {
        return "FCGIEndResponse{" +
                "appStatus=" + appStatus +
                ", protocolStatus=" + protocolStatus +
                '}';
    }
}
