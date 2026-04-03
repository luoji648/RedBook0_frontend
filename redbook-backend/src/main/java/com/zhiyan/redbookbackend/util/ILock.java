package com.zhiyan.redbookbackend.util;

public interface ILock {
    boolean tryLock(long timeoutSec);

    void unlock();
}
