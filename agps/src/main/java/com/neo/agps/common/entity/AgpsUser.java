package com.neo.agps.common.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: 20160301301
 * Date: 2018/1/3 9:11
 * Comment:
 */
public class AgpsUser {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
