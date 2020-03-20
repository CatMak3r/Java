package com.catmaker.stream;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * StopTest
 * 3. 终止操作
 *
 * @author chenhang
 * @date 2020/3/20 22:48
 */
public class StopTest {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99, Employee.Status.FREE),
            new Employee("李四", 58, 5555.55, Employee.Status.BUSY),
            new Employee("王五", 26, 3333.33, Employee.Status.VACATION),
            new Employee("赵六", 36, 6666.66, Employee.Status.FREE),
            new Employee("田七", 12, 8888.88, Employee.Status.BUSY)
    );
    /**
     * 3.1 查找与匹配
     *      allMatch: 检查是否匹配所有元素
     *      anyMatch: 检查是否至少匹配一个元素
     *      noneMatch: 检查是否没有匹配所有元素
     *      findFirst: 返回第一个元素
     *      findAny: 返回当前流中的任意元素
     *      count: 返回流中元素的总个数
     *      max: 返回流中最大值
     *      min: 返回流中最小值
     */

    @Test
    public void test1() {
        // allMatch
        boolean b1 = employees.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);
        // anyMatch
        boolean b2 = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);
        // noneMatch
        boolean b3 = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

        // findFirst
        // Optional, 把有可能为空的值封装到Optional中
        // orElse(): 如果为空，那么
        Optional<Employee> first = employees.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(first.get());

        /// findAny
        // Optional<Employee> any = employees.stream()
        // parallelStream(): 并行流，多个线程同时找
        Optional<Employee> any = employees.parallelStream()
                .filter(e -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(any.get());
    }

    /**
     * count: 返回流中元素的总个数
     * max: 返回流中最大值
     * min: 返回流中最小值
     */
    @Test
    public void test2() {
        // count
        long count = employees.stream()
                .count();
        System.out.println(count);

        // max
        Optional<Employee> max = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());

        //min
        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(min.get());
    }
}
