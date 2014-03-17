/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import com.iskyshop.core.tools.CommUtil;
import java.io.File;

public class TestImage {
    public static void main(String[] args) {
        File f = new File("F://JAVA_PRO//iskyshop//upload//store//163840");
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; ++i) {
            File temp = files[i];
            String source = temp.getPath();
            String target = temp.getPath() + "_small.jpg";
            CommUtil.createSmall(source, target, 160, 160);
        }
    }
}