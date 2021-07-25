package com.accumulate.common;

import com.accumulate.common.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 来自于: https://mp.weixin.qq.com/s/2WYpN1hcTgVChfcXtnvK5g
 */
public class StreamTest {

    private List<Employee> initList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Tom", 8900, 23, "male", "New York"));
        employeeList.add(new Employee("Jack", 7000, 25, "male", "Washington"));
        employeeList.add(new Employee("Lily", 7800, 21, "female", "Washington"));
        employeeList.add(new Employee("Anni", 8200, 24, "female", "New York"));
        employeeList.add(new Employee("Owen", 9500, 25, "male", "New York"));
        employeeList.add(new Employee("Alisa", 7900, 26, "female", "New York"));
        return employeeList;
    }

    /**
     * 遍历 匹配
     */
    @Test
    public void test() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        findFirst.ifPresent(integer -> System.out.println("匹配第一个值：" + integer));
        findAny.ifPresent(i -> System.out.println("匹配任意一个值：" + i));
        System.out.println("是否存在小于6的值：" + anyMatch);
    }

    /**
     * 筛选出Integer集合中大于7的元素，并打印出来
     */
    @Test
    public void testFilter() {

        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        Stream<Integer> stream = list.stream();
        stream.filter(x -> x > 7).forEach(System.out::println);
    }

    /**
     * 筛选员工中工资高于8000的人，并形成新的集合
     */
    @Test
    public void testSalary() {
        List<Employee> employeeList = initList();

        List<String> list = employeeList.stream()
                .filter(x -> x.getSalary() > 8000)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("高于8000的员工姓名:" + list);
    }

    /**
     * 获取String集合中最长的元素
     */
    @Test
    public void testMax() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    /**
     * 获取Integer集合中的最大值
     */
    @Test
    public void testCompare() {
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);

        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }

    /**
     * 获取员工工资最高的人
     */
    @Test
    public void testObjectComparator() {
        List<Employee> employeeList = initList();

        Optional<Employee> employee = employeeList.stream().max(Comparator.comparingInt(Employee::getSalary));
        System.out.println(employee.get().getName());
    }

    /**
     * 英文字符串数组的元素全部改为大写。整数数组每个元素+3
     */
    @Test
    public void testMap() {
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);
    }

    /**
     * 将员工的薪资全部增加1000
     */
    @Test
    public void testMap1() {
        List<Employee> employeeList = initList();

        // 不改变原来员工集合的方式
        List<Employee> newEmployeeList1 = employeeList.stream().map(employee -> {
            employee.setSalary(employee.getSalary() + 1000);
            Employee newEmployee = Employee.builder()
                    .name(employee.getName())
                    .age(employee.getAge())
                    .salary(employee.getSalary() + 1000)
                    .area(employee.getArea())
                    .sex(employee.getSex())
                    .build();
            return newEmployee;
        }).collect(Collectors.toList());
        System.out.println("不改变原来员工集合: " + newEmployeeList1);

        // 改变原来员工集合的方式
        List<Employee> newEmployeeList2 = employeeList.stream().map(employee -> {
            employee.setSalary(employee.getSalary() + 1000);
            return employee;
        }).collect(Collectors.toList());
        System.out.println("改变原来员工集合:" + newEmployeeList2);
    }

    /**
     * 将两个字符数组合并成一个新的字符数组
     */
    @Test
    public void testFlatMap() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    /**
     * 求Integer集合的元素之和、乘积和最大值
     */
    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);
    }

    /**
     * 求所有员工的工资之和和最高工资
     */
    @Test
    public void testFlatMap1() {
        List<Employee> employeeList = initList();

        // 求工资之和方式1：
        Optional<Integer> sumSalary = employeeList.stream().map(Employee::getSalary).reduce(Integer::sum);
        // 求工资之和方式2：
        Integer sumSalary2 = employeeList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式：
        Integer maxSalary = employeeList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2);
        System.out.println("最高工资：" + maxSalary);
    }

    /**
     * toList、toSet和toMap
     */
    @Test
    public void testTo() {
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        List<Employee> employeeList = initList();
        Map<String, Employee> map = employeeList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Employee::getName, p -> p));

        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }


    /**
     * 统计
     */
    @Test
    public void testCollectors() {
        List<Employee> employeeList = initList();

        // 求总数
        Long count = employeeList.stream().collect(Collectors.counting());
        // 求平均工资
        Double avgSalary = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));

        // 求最高工资
        Optional<Integer> max = employeeList.stream().map(Employee::getSalary).max(Integer::compare);

        // 求工资之和
        Integer sum = employeeList.stream().collect(Collectors.summingInt(Employee::getSalary));
        int sum1 = employeeList.stream().mapToInt(Employee::getSalary).sum();

        // 一次性统计所有信息
        DoubleSummaryStatistics collect = employeeList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + avgSalary);
        System.out.println("员工最高工资：" + max.get());
        System.out.println("员工工资总和：" + sum + "," + sum1);
        System.out.println("员工工资所有统计：" + collect);
    }

    /**
     * 将员工按薪资是否高于8000分为两部分；将员工按性别和地区分组
     */
    @Test
    public void testGroup() {
        List<Employee> employeeList = initList();
        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Employee>> part = employeeList.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 8000));

        // 将员工按性别分组
        Map<String, List<Employee>> group = employeeList.stream().collect(Collectors.groupingBy(Employee::getSex));

        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Employee>>> group2 = employeeList.stream().collect(Collectors.groupingBy(Employee::getSex, Collectors.groupingBy(Employee::getArea)));

        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }


    /**
     * joining
     */
    @Test
    public void testJoin() {
        List<Employee> employeeList = initList();
        String names = employeeList.stream().map(Employee::getName).collect(Collectors.joining(","));
        System.out.println("所有的员工的姓名:" + names);

        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);
    }

    /**
     * 排序
     */
    @Test
    public void testSorted() {
        List<Employee> employeeList = initList();
        // 按工资升序排序（自然排序）
        List<String> nameList = employeeList.stream().sorted(Comparator.comparing(Employee::getSalary)).map(Employee::getName).collect(Collectors.toList());
        // 按工资倒序排序
        List<String> nameList2 = employeeList.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).map(Employee::getName).collect(Collectors.toList());
        // 先按工资再按年龄升序排序
        List<String> nameList3 = employeeList.stream().sorted(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getAge)).map(Employee::getName).collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（降序）
        List<String> nameList4 = employeeList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Employee::getName).collect(Collectors.toList());
        System.out.println("按工资升序排序：" + nameList);
        System.out.println("按工资降序排序：" + nameList2);
        System.out.println("先按工资再按年龄升序排序：" + nameList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + nameList4);
    }

    /**
     * 提取/组合
     */
    @Test
    public void testIterate() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> limitList = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> limitList2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + limitList);
        System.out.println("skip：" + limitList2);
    }

}
