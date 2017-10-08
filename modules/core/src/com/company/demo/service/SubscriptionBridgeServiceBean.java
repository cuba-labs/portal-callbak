package com.company.demo.service;

import com.haulmont.cuba.core.sys.serialization.SerializationSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service(SubscriptionBridgeService.NAME)
public class SubscriptionBridgeServiceBean implements SubscriptionBridgeService {
    private List<Consumer<byte[]>> subscriptions = new ArrayList<>();

    @Override
    public void subscribe(Consumer<byte[]> listener) {
        subscriptions.add(listener);
    }

    @Override
    public void fire(DemoEvent event) {
        // here we have to serialize data, because core/portal uses different class loaders
        byte[] data = SerializationSupport.serialize(event);

        for (Consumer<byte[]> subscription : subscriptions) {
            subscription.accept(data);
        }
    }
}