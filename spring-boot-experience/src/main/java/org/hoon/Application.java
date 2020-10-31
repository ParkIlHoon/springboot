package org.hoon;

import org.hoon.listener.SampleListener;
import org.hoon.properties.IhoonProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        // Application Context 구성 전 이벤트 리스너 추가
        application.addListeners(new SampleListener());

        application.run(args);
    }
}
