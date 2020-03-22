package com.catmaker.stream;

import com.catmaker.data.Trader;
import com.catmaker.data.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class TransactionTest {

    static List<Transaction> transactions = null;

    @BeforeAll
    public static void before(){
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
    }

    /**
     * 1. 找出2011年发生的所有交易，并按交易额排序（从高到底）
     */
    @Test
    public void test1() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((t1, t2) -> t2.getValue() - t1.getValue())
                .forEach(System.out::println);
    }

    /**
     * 2. 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test2() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 3. 查找来自剑桥的交易员，并按姓名排序
     */
    @Test
    public void test3() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 4. 返回所有交易员的姓名字符串，并按字母顺序排序
     */
    @Test
    public void test4() {
        transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * 5. 有没有交易员是在米兰工作的？
     */
    @Test
    public void test5() {
        boolean milan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(milan);
    }

    /**
     * 6. 打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test6() {
        Integer cambridge = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.summingInt(Transaction::getValue));
        System.out.println(cambridge);

        int cambridge1 = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .mapToInt(t -> t.getValue())
                .sum();
        System.out.println(cambridge1);
    }

    /**
     * 7. 所有交易中，最高的交易额是多少？
     */
    @Test
    public void test7() {
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue).max();
        System.out.println(max.getAsInt());
    }

    /**
     * 8. 找到交易额最小的交易
     */
    @Test
    public void test8() {
        Optional<Transaction> min = transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue));
        System.out.println(min.get());
    }
}