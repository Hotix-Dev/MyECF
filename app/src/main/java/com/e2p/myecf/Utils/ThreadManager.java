package com.e2p.myecf.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static ThreadPoolExecutor threadPoolExecutor;

    public static synchronized ThreadPoolExecutor getInstance() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    4,
                    20,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(50),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
        }
        return threadPoolExecutor;
    }
}
