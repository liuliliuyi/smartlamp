package com.example.publics;

 

public class MyMsg {

    private Object msg;
    private int tag;

    public int getTag() {
        return this.tag;
    }

    public void setTag(int var1) {
        this.tag = var1;
    }

    public Object getMsg() {
        return this.msg;
    }

    public void setMsg(String var1) {
        this.msg = var1;
    }

    public MyMsg(Object var1, int var2) {
        this.msg = var1;
        this.tag = var2;
    }

    @Override
    public String toString() {
        return "DialogShowMsg{msg='" + this.msg + '\'' + ", tag=" + this.tag + '}';
    }
}
