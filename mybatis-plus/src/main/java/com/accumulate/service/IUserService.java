package com.accumulate.service;

import com.accumulate.entity.User;

import java.util.List;

public interface IUserService {

    List<User> getAll();

    /**
     * 模糊匹配
     * @param name
     * @return
     */
    List<User> selectByName(String name);

    /**
     * 按照直属上级进行分组，查询每组的平均年龄，最大年龄，最小年龄
     * @return
     */
    List<User> getAvgAge();
}
