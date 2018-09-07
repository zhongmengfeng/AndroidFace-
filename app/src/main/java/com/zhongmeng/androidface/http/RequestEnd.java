package com.zhongmeng.androidface.http;
/**
 * 用于缓存处理
 */
public interface RequestEnd extends RequestListener{
	void onRequest(String url);
}
