package com.mb.mmdepartment.tools.log;

public interface LogNode {

    public void println(int priority, String tag, String msg, Throwable tr);

}
