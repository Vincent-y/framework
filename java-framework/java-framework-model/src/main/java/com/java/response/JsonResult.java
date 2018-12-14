package com.java.response;

import com.alibaba.fastjson.JSON;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>Json返回格式
 * <p>@author DRAGON
 * <p>@version 1.0
 * <p>@date 2015年6月16日
 */
@ToString
public class JsonResult implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7904108544792037300L;
    /**
     * 相应代码
     **/
    private int code;
    /**
     * 成功信息
     **/
    private String mes;
    /**
     * 返回信息
     **/
    private Object data;
    /**
     * 版本信息
     **/
    private String version;

    //分布式id
    private String id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonResult(int code, String mes, Object data, String version) {
        super();
        this.code = code;
        this.mes = mes;
        this.data = data;
        this.version = version;
    }

    public JsonResult() {
        super();
    }
    
    public static JsonResult success(Object data){
        return JsonResult.result(0, "", data, "1.0");
    }
    
    public static JsonResult fail(String message){
        return JsonResult.result(-1, message, null, "1.0");
    }

    public static JsonResult result(int code) {
        return JsonResult.result(code, "");
    }

    public static JsonResult result(int code, String mes) {
        return JsonResult.result(code, mes, null);
    }

    public static JsonResult result(int code, String mes, Object data) {
        return JsonResult.result(code, mes, data, "1.0");
    }

    public static JsonResult result(int code, String mes, Object data, String version) {
        return new JsonResult(code, mes, data, version);
    }

    public static String toJson(int code) {
        return JSON.toJSONString(JsonResult.toJson(code, ""));
    }

    public static String toJson(int code, String mes) {
        return JSON.toJSONString(JsonResult.toJson(code, mes, null));
    }

    public static String toJson(int code, String mes, Object data) {
        return JSON.toJSONString(JsonResult.toJson(code, mes, data, "1.0"));
    }

    public static String toJson(int code, String mes, Object data, String version) {
        return JSON.toJSONString(new JsonResult(code, mes, data, version));
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
