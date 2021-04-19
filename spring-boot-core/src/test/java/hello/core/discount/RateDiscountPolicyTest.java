package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    void VIP고객_할인() {
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        int discount = discountPolicy.discount(member, 10000);
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    void 일반고객_할인() {
        Member member = new Member(1L, "memberBASIC", Grade.BASIC);
        int discount = discountPolicy.discount(member, 10000);
        Assertions.assertThat(discount).isEqualTo(0);
    }
}