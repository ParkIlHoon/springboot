package org.hoon;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ServletWebServer가 초기화 됬을 때 이벤트 리스너 클래스
 */
@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    /**
     * 어플리케이션 ServletWebServer가 초기화 됬을 때 호출되는 메서드
     * PORT를 콘솔에 찍는다.
     *
     * @param servletWebServerInitializedEvent
     */
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent servletWebServerInitializedEvent) {
        ServletWebServerApplicationContext context = servletWebServerInitializedEvent.getApplicationContext();

        System.out.println(context.getWebServer().getPort());
    }
}
