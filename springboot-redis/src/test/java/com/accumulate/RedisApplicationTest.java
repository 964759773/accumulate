package com.accumulate;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisApplicationTest {
    RedissonClient redisson;

    @Test
    public void testSet() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);
        String key = "test1";
        RBucket<Integer> bucket = redisson.getBucket(key);
        bucket.set(1, 60, TimeUnit.MINUTES);
    }

    @Test
    public void testSetAndKeepTTL() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);
        String key = "test1";
        RBucket<Integer> bucket = redisson.getBucket(key);
        bucket.setAndKeepTTLAsync(3);
    }

    @Test
    public void testGet() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);
        RBucket<Integer> test = redisson.getBucket("test1");
        System.out.println(test.get());
    }
}
