package org.hoon.springbootrestapi.events;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {
    /**
     * 이벤트 이름
     */
    @NotEmpty
    private String name;
    /**
     * 이벤트 설명
     */
    @NotEmpty
    private String description;
    /**
     * 등록 시작 일시
     */
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    /**
     * 등록 종료 일시
     */
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    /**
     * 이벤트 시작 일시
     */
    @NotNull
    private LocalDateTime beginEventDateTime;
    /**
     * 이벤트 종료 일시
     */
    @NotNull
    private LocalDateTime endEventDateTime;
    /**
     * 이벤트 장소 (Optional)
     * 이 값이 비어있을 경우 온라인 모임
     */
    private String location;
    /**
     * 기본가
     */
    @Min(0)
    private int basePrice;
    /**
     * 최고가
     */
    @Min(0)
    private int maxPrice;
    /**
     * 등록 상한수
     */
    @Min(0)
    private int limitOfEnrollment;
}
