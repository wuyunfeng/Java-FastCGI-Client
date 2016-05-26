package com.wuyufeng.open.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * ***********************************
 * ***** FastCGI Client For Java *****
 * ***********************************
 * JavaFastCGIClient
 * Author: wuyunfeng
 * Date: 16/5/23
 * Time: 上午11:23
 * Email: wuyunfeng@126.com
 */
public class FCGIProtocolHelper {
//    public static byte[] encodeFCGIPacket(int type, int requestId, String body) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        out.write(FCGIConstant.FCGI_VERSION);
//        out.write(type);
//        out.write(requestId >> 8);
//        out.write(requestId);
//        int length = body.length();
//        out.write(length >> 8);
//        out.write(length);
//        out.write(0);
//        out.write(0);
//        out.write(body.getBytes(), 0, body.length());
//        return out.toByteArray();
//    }

//    public static String decodeFCGIPacket(byte[] data) {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        int version = in.read();
//        int type = in.read();
//        int requestID = (in.read() << 8) + in.read();
//        int contentLength = (in.read() << 8) + in.read();
//        int paddingLength = in.read();
//        int reserverd = in.read();
//        return "FASTCGI";
//    }

//    public static byte[] buildNameValuePairs(Map<String, String> params) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        Iterator it = params.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
//            if (it.hasNext()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                writeNameValue(out, key);
//                writeNameValue(out, value);
//                out.write(key.getBytes(), 0, key.length());
//                out.write(value.getBytes(), 0, value.length());
//            }
//        }
//        return out.toByteArray();
//    }

//    public static Map<String, String> decodeNameValuePairs(byte[] data) {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        int pack;
//        Map<String, String> result = new HashMap<String, String>();
//        while ((pack = in.read()) != -1) {
//            int nameLen = pack;
//            if (nameLen > 128) {
//                nameLen = read128NameValue(in);
//            }
//            int valLen = in.read();
//            if (valLen > 128) {
//                valLen = read128NameValue(in);
//            }
//            StringBuilder nameBuilder = new StringBuilder();
//            while (nameLen > 0) {
//                nameBuilder.append(in.read());
//                nameLen--;
//            }
//            StringBuilder valueBuilder = new StringBuilder();
//            while (valLen > 0) {
//                valueBuilder.append(in.read());
//                valLen--;
//            }
//            result.put(nameBuilder.toString(), valueBuilder.toString());
//        }
//        return result;
//    }


//    private static int read128NameValue(ByteArrayInputStream in) {
//        int nameLen = ((in.read() & 0x7F) << 24) & 0xff000000;
//        nameLen |= (in.read() << 16) & 0x00ff0000;
//        nameLen |= (in.read() << 8) & 0x0000ff00;
//        nameLen |= in.read() & 0x000000ff;
//        return nameLen;
//    }

//    private static void writeNameValue(ByteArrayOutputStream out, String value) {
//        int valueLength = value.length();
//        if (valueLength < 128) {
//            out.write(valueLength);
//        } else {
//            out.write((valueLength >> 24) | 0x80);
//            out.write((valueLength >> 16) & 0xFF);
//            out.write((valueLength >> 8) & 0xFF);
//            out.write(valueLength & 0xFF);
//        }
//    }
}
