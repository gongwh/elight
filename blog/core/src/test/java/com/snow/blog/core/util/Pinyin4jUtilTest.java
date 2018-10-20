package com.snow.blog.core.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class Pinyin4jUtilTest {

    @Test
    public void converterToFirstSpell() {
        String chinese1 = "前端优化";
        String chinese2 = "activemq使用1";
        String chinese3 = "1%";
        String chinese4 = "du";
        String chinese5 = "编辑器功能介绍";
        System.out.println(Pinyin4jUtil.converterToFirstSpell(chinese1));
        System.out.println(Pinyin4jUtil.converterToFirstSpell(chinese2));
        System.out.println(Pinyin4jUtil.converterToFirstSpell(chinese3));
        System.out.println(Pinyin4jUtil.converterToFirstSpell(chinese4));
        System.out.println(Pinyin4jUtil.converterToFirstSpell(chinese5));
    }

    @Test
    public void converterToSpell() {
    }
}