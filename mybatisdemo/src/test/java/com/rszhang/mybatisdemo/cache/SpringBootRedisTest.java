package com.rszhang.mybatisdemo.cache;

import com.rszhang.mybatisdemo.bean.Employee;
import com.rszhang.mybatisdemo.dao.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate;

    @Test
    public void test01() {
        // 操作字符串
        stringRedisTemplate.opsForValue().append("msg", "hello");  // 写字符串

        String msg = stringRedisTemplate.opsForValue().get("msg");  // 读字符串
        System.out.println(msg);

        // 操作字符串
        stringRedisTemplate.opsForList().leftPush("mylist", "1");
        stringRedisTemplate.opsForList().leftPush("mylist", "2");
    }

    @Test
    public void test02() {
        // Employee 必须要序列化
        Employee employee = employeeMapper.selectByPrimaryKey(1012);
        redisTemplate.opsForValue().set("emp-01", employee);

        // 使用json序列化
        employeeRedisTemplate.opsForValue().set("emp-02", employee);
    }
}
