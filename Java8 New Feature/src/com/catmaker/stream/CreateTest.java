package com.catmaker.stream;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、 Stream 的三个操作步骤
 * 1. 创建 Stream
 * 2. 中间操作
 * 3. 终止操作（）
 *
 * @author Cat Maker
 */
public class CreateTest {
    /**
     * 1. 创建 Stream
     */
    @Test
    public void create() {
        //1.1 可以通过Collection系列集合提供的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //1.2 通过Arrays中的静态方法stream()获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);

        //1.3 通过Stream类中的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //1.4 创建无限流
        //1.4.1 迭代
        Stream<Integer> stream4 = Stream.iterate(0, x -> x + 2);
        stream4.limit(0).forEach(System.out::println);
        //1.4.2 生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }
}
