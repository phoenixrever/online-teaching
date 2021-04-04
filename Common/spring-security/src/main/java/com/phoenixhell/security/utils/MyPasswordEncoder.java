package com.phoenixhell.security.utils;

import com.phoenixhell.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5.encrypt((String) rawPassword).equals(encodedPassword);
    }
}
