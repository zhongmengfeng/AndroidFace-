package com.zhongmeng.androidface;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yidouco.ltdyellow on 2018/4/13.
 */

public class test2 {
//    public static void main(String[] args) throws Exception {
//        // replace api_key and api_secret here (note)
//        HttpRequests httpRequests = new HttpRequests("4480afa9b8b364e30ba03819f3e9eff5", "Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M ", true, true);
////        识别一张足球队的合影。
//
//// detection/detect
//        result = httpRequests.detectionDetect(new PostParameters().setUrl("http://www.faceplusplus.com.cn/wp-content/themes/faceplusplus/assets/img/demo/9.jpg"));
////    创建Person，通过personAddFace添加人脸，通过setPersonName添加中文姓名，最后通过personGetInfo得到每一个Person的信息输出。
//
//        // -----------------Person-----------------
//// person/create
//        System.out.println("\nperson/create");
//        for (int i = 0; i < result.getJSONArray("face").length(); ++i)
//            System.out.println(httpRequests.personCreate(newPostParameters().setPersonName("person_" + i)));
//
//        new PostParameters().
//                setPersonName("person_" + 0).
//                setFaceId(result.getJSONArray("face").
//                        getJSONObject(0).
//                        getString("face_id")).
//                getMultiPart().
//                writeTo(System.out);
//
//// person/add_face
//        System.out.println("\nperson/add_face");
//        for (int i = 0; i < result.getJSONArray("face").length(); ++i)
//            System.out.println(httpRequests.personAddFace(newPostParameters().setPersonName("person_" + i).setFaceId(result.getJSONArray("face").getJSONObject(i).getString("face_id"))));
//
//
//// person/set_info
//        System.out.println("\nperson/set_info");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//
//        {
//            new PostParameters().setPersonName("person_" + i)
//                    .setTag("中文 tag_" + i).getMultiPart()
//                    .writeTo(System.out);
//            System.out.println(httpRequests
//                    .personSetInfo(new PostParameters().setPersonName(
//                            "person_" + i).setTag("中文 tag_" + i)));
//        }
//
//// person/get_info
//        System.out.println("\nperson/get_info");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//            System.out.println(httpRequests
//                    .personGetInfo(new
//
//                            PostParameters()
//                            .
//
//                                    setPersonName("person_" + i)));
//        创建Faceset，此处和创建Person基本一致。
//
//        // -----------------Faceset-----------------
//// faceset/create
//        System.out.println("\nfaceset/create");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//            System.out.println(httpRequests
//                    .facesetCreate(new
//
//                            PostParameters()
//                            .
//
//                                    setFacesetName("faceset_" + i)));
//
//// faceset/add_face
//        System.out.println("\nfaceset/add_face");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//            System.out.println(httpRequests
//                    .facesetAddFace(new
//
//                            PostParameters().
//
//                            setFacesetName(
//                                    "faceset_" + i).
//
//                            setFaceId(
//                                    result.getJSONArray("face").
//
//                                            getJSONObject(i)
//                                            .
//
//                                                    getString("face_id"))));
//
//// faceset/set_info
//        System.out.println("\nfaceset/set_info");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//
//        {
//            new PostParameters().setFacesetName("faceset_" + i)
//                    .setTag("中文 tag_" + i).getMultiPart()
//                    .writeTo(System.out);
//            System.out.println(httpRequests
//                    .facesetSetInfo(new PostParameters().setFacesetName(
//                            "faceset_" + i).setTag("中文 tag_" + i)));
//        }
//
//// faceset/get_info
//        System.out.println("\nfaceset/get_info");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//            System.out.println(httpRequests
//                    .facesetGetInfo(new
//
//                            PostParameters()
//                            .
//
//                                    setFacesetName("faceset_" + i)));
//        创建一个Group，把所有Person加入到Group中去。
//
//        // -----------------Group-----------------
//// group/create
//        System.out.println("\ngroup/create");
//        System.out.println(httpRequests.groupCreate(new
//
//                PostParameters()
//                .
//
//                        setGroupName("group_0")));
//
//// group/add_person
//        System.out.println("\ngroup/add_person");
//        ArrayList personList = new ArrayList();
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//            personList.add("person_" + i);
//
//        new
//
//                PostParameters().
//
//                setGroupName("group_0")
//                .
//
//                        setPersonName(personList).
//
//                getMultiPart()
//                .
//
//                        writeTo(System.out);
//        System.out.println(httpRequests.groupAddPerson(new
//
//                PostParameters()
//                .
//
//                        setGroupName("group_0").
//
//                        setPersonName(personList)));
//
//// group/set_info
//        System.out.println("\ngroup/set_info");
//        System.out.println(httpRequests.groupSetInfo(new
//
//                PostParameters()
//                .
//
//                        setGroupName("group_0").
//
//                        setTag("group tag")));
//
//// group/get_info
//        System.out.println("\ngroup/get_info");
//        System.out.println(httpRequests.groupGetInfo(new
//
//                PostParameters()
//                .
//
//                        setGroupName("group_0")));
//        通过trainIdentify建立人脸标识模型，再通过trainVerify建立人脸验证模型。
//
//// -----------------Recognition-----------------
//
//        // recognition/train
//        JSONObject syncRet = null;
//
//        System.out.println("\ntrain/Identify");
//        syncRet = httpRequests.trainIdentify(new
//
//                PostParameters()
//                .
//
//                        setGroupName("group_0"));
//        System.out.println(syncRet);
//        System.out.println(httpRequests.getSessionSync(syncRet
//                .getString("session_id")));
//
//        System.out.println("\ntrain/verify");
//        for (
//                int i = 0; i < result.getJSONArray("face").
//
//                length(); ++i)
//
//        {
//            syncRet = httpRequests.trainVerify(new PostParameters()
//                    .setPersonName("person_" + i));
//            System.out.println(httpRequests.getSessionSync(syncRet.get(
//                    "session_id").toString()));
//        }
//
//        调用recognitionIdentify标识了一张孙燕姿的照片，console中将输出识别成功，但confidence只有10 %。
//
//        // recognition/recognize
//        System.out.println("\nrecognition/identify");
//        System.out
//                .println(httpRequests
//                        .recognitionIdentify(new
//
//                                PostParameters()
//                                .
//
//                                        setGroupName("group_0")
//                                .
//
//                                        setUrl("http://www.faceplusplus.com.cn/wp-content/themes/"
//                                                "faceplusplus/assets/img/demo/5.jpg")));
////    调用recognitionVerify验证了两张人脸图片，一张返回True一张返回False。
//
//        // recognition/verify
//        System.out.println("\nrecognition/verify");
//        System.out.println(httpRequests
//                .recognitionVerify(new
//
//                        PostParameters().
//
//                        setPersonName(
//                                "person_0").
//
//                        setFaceId(
//                                result.getJSONArray("face").
//
//                                        getJSONObject(0)
//                                        .
//
//                                                getString("face_id"))));
//        System.out.println(httpRequests
//                .recognitionVerify(new
//
//                        PostParameters().
//
//                        setPersonName(
//                                "person_1").
//
//                        setFaceId(
//                                result.getJSONArray("face").
//
//                                        getJSONObject(0)
//                                        .
//
//                                                getString("face_id"))));
//    }
}
