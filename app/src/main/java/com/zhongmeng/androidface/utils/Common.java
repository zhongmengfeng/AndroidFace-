package com.zhongmeng.androidface.utils;

import android.os.Environment;

/**
 * Created by yidouco.ltdyellow on 2018/4/16.
 */

public class Common {
    //哪里用到了请求
    //path 拍照获取的图片位置
    public static final String path = Environment.getExternalStorageDirectory() + "/DCIM/CameraV2/";
    // 获取预览图的位置
    public static final String PIC_PATH = Environment.getExternalStorageDirectory() + "/Boohee";

    //Face++
    public static final String KEY = "sgmedcYPf3oo5UOjEgT31jTqZGwrx6ko";//api_key
    public static final String SECRET = "H9r0VbbtbJOdeItOxBPnTEJyaOBQhEhE";//api_secret
    public static final String FACE_SET_TOKEN = "0636196484660b5f92b90461261d569a";//TOKEN

    //sp
    public static final String CURRENT_SP = "currentName";

}
