package com.lzs.springsecurity;

import com.lzs.springsecurity.util.RedisUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder encoder = PasswordEncoderFactories. createDelegatingPasswordEncoder();
        System.out.println(encoder. encode("123456"));
    }
    @Test
    void test() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // or HS256, HS384
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Base64 Encoded Key: " + base64Key);
    }

    @Test
    void testRedisson() {
        User user = new User("lzs", "123456", new ArrayList<>());
        RedisUtils.setCacheObject("user", user, Duration.ofSeconds(1600));
    }

    @Test
    void testRedisson2() {
        User user = RedisUtils.getCacheObject("user");
        System.out.println(user);
    }
}
