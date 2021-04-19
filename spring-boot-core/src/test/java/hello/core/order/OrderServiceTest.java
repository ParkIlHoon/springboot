package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl(new MemoryMemberRepository());
    OrderService orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());

    DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);

        int itemPrice = 10000;
        Order order = orderService.createOrder(memberId, "itemA", itemPrice);

        Assertions.assertThat(order.calculatePrice()).isEqualTo(10000 - discountPolicy.discount(memberA, itemPrice));
    }
}
