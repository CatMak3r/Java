package com.catmaker.stream;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 3.2 归约
     *      reduce(T identity, BinaryOperator) / reduce(BinaryOperator): 可以将流中元素反复结合起来，得到一个值
     */
    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 将identity作为x，从流中取出第一个元素作为y，相加得到的结果作为identity
        // 这里就是将0作为x，将1作为y，相加的和为1，赋值identity
        // 将1作为x，将2作为y，相加的和为3，赋值identity
        // 不断重复
        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);
        System.out.println("-------------------------------");

        // 累计算出所有员工工资总和
        Optional<Double> reduce1 = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(reduce1.get());
    }

    /**
     * 3.3 收集
     *      collect: 将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void testCollect1() {
        // 把所有员工的名字提取出来，封装到集合中
        List<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(collect);

        System.out.println("---------------------------");
        Set<String> collect1 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        System.out.println(collect1);

        System.out.println("---------------------------");
        HashSet<String> collect2 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        System.out.println(collect2);
    }
    @Test
    public void testCollect2() {
        // 获取总数
        Long collect = employees.stream()
                .collect(Collectors.counting());
        System.out.println(collect);
        System.out.println("===========================");
        // 获取平均值
        Double collect1 = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect1);
        System.out.println("----------------------------");
        // 工资总和
        Double collect2 = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(collect2);
        System.out.println("----------------------------");
        // 工资最多的员工信息
        Optional<Employee> collect3 = employees.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(collect3.get());
        System.out.println("----------------------------");
        // 工资最少的员工信息
        Optional<Double> collect4 = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(collect4.get());
    }

    /**
     * 分组
     */
    @Test
    public void testCollect3() {
        // 按照状态分组
        Map<Employee.Status, List<Employee>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect);
    }

    /**
     * 多级分组
     */
    @Test
    public void testCollect4() {
        // 按照状态分组
        Map<Employee.Status, Map<Object, List<Employee>>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(collect);
    }

    /**
     * 分区
     */
    @Test
    public void testCollect5() {
        Map<Boolean, List<Employee>> collect = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 8000));
        System.out.println(collect);
    }

    @Test
    public void testCollect6() {
        DoubleSummaryStatistics collect = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect.getMax());
        System.out.println(collect.getAverage());
        System.out.println(collect.getCount());
        System.out.println(collect.getMin());
        System.out.println(collect.getSum());
    }

    @Test
    public void testCollect7() {
        String collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect);
    }
}
