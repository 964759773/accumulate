package com.accumulate;

import com.accumulate.entity.User;
import com.accumulate.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void testGetAll() {
        List<User> list = userService.getAll();
        assertEquals(5, list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectByName() {
        List<User> users = userService.selectByName("大");
        users.forEach(System.out::println);
    }

    @Test
    public void testGetAvgAge() {
        List<User> list = userService.getAvgAge();
        list.forEach(System.out::println);
    }


}
