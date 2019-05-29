package com.ley.java.concurrent;

import org.junit.Test;

public class NumberTest {


    @Test
    public void testInt() {
        Integer num = Float.floatToIntBits(1.1f);
        System.out.println(num);
        System.out.println(Float.intBitsToFloat(num));
    }
}
