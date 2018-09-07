package com.zhongmeng.androidface.bean;

import java.util.List;

/**
 * Created by yidouco.ltdyellow on 2018/4/16.
 */

public class FaceDetail {

    /**
     * faceset_token : 0636196484660b5f92b90461261d569a
     * tags :
     * time_used : 96
     * user_data :
     * display_name :
     * face_tokens : ["7a5c4d1cc38f5552406e3435d7eb6e1a","edfec31b00cc800de07a1bf6505a4d7f","4a290f15ec04ffca6d468a7712723fb8","884cdaf6cf43cbcb4bf1798ab91f525c","bbafe7cda2f500b3c0327fc7ea2e30ee","41eb3f3d2b990b38edc54e6e8f00c066","0c5f7eef5a8833f09b44ae533a26eb1e","0672ab46e2f1df78d000e5b164114d73","6879298b164604d0b736d3ef8f30d943","23338a451721d519eb9744d145126629","5fcb895033703370bf8288a32a6f72fc","6ff0ab80024f7666781c8b2e6e34710c","6ed88c496b72ef9d22f2e947b183ec85","f6bd426bdf3729574673a0201cd4c571","5c7036cd6a689c8ada3552631b8a0e26","3204ca0d827e191f3e9aad3849b722a9","8638989335cebd315f3ccdd80f91b60e","70072ca2b7e85fbf7adb986811b19ce2","9a3d169fde140c454a55da211f93f870","ec21e8331b9858f6a8a7b47110ef4ba3","34c785e0410112818f0041d431961e54","9d20d5abd0cc540af10206abff18464e","a6d2a39eca9d432d69da96ca79671a6f"]
     * face_count : 23
     * request_id : 1523871350,0be7747e-537e-4301-a6a2-45b4be4daa5b
     * outer_id : testsss
     */

    private String faceset_token;
    private String tags;
    private int time_used;
    private String user_data;
    private String display_name;
    private int face_count;
    private String request_id;
    private String outer_id;
    private List<String> face_tokens;

    public String getFaceset_token() {
        return faceset_token;
    }

    public void setFaceset_token(String faceset_token) {
        this.faceset_token = faceset_token;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getUser_data() {
        return user_data;
    }

    public void setUser_data(String user_data) {
        this.user_data = user_data;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getFace_count() {
        return face_count;
    }

    public void setFace_count(int face_count) {
        this.face_count = face_count;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public List<String> getFace_tokens() {
        return face_tokens;
    }

    public void setFace_tokens(List<String> face_tokens) {
        this.face_tokens = face_tokens;
    }
}
