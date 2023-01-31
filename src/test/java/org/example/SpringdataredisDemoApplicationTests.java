package org.example;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringdataredisDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作String类型数据
     */
    @Test
    public void testString(){
        redisTemplate.opsForValue().set("city123","guangzhou");

        String city123 = (String) redisTemplate.opsForValue().get("city123");
        System.out.println(city123);

        redisTemplate.opsForValue().set("key1","value1",10L, TimeUnit.SECONDS);

        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("city123", "beijing");
        System.out.println(aBoolean);

    }

    /**
     * 操作Hash类型数据
     */
    @Test
    public void testHash(){

        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.put("002","name","zhangsan");
        hashOperations.put("002","age","20");
        hashOperations.put("002","addr","beijing");

        String name = (String) hashOperations.get("002", "name");
        System.out.println(name);

        //遍历
        Set keys = hashOperations.keys("002");
        for (Object key : keys) {
            System.out.println(key);
        }

        List values = hashOperations.values("002");
        for (Object value : values) {
            System.out.println(value);
        }

    }


    /**
     * 操作list类型
     */
    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPush("myList","a");
        listOperations.leftPushAll("myList","b","c","d");

        List<String> myList = listOperations.range("myList", 0, -1);
        for (String value : myList) {
            System.out.println(value);
        }

        System.out.println();

        Long size = listOperations.size("myList");
        int lsize = size.intValue();
        for (int i = 0; i < lsize; i++) {
            String element = (String) listOperations.rightPop("myList");
            System.out.println(element);
        }


    }

    /**
     * 操作set类型
     */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("myset","a","b","c","a");

        Set<String> myset = setOperations.members("myset");
        for (String value : myset) {
            System.out.println(value);
        }

        System.out.println();

        setOperations.remove("myset","a","b");

        myset = setOperations.members("myset");
        for (String value : myset) {
            System.out.println(value);
        }
    }

    /**
     * 操作ZSet
     */
    @Test
    public void testZSet(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("myZSet","a",10.0);
        zSetOperations.add("myZSet","b",11.0);
        zSetOperations.add("myZSet","c",12.0);
        zSetOperations.add("myZSet","a",13.0);

        Set<String> myZSet = zSetOperations.range("myZSet", 0, -1);
        for (String s : myZSet) {
            System.out.println(s);
        }

        System.out.println();

        zSetOperations.incrementScore("myZSet","b",20);
        myZSet = zSetOperations.range("myZSet", 0, -1);
        for (String s : myZSet) {
            System.out.println(s);
        }

        System.out.println();

        zSetOperations.remove("myZSet","a","b");
        myZSet = zSetOperations.range("myZSet", 0, -1);
        for (String s : myZSet) {
            System.out.println(s);
        }

        System.out.println();
    }

    /**
     * 通用操作
     */
    @Test
    public void testCommon(){
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        Boolean tom = redisTemplate.hasKey("tom");
        System.out.println(tom);

        redisTemplate.delete("myZSet");

        DataType dataType = redisTemplate.type("myset");
        System.out.println(dataType);
    }

}
