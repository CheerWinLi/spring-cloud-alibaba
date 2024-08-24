package com.alibaba.dubbo.consumer.entity;

import java.io.Serializable;

/**
 * @author :Lictory
 * @date : 2024/08/01
 */
public class User implements Serializable {
    private int id=1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
