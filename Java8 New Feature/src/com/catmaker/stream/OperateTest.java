package com.catmaker.stream;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

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
public class OperateTest {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 58, 5555.55),
            new Employee("王五", 26, 3333.33),
            new Employee("赵六", 36, 6666.66),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88),
            new Employee("田七", 12, 8888.88)
    );

    /**
     * 2. 中间操作：
     *  2.1 筛选与切片：
     *      filter: 接收 Lambda，从流中排除某些元素。
     *      limit: 截断流， 使其元素不超过给定数量。
     *      skip(n): 跳过元素， 返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补。
     *      distinct: 筛选， 通过流所生成元素的hashCode()和equals()去除重复元素
     */
    @Test
    public void testFilter() {
        // 内部迭代：迭代操作由 Stream API 完成，而不是手动迭代（外部迭代）
        // 中间操作：不会执行任何操作
        Stream<Employee> stream = employees.stream().filter(e -> {
            System.out.println("Stream API 的中间操作");
            return e.getAge() > 35;
        });
        // 直到出现终止操作，才会一次性执行全部中间操作。这个过程称作“惰性求值”
        // 终止操作。当没有这句话的时候，就不会打印“Stream API 的中间操作”
        stream.forEach(System.out::println);
    }

    @Test
    public void testLimit() {
        employees.stream()
                .filter(e -> e.getSalary() > 5000)
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    public void testSkip() {
        // skip(n)表示跳过前n个
        employees.stream()
                .filter(e -> e.getSalary() > 5000)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void testDistinct() {
        // distinct 是根据hashCode和equals方法来去重的，所以要想生效就必须重写这两个方法
        employees.stream()
                .filter(e -> e.getSalary() > 5000)
                .distinct()
                .forEach(System.out::println);
    }
}
