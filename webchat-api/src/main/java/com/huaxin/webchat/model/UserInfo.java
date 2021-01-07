package com.huaxin.webchat.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String name;
    private transient String sex;

    public UserInfo(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String toString() {
        return "name=" + name + ", sex=" + sex;
    }

}
