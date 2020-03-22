package com.catmaker.stream;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Practice
 *
 * @author chenhang
 * @date 2020/3/21 9:29
 */
public class PracticeTest {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99, Employee.Status.FREE),
            new Employee("李四", 58, 5555.55, Employee.Status.BUSY),
            new Employee("王五", 26, 3333.33, Employee.Status.VACATION),
            new Employee("赵六", 36, 6666.66, Employee.Status.FREE),
            new Employee("田七", 12, 8888.88, Employee.Status.BUSY)
    );
    /**
     * 1. 给定一个数字列表，如何返回一个由每个数的平方构成的列表？
     *      如：给定{1, 2, 3, 4, 5}，应该返回{1, 4, 9, 16, 25}
     */
    @Test
    public void test1() {
        List<Integer> collect = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(a -> a * a)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 2. 用map和reduce方法数一数流中有多少个Employee
     */
    @Test
    public void test2() {
        Optional<Integer> reduce = employees.stream()
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());
    }
}
