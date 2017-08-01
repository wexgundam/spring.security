package org.mose.spring.security.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;

/**
 * 接收Spring Security发布的AbstractAuthenticationEvent
 *
 * Created by Administrator on 2017/8/1.
 */
@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        System.out.println("Receive event of type:" + event.getClass().getName() + ":" + event.toString());
    }
}
