package com.accumulate.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    /**
     * 姓名
     */
    private String name;

    /**
     * 薪资
     */
    private int salary;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 地区
     */
    private String area;
}
