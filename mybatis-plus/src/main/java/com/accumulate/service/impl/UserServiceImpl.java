package com.accumulate.service.impl;

import com.accumulate.entity.User;
import com.accumulate.mapper.UserMapper;
import com.accumulate.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAll() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> selectByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "age").like("name", name);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * select avg(age) avg_age ,min(age) min_age, max(age) max_age from user group by manager_id having sum(age) < 500;
     * @return
     */
    @Override
    public List<User> getAvgAge() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("manager_id", "avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) > {0}", 30);
        return userMapper.selectList(queryWrapper);
    }
}
