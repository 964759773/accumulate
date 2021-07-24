package com.accumulate.common;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void test() {
        String[] arr = {"1"};
        Stream<String> stream = Arrays.stream(arr);

    }
}
