package com.company.demo.web.screens;

import com.company.demo.service.SubscriptionBridgeService;
import com.company.demo.service.SubscriptionBridgeService.DemoEvent;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private SubscriptionBridgeService subscriptionBridgeService;

    public void onPushBtnClick() {
        subscriptionBridgeService.fire(new DemoEvent("It works!"));
    }
}