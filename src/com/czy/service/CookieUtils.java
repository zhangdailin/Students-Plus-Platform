package com.czy.service;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies == null) {// cookie
            return null;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
            return null;
        }
    }
}
