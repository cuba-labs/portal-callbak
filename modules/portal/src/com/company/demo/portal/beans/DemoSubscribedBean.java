package com.company.demo.portal.beans;

import com.company.demo.service.SubscriptionBridgeService;
import com.company.demo.service.SubscriptionBridgeService.DemoEvent;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.core.sys.serialization.SerializationSupport;
import com.haulmont.cuba.portal.config.PortalConfig;
import com.haulmont.cuba.security.app.LoginService;
import com.haulmont.cuba.security.global.UserSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class DemoSubscribedBean {
    @Inject
    private Logger log;

    @Inject
    private SubscriptionBridgeService subscriptionBridgeService;

    @Inject
    private LoginService loginService;

    @Inject
    private PortalConfig portalConfig;

    @PostConstruct
    private void subscribeOnCoreEvents() throws Exception {
        UserSession systemSession =
                loginService.getSystemSession(portalConfig.getTrustedClientPassword());
        AppContext.withSecurityContext(new SecurityContext(systemSession), () -> {
            log.info("Subscribe on core events. Works only if deployed in a single tomcat instance!");

            subscriptionBridgeService.subscribe(this::acceptEventFromCore);
        });
    }

    private void acceptEventFromCore(byte[] data) {
        DemoEvent event = (DemoEvent) SerializationSupport.deserialize(data);

        log.info("Event from core caught: {}", event.getData());
    }
}