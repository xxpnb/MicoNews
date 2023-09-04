package com.crab;

import com.crab.utils.JwtHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xxp
 * @version sota；
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
public class JwtTest {
    @Autowired
    private JwtHelper jwtHelper;
    @Test
    public void test(){
        String token = jwtHelper.createToken(1L);
        System.out.println(token);

        int i = jwtHelper.getUserId(token).intValue();
        System.out.println(i);

        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println(expiration);
    }



}
