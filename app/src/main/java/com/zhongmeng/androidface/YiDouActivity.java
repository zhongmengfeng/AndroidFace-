package com.zhongmeng.androidface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;
import com.zhongmeng.androidface.bean.CreateBena;
import com.zhongmeng.androidface.bean.FaceBean;
import com.zhongmeng.androidface.bean.SreachBean;
import com.zhongmeng.androidface.http.JsonUtils;
import com.zhongmeng.androidface.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.security.cert.CRLReason;
import java.util.ArrayList;

public class YiDouActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4;
    String key = "sgmedcYPf3oo5UOjEgT31jTqZGwrx6ko";//api_key
    String secret = "H9r0VbbtbJOdeItOxBPnTEJyaOBQhEhE";//api_secret
    String imageUrl = "http://pic1.hebei.com.cn/003/005/869/00300586905_449eedbb.jpg";
    FaceSetOperate FaceSet;
    CommonOperate commonOperate;
    ArrayList<String> faces;

    String facetoken, faceset;

    public final static String FACE_SET_TOKEN = "face_set_token";
    public final static String FACE_TOKEN = "face_token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_dou);
        faces = new ArrayList<>();
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 创建并且获取 faceset  face_tocken
                 */
                try {
                    /**
                     *  获取到faceet  facetoken
                     *  开始添加
                     */
//                    Response response1 = commonOperate.detectByte(getBitmap(R.mipmap.c033), 0, null);
//                    String faceToken1 = getFaceToken(response1);
//                    faces.add(faceToken1);
                    commonOperate = new CommonOperate(key, secret, false);
                    FaceSet = new FaceSetOperate(key, secret, false);
                    Response response2 = commonOperate.detectUrl(imageUrl, 0, null);
                    String faceToken2 = null;
                    faceToken2 = getFaceToken(response2);
                    faces.add(faceToken2);
                    String faceTokens = creatFaceTokens(faces);
                    Response faceset = FaceSet.createFaceSet(null, "yidou", null, faceTokens, null, 1);
                    Log.e("facesetr", faceset.toString());
                    CreateBena createBena = JsonUtils.parseJsonToBean(faceset.toString(), CreateBena.class);
//                    Log.e("String", createBena.getFaceset_token());
//                    Log.e("String", createBena.getFace_added() + "");
//                    Log.e("String", createBena.getFailure_detail().get(0).getFace_token());
//                    Log.e("String", createBena.getFailure_detail().get(0).getReason());
                    SpUtils.setParam(YiDouActivity.this, FACE_SET_TOKEN, createBena.getFaceset_token());
                    SpUtils.setParam(YiDouActivity.this, FACE_TOKEN, createBena.getFailure_detail().get(0).getFace_token());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("e","eee"+e.getLocalizedMessage());
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  获取到faceet  facetoken
                 *  开始添加
                 */
                try {
                    Object param = SpUtils.getParam(YiDouActivity.this, FACE_SET_TOKEN, "");
                    Object param2 = SpUtils.getParam(YiDouActivity.this, FACE_TOKEN, "");

                    Response response = FaceSet.addFaceByFaceToken(param2.toString(), param.toString());
                    FaceBean faceBean = JsonUtils.parseJsonToBean(response.toString(), FaceBean.class);
                    Log.e("faceBean", faceBean.getTime_used() + "");
                    if (faceBean.getTime_used() != 1) {
                        Log.e("add", "添加成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 开始进行搜索 faceset 共两张图片
                 *  选择传入第一张 进行比对
                 */
                try {
                    //调用搜索API，得到结果
                    //use search API to find face
                    Response res = commonOperate.searchByOuterId(null, imageUrl, null, "yidou", 1);
                    String result = new String(res.getContent());
                    SreachBean sreachBean = JsonUtils.parseJsonToBean(result, SreachBean.class);
                    if( sreachBean.getRequest_id().length()>0){
                        Log.e("sreach","搜索成功");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private byte[] getBitmap(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private String creatFaceTokens(ArrayList<String> faceTokens) {
        if (faceTokens == null || faceTokens.size() == 0) {
            return "";
        }
        StringBuffer face = new StringBuffer();
        for (int i = 0; i < faceTokens.size(); i++) {
            if (i == 0) {
                face.append(faceTokens.get(i));
            } else {
                face.append(",");
                face.append(faceTokens.get(i));
            }
        }
        return face.toString();
    }

    private String getFaceToken(Response response) throws JSONException {
        if (response.getStatus() != 200) {
            return new String(response.getContent());
        }
        String res = new String(response.getContent());
        Log.e("response", res);
        JSONObject json = new JSONObject(res);
        String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
        return faceToken;
    }
}
