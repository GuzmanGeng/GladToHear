package com.mb.mmdepartment.tools.log;

public class MessageOnlyLogFilter implements LogNode {

    LogNode mNext;

    public MessageOnlyLogFilter(LogNode next) {
        mNext = next;
    }

    public LogNode getNext() {
        return mNext;
    }

    public void setNext(LogNode node) {
        mNext = node;
    }

    public MessageOnlyLogFilter() {
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (mNext != null) {
            getNext().println(Log.NONE, null, msg, null);
        }
    }
}
