/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import java.io.PrintStream;
import java.util.Random;

public class TestRandom {
    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(3));
    }
}