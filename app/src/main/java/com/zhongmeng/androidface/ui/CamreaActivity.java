package com.zhongmeng.androidface.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;
import com.squareup.okhttp.Request;
import com.zhongmeng.androidface.R;
import com.zhongmeng.androidface.bean.SreachBean;
import com.zhongmeng.androidface.bean.UserDetail;
import com.zhongmeng.androidface.http.HttpHelper;
import com.zhongmeng.androidface.http.JsonUtils;
import com.zhongmeng.androidface.http.RequestListener;
import com.zhongmeng.androidface.utils.BaseUtils;
import com.zhongmeng.androidface.utils.Common;
import com.zhongmeng.androidface.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import madgaze.x5_gesture.detector.MADGestureTouchDetector;
import madgaze.x5_gesture.view.MADGestureView;
import madgaze.x5_gesture.view.MADToast;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CamreaActivity extends Activity {

    private static final SparseIntArray ORIENTATION = new SparseIntArray();

    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }

    private String mCameraId;
    private Size mPreviewSize;
    private Size mCaptureSize;
    private HandlerThread mCameraThread;
    private Handler mCameraHandler;
    private CameraDevice mCameraDevice;
    private TextureView mTextureView;
    private ImageReader mImageReader;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mCameraCaptureSession;

    List<String> list = new ArrayList<>();
    private MADGestureView madGesture;

    private CommonOperate commonOperate;
    private FaceSetOperate FaceSet;
    ArrayList<String> faces;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /**
                     * 添加user
                     */
                    Log.e("size     ", list.size() + "");
                    Object param = SpUtils.getParam(CamreaActivity.this, Common.CURRENT_SP, "");
                    Log.e("param", param.toString());
//                    if(!param.equals("")) {
//                    addUser(Common.PIC_PATH + File.separatorChar + list.get(list.size() - 1));
//                    }else {
//                        Toast.makeText(CamreaActivity.this,"请重新拍摄",Toast.LENGTH_SHORT).show();
//                    }
                    break;
                case 2:
                    /**
                     * 搜索user
                     */

                    File file = new File(Common.PIC_PATH + File.separatorChar + list.get(list.size() - 1));
                    if (file.exists()) {
                        sreachUser(list.get(list.size() - 1));
                    }

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏无状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        mTextureView = findViewById(R.id.textureView);
        madGesture = findViewById(R.id.gestureView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraThread();
        if (!mTextureView.isAvailable()) {
            mTextureView.setSurfaceTextureListener(mTextureListener);
        } else {
            openCamera();
        }
    }

    private void startCameraThread() {
        mCameraThread = new HandlerThread("CameraThread");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());
    }

    private TextureView.SurfaceTextureListener mTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //当SurefaceTexture可用的时候，设置相机参数并打开相机
            setupCamera(width, height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private void setupCamera(int width, int height) {
        //获取摄像头的管理者CameraManager
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            //遍历所有摄像头
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                //此处默认打开后置摄像头
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT)
                    continue;
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                //根据TextureView的尺寸设置预览尺寸
                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                //获取相机支持的最大拍照尺寸
                mCaptureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                    @Override
                    public int compare(Size lhs, Size rhs) {
                        return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth());
                    }
                });
                //此ImageReader用于拍照所需
//                setupImageReader();
                mCameraId = cameraId;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //选择sizeMap中大于并且最接近width和height的size
    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
        List<Size> sizeList = new ArrayList<>();
        for (Size option : sizeMap) {
            if (width > height) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (option.getWidth() > width && option.getHeight() > height) {
                        sizeList.add(option);
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (option.getWidth() > height && option.getHeight() > width) {
                        sizeList.add(option);
                    }
                }
            }
        }
        if (sizeList.size() > 0) {
            return Collections.min(sizeList, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }

            });
        }
        return sizeMap[0];
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                manager.openCamera(mCameraId, mStateCallback, mCameraHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                camera.close();
            }
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                camera.close();
            }
            mCameraDevice = null;
        }
    };

    private void startPreview() {
        SurfaceTexture mSurfaceTexture = mTextureView.getSurfaceTexture();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        }
        Surface previewSurface = new Surface(mSurfaceTexture);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);


                mCaptureRequestBuilder.addTarget(previewSurface);

                mImageReader = ImageReader.newInstance(mCaptureSize.getWidth(), mCaptureSize.getHeight(), ImageFormat.JPEG, 2);

                mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onConfigured(CameraCaptureSession session) {
                        try {
                            if (null == mCameraDevice)
                                return;
                            mCaptureRequest = mCaptureRequestBuilder.build();
                            mCameraCaptureSession = session;
                            //预览时，需要设置自动聚焦模式
                            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            //开启图像预览
                            mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, mCaptureCallback, mCameraHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                            Toast.makeText(CamreaActivity.this, "暂无摄像头权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSurfacePrepared(@NonNull CameraCaptureSession session, @NonNull Surface surface) {
                        super.onSurfacePrepared(session, surface);

                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession session) {

                    }
                }, mCameraHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);

        }

        @Override
        public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {

        }

        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {

            //向上下左右的滑动事件
            madGesture.getGestureListener().setOnSwipeListener(new MADGestureTouchDetector.OnSwipeListener() {
                @Override
                public void onSwipeRight() {
//                    MADToast.makeText(CamreaActivity.this, "向右滑动...", MADToast.Duration.SHORT).show();

                }

                @Override
                public void onSwipeLeft() {
//                    MADToast.makeText(CamreaActivity.this, "向左滑动...", MADToast.Duration.SHORT).show();
                }

                @Override
                public void onSwipeUp() {
//                    MADToast.makeText(CamreaActivity.this, "向上滑动...", MADToast.Duration.SHORT).show();
                    saveImage(mTextureView.getBitmap());
                    handler.sendEmptyMessage(1);
                }

                @Override
                public void onSwipeDown() {
//                    MADToast.makeText(CamreaActivity.this, "向下滑动...", MADToast.Duration.SHORT).show();
                    saveImage(mTextureView.getBitmap());
                    handler.sendEmptyMessage(2);
                }
            });


        }

    };


    public File saveImage(Bitmap bmp) {
        File appDir = new File(Common.PIC_PATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        Log.e("file", fileName);
        SpUtils.setParam(CamreaActivity.this, Common.CURRENT_SP, fileName);
        list.add(fileName);
        final File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
    }

    /**
     * 添加用户头像
     */
    private void addUser(final String absolutePath) {

        if (TextUtils.isEmpty(Common.KEY) || TextUtils.isEmpty(Common.SECRET)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("please enter key and secret");
            builder.setTitle("");
            builder.show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    commonOperate = new CommonOperate(Common.KEY, Common.SECRET, false);
                    FaceSet = new FaceSetOperate(Common.KEY, Common.SECRET, false);
                    faces = new ArrayList<>();
                    try {
                        String base64File = BaseUtils.encodeBase64File(absolutePath);
                        Response response3 = null;
                        response3 = commonOperate.detectBase64(base64File, 0, null);
                        String faceToken3 = getFaceToken(response3);
                        faces.add(faceToken3);
                        //创建人脸库，并往里加人脸
                        //create faceSet and add face
                        String faceTokens = creatFaceTokens(faces);
                        Response faceset = FaceSet.createFaceSet(null, "testsss", "wang", faceTokens, null, 1);
                        String faceSetResult = new String(faceset.getContent());
                        Log.e("faceSetResult", faceSetResult);

                        HashMap map = new HashMap();
                        map.put("api_key", Common.KEY);
                        map.put("api_secret", Common.SECRET);
                        map.put("faceset_token", Common.FACE_SET_TOKEN);
                        map.put("tags","wang");
                        HttpHelper.post("https://api-cn.faceplusplus.com/facepp/v3/faceset/update", map, new RequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("faceSetResult555", response);
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }

    /**
     * 搜索用户
     *
     * @param picPath 图片路径
     */
    private void sreachUser(String picPath) {
        ///sdcard/Boohee/1523859460252.jpg
        File file = new File(Common.PIC_PATH + File.separator + "/" + picPath);

        if (file.exists()) {
            HttpHelper.uploadHeadFile("https://api-cn.faceplusplus.com/facepp/v3/search", file, new RequestListener() {
                @Override
                public void onResponse(String response) {
                    Log.e("faceSetResult2", response + "");
                    SreachBean sreachBean = JsonUtils.parseJsonToBean(response, SreachBean.class);

                    if (sreachBean.getFaces().size() > 0) {
                        double confidence = sreachBean.getResults().get(0).getConfidence();
//                        Log.e("faceSetResult2", "相似度     " + confidence);
                        if(confidence >80.00d) {

                            if(sreachBean.getResults().get(0).getFace_token().equals("5d8afdcc58ec1a6a96137ded491a1980")){
                                Toast.makeText(CamreaActivity.this, "王俊翔", Toast.LENGTH_SHORT).show();
                            }else if(sreachBean.getResults().get(0).getFace_token().equals("5fcb895033703370bf8288a32a6f72fc")) {
                                Toast.makeText(CamreaActivity.this, "张天鹤", Toast.LENGTH_SHORT).show();
                            }

//                            HashMap map = new HashMap();
//                            map.put("api_key", Common.KEY);
//                            map.put("api_secret", Common.SECRET);
//                            map.put("face_token", sreachBean.getResults().get(0).getFace_token());
//                            HttpHelper.post("https://api-cn.faceplusplus.com/facepp/v3/face/getdetail", map, new RequestListener() {
//                                @Override
//                                public void onResponse(String response) {
//                                    Log.e("faceSetResult3", response);
//                                    UserDetail userDetail = JsonUtils.parseJsonToBean(response, UserDetail.class);
//                                    if (userDetail.getFacesets().get(0).getTags().get(0).equals("zhangtianhe")) {
//
//                                    } else if (userDetail.getFacesets().get(0).getTags().get(0).equals("wang")) {
//
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onError(Request request, Exception e) {
//
//                                }
//                            });
                        }
                    } else {
                        Toast.makeText(CamreaActivity.this, "图中未监测到人脸", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    Log.e("jjj", "eeee      " + e.getLocalizedMessage() + "         " + request.httpUrl() + "       " + request.headers());
                }
            });
        }
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