package com.catmaker.optional;

import com.catmaker.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * OptionalTest
 *
 * @author chenhang
 * @date 2020/3/22 9:24
 */
public class OptionalTest {
    /**
     * Optional容器类的常用方法：
     *  Optional.of(T t): 创建一个Optional实例
     *  Optional.empty(): 创建一个空的Optional实例
     *  Optional.ofNullable(T t): 若t不为null，创建Optional实例，否则创建空实例
     *  isPresent(): 判断是否包含值
     *  orElse(T t): 如果调用对象包含值，返回该值，否则返回t
     *  orElseGet(Supplier s): 如果调用对象包含值，返回该值，否则返回s获取的值
     *  map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     *  flatMap(Function mapper): 与map类似，要求返回值必须是Optional
     */

    /**
     * Optional.of(T t): 创建一个Optional实例
     */
    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);
        // 如果传入为null，就会直接报空指针异常，方便排查
        Optional<Employee> op1 = Optional.of(null);
        employee = op1.get();
        System.out.println(employee);
    }

    /**
     * Optional.empty(): 创建一个空的Optional实例
     */
    @Test
    public void test2() {
        Optional<Employee> empty = Optional.empty();
        System.out.println(empty.get());
    }

    /**
     * Optional.ofNullable(T t): 若t不为null，创建Optional实例，否则创建空实例
     */
    @Test
    public void test3() {
        Optional<Employee> optional = Optional.ofNullable(new Employee());
        System.out.println(optional.get());

        System.out.println("---------------------------");
        optional = Optional.ofNullable(null);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }
        System.out.println("---------------------------");

        Employee employee = optional.orElse(new Employee("张三", 18, 1888.99));
        System.out.println(employee);

        System.out.println("---------------------------");

        Employee employee1 = optional.orElseGet(Employee::new);
        System.out.println(employee1);
    }

    /**
     * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * flatMap(Function mapper): 与map类似，要求返回值必须是Optional
     */
    @Test
    public void test4() {
        Optional<Employee> optional = Optional.ofNullable(new Employee("张三", 18, 1888.99));
        Optional<String> s = optional.map(e -> e.getName());
        System.out.println(s.get());

        System.out.println("----------------------------");
        Optional<String> s1 = optional.flatMap(e -> Optional.of(e.getName()));
        System.out.println(s1.get());
    }
}
