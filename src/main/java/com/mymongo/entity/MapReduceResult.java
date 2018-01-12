package com.mymongo.entity;

/**
 * 执行mapReduce处理的结果集对象
 */
public class MapReduceResult {
    private String _id;

    private String value;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
