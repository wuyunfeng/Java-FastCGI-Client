package com.wuyufeng.open.core;

/**
 * FastCGI Constant
 *
 * @author wuyunfeng
 * @date 16/5/20.
 */
public final class FCGIConstant {

    public static final int FCGI_VERSION = 1;
    public static final int FCGI_BEGIN_REQUEST = 1;
    public static final int FCGI_ABORT_REQUEST = 2;
    public static final int FCGI_END_REQUEST = 3;
    public static final int FCGI_PARAMS = 4;
    public static final int FCGI_STDIN = 5;
    public static final int FCGI_STDOUT = 6;
    public static final int FCGI_STDERR = 7;
    public static final int FCGI_DATA = 8;
    public static final int FCGI_GET_VALUES = 9;
    public static final int FCGI_GET_VALUES_RESULT = 10;
    public static final int FCGI_UNKNOWN_TYPE = 11;

    public static final int FCGI_ROLE_RESPONSER = 1;
    public static final int FCGI_ROLE_AUTHORIZER= 2;
    public static final int FCGI_ROLE_FILTER= 3;

    public static final int  FCGI_REQUEST_COMPLETE     = 0;
    public static final int  FCGI_CANT_MPX_CONN        = 1;
    public static final int  FCGI_OVERLOADED           = 2;
    public static final int  FCGI_UNKNOWN_ROLE         = 3;

}
