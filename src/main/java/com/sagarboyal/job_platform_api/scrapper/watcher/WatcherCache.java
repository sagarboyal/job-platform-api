package com.sagarboyal.job_platform_api.scrapper.watcher;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WatcherCache {

    private final Map<String, String> hashStore = new ConcurrentHashMap<>();

    public String get(String key) {
        return hashStore.get(key);
    }

    public void put(String key, String hash) {
        hashStore.put(key, hash);
    }

    public boolean contains(String key) {
        return hashStore.containsKey(key);
    }

    public void clear() {
        hashStore.clear();
    }
}