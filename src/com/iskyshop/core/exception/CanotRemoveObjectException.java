/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.exception;

import java.io.PrintStream;

public class CanotRemoveObjectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public void printStackTrace() {
        System.out.println("É¾³ý¶ÔÏó´íÎó!");
        super.printStackTrace();
    }
}