package com.wuyufeng.open.core;

import com.wuyufeng.open.response.FCGIEndResponse;
import com.wuyufeng.open.response.FCGIResponseHeaderHandler;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * FILE: FCGIEngine.java
 * Author: wuyunfeng
 * Date: 16/5/20
 * Time: 下午11:31
 * Email: wuyunfeng@126.com
 */
public class FCGIEngine {

    private Socket mSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private FCGIEngine(Socket socket) {
        this.mSocket = socket;
        try {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            //nothing
        }
    }

    public static FCGIEngine newInstance(Socket socket) {
        return new FCGIEngine(socket);
    }

    public void execute(FCGIRequest request) {
        request.writeTo(this.outputStream);
    }

    public String waitForResponse() {

        try {
            byte header[] = new byte[8];
            inputStream.read(header, 0, 8);
            FCGIResponseHeaderHandler responseHeader = new FCGIResponseHeaderHandler(header);
            System.out.println(responseHeader.toString());
            byte contentData[] = new byte[responseHeader.contentLength];
            System.out.println(Arrays.toString(header));
            inputStream.read(contentData, 0, responseHeader.contentLength);
            String contentStr = new String(contentData);
            System.out.println("contentStr = " + contentStr);
            if (responseHeader.paddingLength > 0) {
                inputStream.skip(responseHeader.paddingLength);
            }
            byte header1[] = new byte[8];
            inputStream.read(header1, 0, 8);
            System.out.println(Arrays.toString(header1));
            FCGIResponseHeaderHandler responseHeader1 = new FCGIResponseHeaderHandler(header1);
            System.out.println(responseHeader1.toString());
            byte contentData1[] = new byte[responseHeader1.contentLength];
            inputStream.read(contentData1, 0, responseHeader1.contentLength);
            System.out.println(Arrays.toString(contentData1));
            FCGIEndResponse responseEnd = new FCGIEndResponse(contentData1);
            System.out.println("responseEnd = " + responseEnd);
            if (responseHeader1.paddingLength > 0) {
                inputStream.skip(responseHeader1.paddingLength);
            }

        } catch (IOException e) {
            //nothing
        }



        try {
            inputStream.close();
            outputStream.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    public static String decodeFCGIPacket(byte[] data) {
        int version = data[0];
        int type = data[1];
        int requestID = (data[2] << 8) + data[3];
        int contentLength = (data[4] << 8) + data[5];
        int paddingLength = data[6];
        int reserverd = data[7];
        return "FASTCGI";
    }

}
