package com.zhongmeng.androidface.bean;

import java.util.List;

/**
 * Created by yidouco.ltdyellow on 2018/4/14.
 */

public class AddBean {

    /**
     * faceset_token : 0636196484660b5f92b90461261d569a
     * time_used : 1162
     * face_count : 6
     * face_added : 3
     * request_id : 1523688038,0591c084-2709-47a4-b129-5a531f07a73e
     * outer_id : testsss
     * failure_detail : []
     */

    private String faceset_token;
    private int time_used;
    private int face_count;
    private int face_added;
    private String request_id;
    private String outer_id;
    private List<?> failure_detail;

    public String getFaceset_token() {
        return faceset_token;
    }

    public void setFaceset_token(String faceset_token) {
        this.faceset_token = faceset_token;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public int getFace_count() {
        return face_count;
    }

    public void setFace_count(int face_count) {
        this.face_count = face_count;
    }

    public int getFace_added() {
        return face_added;
    }

    public void setFace_added(int face_added) {
        this.face_added = face_added;
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

    public List<?> getFailure_detail() {
        return failure_detail;
    }

    public void setFailure_detail(List<?> failure_detail) {
        this.failure_detail = failure_detail;
    }
}
