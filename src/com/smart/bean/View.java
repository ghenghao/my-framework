package com.smart.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装视图对象
 *
 * @author 
 * @since 1.0
 */
public class View{

    private String path;              // 视图路径
    private Map<String, Object> model; // 相关数据

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
