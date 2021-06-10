package com.accumulate.service.impl;

import com.accumulate.common.User;
import com.accumulate.mapper.UserMapper;
import com.accumulate.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getAll() {
        List<User> list = userMapper.getAll();
        String userJson = new Gson().toJson(list);
        return userJson;
    }
}
