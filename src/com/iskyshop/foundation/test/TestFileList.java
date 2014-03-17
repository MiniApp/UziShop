/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import java.io.File;
import java.io.PrintStream;

public class TestFileList {
    public static void main(String[] args) {
        String strPath = "F:\\JAVA_PRO\\iskyshop\\data\\20120829_1";
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        for (File f : files)
            System.out.println(f.getName());
    }
}