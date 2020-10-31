package org.hoon;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * holoman Bean 자동 등록 설정 클래스
 *
 * @EnableConfigurationProperties(HolomanProperties.class) 으로 application.properties에서 정의한 값을 이용해
 * Bean 객체를 생성한다.
 */
@Configuration
@EnableConfigurationProperties(HolomanProperties.class)
public class HolomanConfiguration {
	/*
	@Bean
	@ConditionalOnMissingBean // Holoman 타입의 Bean이 없을 때만 Bean 등록
	public Holoman holoman()
	{
		Holoman holoman = new Holoman();
		holoman.setHowLong(5);
		holoman.setName("Ilhoon");

		return holoman;
	}
	 */

    /**
     * Properties를 이용해 Bean 생성
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Holoman holoman(HolomanProperties properties) {
        Holoman holoman = new Holoman();
        holoman.setHowLong(properties.getHowLong());
        holoman.setName(properties.getName());

        return holoman;
    }
}
