package org.mose.spring.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description:生成加密密码
 * @Author: 靳磊
 * @Date: 2017/7/20:22
 */
public class TestPasswordEncoder {
    /**
     * 生成加密密文，用于更新数据库
     */
    @Test
    public void createBCrypt() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        System.out.println(encodedPassword);
    }
}
