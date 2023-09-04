package com.crab;

import com.crab.utils.JwtHelper;
import com.crab.utils.MD5Util;
import io.jsonwebtoken.Jwt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xxp
 * @version sota；
 */

public class creatExample {
    public static void main(String[] args) {
        String encrypt = MD5Util.encrypt("123456");
        System.out.println(encrypt);
    }
}
