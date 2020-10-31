package org.hoon;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.properties 를 통해서 bean 생성을 할 수 있도록 해주는 설정
 */
@ConfigurationProperties("holoman")
public class HolomanProperties {
    private String name;

    private int howLong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }
}
