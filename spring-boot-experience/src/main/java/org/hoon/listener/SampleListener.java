package org.hoon.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * 애플리케이션 맨 처음에 발생하는 이벤트 리스너
 * 애플리케이션 구동 이후 발생하는 이벤트들은 @Component 어노테이션을 이용해 자동 등록이 가능하지만,
 * ApplicationContext 구성 이전의 이벤트는 직접 등록해줘야함
 */
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("Application is starting...");
    }
}
