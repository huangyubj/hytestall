package com.hy.api.entity;

import java.io.Serializable;

public class InfoEntity implements Serializable {
    private String id;
    private String name;
    private String remark;

    public InfoEntity(){}

    public InfoEntity(String id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InfoEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
