package com.company.demo.service;

import com.haulmont.cuba.core.sys.remoting.BypassSerialization;

import java.io.Serializable;
import java.util.function.Consumer;

public interface SubscriptionBridgeService {
    String NAME = "demo_SubscriptionBridgeService";

    /**
     * Do not require serialization of "consumer" parameter!
     * Use @BypassSerialization.
     *
     * @param listener listener
     */
    void subscribe(@BypassSerialization Consumer<byte[]> listener);

    void fire(DemoEvent data);

    class DemoEvent implements Serializable {
        private String data;

        public DemoEvent(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}