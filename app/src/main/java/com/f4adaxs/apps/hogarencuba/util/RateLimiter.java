package com.f4adaxs.apps.hogarencuba.util;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

public class RateLimiter<T> {

    private ArrayMap<T, Long> timestamps;
    private Long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timestamps = new ArrayMap<>();
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(T key) {
        Long lastFetched = timestamps.get(key);
        Long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    private Long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(T key) {
        timestamps.remove(key);
    }

}
