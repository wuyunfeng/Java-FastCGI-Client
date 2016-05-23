package com.wuyufeng.open.core;

import com.wuyufeng.open.response.FCGIEndResponse;
import com.wuyufeng.open.response.FCGIResponse;
import com.wuyufeng.open.response.FCGIResponseHeaderHandler;

import java.io.*;
import java.net.Socket;

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

    public FCGIResponse waitForResponse() {
        FCGIResponse response = new FCGIResponse();
        int nRead;
        do {
            try {
                byte header[] = new byte[8];
                nRead = inputStream.read(header, 0, 8);
                FCGIResponseHeaderHandler headerHandler = new FCGIResponseHeaderHandler(header);
                if (headerHandler.type == FCGIConstant.FCGI_STDOUT
                        || headerHandler.type == FCGIConstant.FCGI_STDERR) {
                    if (headerHandler.type == FCGIConstant.FCGI_STDERR) {
                        response.setState(FCGIConstant.FCGI_REP_ERROR);
                    }
                    response.setState(FCGIConstant.FCGI_REP_OK);
                    byte contentData[] = new byte[headerHandler.contentLength];
                    int nReadContent = inputStream.read(contentData, 0, headerHandler.contentLength);
                    if (nReadContent != headerHandler.contentLength) {
                        // error happened
                        response.setState(FCGIConstant.FCGI_REP_ERROR_CONTENT_LENGTH);
                        break;
                    }
                    response.appendRespContent(contentData);
                    if (headerHandler.paddingLength > 0) {
                        inputStream.skip(headerHandler.paddingLength);
                    }
                }
                if (headerHandler.type == FCGIConstant.FCGI_END_REQUEST) {
                    byte endResponse[] = new byte[headerHandler.contentLength];
                    FCGIEndResponse responseEnd = new FCGIEndResponse(endResponse);
                    response.setEndResponse(responseEnd);
                    break;
                }

            } catch (IOException e) {
                //nothing
                response.setState(FCGIConstant.FCGI_REP_ERROR_IOEXCEPTION);
                break;
            }

        } while (nRead == 8);
        try {
            inputStream.close();
            outputStream.close();
            mSocket.close();
        } catch (IOException e) {
            //nothing
        }
        return response;
    }
}
