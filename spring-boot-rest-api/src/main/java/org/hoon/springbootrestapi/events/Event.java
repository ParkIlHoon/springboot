package org.hoon.springbootrestapi.events;

import lombok.*;
import org.hoon.springbootrestapi.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
public class Event
{
	/**
	 * 이벤트 아이디
	 */
	@Id
	@GeneratedValue
	private Integer id;
	/**
	 * 이벤트 이름
	 */
	private String name;
	/**
	 * 이벤트 설명
	 */
	private String description;
	/**
	 * 등록 시작 일시
	 */
	private LocalDateTime beginEnrollmentDateTime;
	/**
	 * 등록 종료 일시
	 */
	private LocalDateTime closeEnrollmentDateTime;
	/**
	 * 이벤트 시작 일시
	 */
	private LocalDateTime beginEventDateTime;
	/**
	 * 이벤트 종료 일시
	 */
	private LocalDateTime endEventDateTime;
	/**
	 * 이벤트 장소 (Optional)
	 * 이 값이 비어있을 경우 온라인 모임
	 */
	private String location;
	/**
	 * 기본가
	 */
	private int basePrice;
	/**
	 * 최고가
	 */
	private int maxPrice;
	/**
	 * 등록 상한수
	 */
	private int limitOfEnrollment;
	/**
	 * 오프라인 모임 여부
	 */
	private boolean offline;
	/**
	 * 무료 모임 여부
	 */
	private boolean free;
	/**
	 * 이벤트 상태
	 */
	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus = EventStatus.DRAFT;

	@ManyToOne
	private Account manager;

	public void update()
	{
		// update free
		if (this.basePrice == 0 && this.maxPrice == 0)
		{
			this.free = true;
		}
		else
		{
			this.free = false;
		}

		// update offline
		if (this.location == null || this.location.isBlank())
		{
			this.offline = false;
		}
		else
		{
			this.offline = true;
		}
	}
}
