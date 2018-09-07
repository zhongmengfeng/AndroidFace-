package com.zhongmeng.androidface.bean;

import java.util.List;

/**
 * Created by yidouco.ltdyellow on 2018/4/17.
 */
//用户搜索详情

public class UserDetail {

    /**
     * user_id :
     * time_used : 107
     * request_id : 1523932199,12a37850-1562-483b-bdcf-0b3cc7e8604d
     * image_id : sUQdRthzEPUbQYwTbQn32A==
     * face_rectangle : {"width":91,"top":180,"left":367,"height":91}
     * facesets : [{"faceset_token":"0636196484660b5f92b90461261d569a","outer_id":"testsss","tags":["zhangtianhe"]}]
     * face_token : 5fcb895033703370bf8288a32a6f72fc
     */

    private String user_id;
    private int time_used;
    private String request_id;
    private String image_id;
    private FaceRectangleBean face_rectangle;
    private String face_token;
    private List<FacesetsBean> facesets;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public FaceRectangleBean getFace_rectangle() {
        return face_rectangle;
    }

    public void setFace_rectangle(FaceRectangleBean face_rectangle) {
        this.face_rectangle = face_rectangle;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public List<FacesetsBean> getFacesets() {
        return facesets;
    }

    public void setFacesets(List<FacesetsBean> facesets) {
        this.facesets = facesets;
    }

    public static class FaceRectangleBean {
        /**
         * width : 91
         * top : 180
         * left : 367
         * height : 91
         */

        private int width;
        private int top;
        private int left;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static class FacesetsBean {
        /**
         * faceset_token : 0636196484660b5f92b90461261d569a
         * outer_id : testsss
         * tags : ["zhangtianhe"]
         */

        private String faceset_token;
        private String outer_id;
        private List<String> tags;

        public String getFaceset_token() {
            return faceset_token;
        }

        public void setFaceset_token(String faceset_token) {
            this.faceset_token = faceset_token;
        }

        public String getOuter_id() {
            return outer_id;
        }

        public void setOuter_id(String outer_id) {
            this.outer_id = outer_id;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}

