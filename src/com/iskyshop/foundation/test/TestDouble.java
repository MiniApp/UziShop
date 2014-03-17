/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import com.iskyshop.core.tools.CommUtil;
import java.io.PrintStream;

public class TestDouble {
    public static void main(String[] args) {
        float a = 290.0F;
        float b = 211.39999F;
        System.out.println(CommUtil.subtract(Double.valueOf(a), Double.valueOf(b)));
    }
}