package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{
    private final int DISCOUNT_PERCENT = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * DISCOUNT_PERCENT / 100;
        } else {
            return 0;
        }
    }
}
