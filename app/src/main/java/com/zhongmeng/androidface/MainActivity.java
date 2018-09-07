package com.zhongmeng.androidface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;
import com.zhongmeng.androidface.bean.FaceCount;
import com.zhongmeng.androidface.http.JsonUtils;
import com.zhy.http.okhttp.utils.ImageUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    String key = "sgmedcYPf3oo5UOjEgT31jTqZGwrx6ko";//api_key
    String secret = "H9r0VbbtbJOdeItOxBPnTEJyaOBQhEhE";//api_secret
    String imageUrl = "http://pic1.hebei.com.cn/003/005/869/00300586905_449eedbb.jpg";
    String imageUrl2 = "http://www.faceplusplus.com.cn/wp-content/themes/faceplusplus/assets/img/demo/9.jpg";//zuqiu
    StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);

        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(secret)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("please enter key and secret");
            builder.setTitle("");
            builder.show();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommonOperate commonOperate = new CommonOperate(key, secret, false);
                    FaceSetOperate FaceSet = new FaceSetOperate(key, secret, false);
                    ArrayList<String> faces = new ArrayList<>();
                    //调用搜索API，得到结果
                    //use search API to find face
                    Response res = null;
//                    try {
//                        res = commonOperate.searchByOuterId(null, imageUrl, null, "testsss", 1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    String result = new String(res.getContent());
//                    Log.e("result", result);
//                    sb.append("\n");
//                    sb.append("\n");
//                    sb.append("search result: ");
//                    sb.append(result);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTextView.setText(sb.toString());
//                        }
//                    });

                    try {
                        Response detailByFaceToken = FaceSet.getDetailByFaceToken("0636196484660b5f92b90461261d569a");
                        String faceToken = getFaceToken(detailByFaceToken);

                        //检测第一个人脸，传的是本地图片文件
                        //detect first face by local file
//                        Response response1 = commonOperate.detectByte(getBitmap(R.mipmap.c033), 0, null);
//                        String faceToken1 = getFaceToken(response1);
//                        faces.add(faceToken1);
//                        sb.append("faceToken1: ");
//                        sb.append(faceToken1);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
//                        //检测第二个人脸，传的是网络图片地址
//                        //detect first face by intenal image
//                        Response response2 = commonOperate.detectUrl(imageUrl, 0, null);
//                        String faceToken2 = getFaceToken(response2);
//                        faces.add(faceToken2);
//                        sb.append("\n");
//                        sb.append("faceToken2: ");
//                        sb.append(faceToken2);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
//                        //检测第三个人脸，传的是base64格式的数据
//                        //detect first face by local file use base64
//                        //6ff0ab80024f7666781c8b2e6e34710c facetoken
//                        String base64 = Base64.encodeToString(getBitmap(R.mipmap.c032), Base64.NO_WRAP);
//                        Response response3 = commonOperate.detectBase64(base64, 0, null);
//                        String faceToken3 = getFaceToken(response3);
//                        faces.add(faceToken3);
//                        sb.append("\n");
//                        sb.append("faceToken3: ");
//                        sb.append(faceToken3);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
////                        //创建人脸库，并往里加人脸
////                        //create faceSet and add face
//                        String faceTokens = creatFaceTokens(faces);
////                        Log.e("face_tokens",faceTokens);//884cdaf6cf43cbcb4bf1798ab91f525c,bbafe7cda2f500b3c0327fc7ea2e30ee,41eb3f3d2b990b38edc54e6e8f00c066
//                        Response faceset = FaceSet.createFaceSet(null,"testsss",null,faceTokens,null, 1);
//                        String faceSetResult = new String(faceset.getContent());
//                        Log.e("faceSetResult",faceSetResult);
//                        if(faceset.getStatus() == 200){
//                            sb.append("\n");
//                            sb.append("\n");
//                            sb.append("faceSet creat success");
//                            sb.append("\n");
//                            sb.append("create result: ");
//                            sb.append(faceSetResult);
//                        }else{
//                            sb.append("\n");
//                            sb.append("\n");
//                            sb.append("faceSet creat faile");
//                            sb.append("\n");
//                            sb.append("create result: ");
//                            sb.append(faceSetResult);
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
//
//                        //调用搜索API，得到结果
//                        //use search API to find face
                        Response ress = commonOperate.searchByOuterId(null, imageUrl2, null, "testsss", 1);
//                        String result = new String(ress.getContent());
//                        Log.e("result", result);
//                        sb.append("\n");
//                        sb.append("\n");
//                        sb.append("search result: ");
//                        sb.append(result);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private String creatFaceTokens(ArrayList<String> faceTokens){
        if(faceTokens == null || faceTokens.size() == 0){
            return "";
        }
        StringBuffer face = new StringBuffer();
        for (int i = 0; i < faceTokens.size(); i++){
            if(i == 0){
                face.append(faceTokens.get(i));
            }else{
                face.append(",");
                face.append(faceTokens.get(i));
            }
        }
        return face.toString();
    }


    private byte[] getBitmap(int res){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private String getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return new String(response.getContent());
        }
        String res = new String(response.getContent());
        Log.e("response", res);

//        Log.e("detailByFaceTceCount",faceCount.getFace_count()+"");
//        JSONObject json = new JSONObject(res);
//        String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
        return res;
    }

}
