/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import java.io.PrintStream;

public class TestVar {
    public static void main(String[] args) {
        String suffix = "";
        String imageSuffix = "gif|jpg|jpeg|bmp|png";
        String[] list = imageSuffix.split("\\|");
        for (String l : list) {
            suffix = "*." + l + ";" + suffix;
        }
        System.out.println(suffix.substring(0, suffix.length() - 1));
    }
}