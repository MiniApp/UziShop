/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import com.iskyshop.core.tools.CommUtil;
import java.awt.Font;
import java.io.PrintStream;

public class TestWaterMark {
    public static void main(String[] args) {
        String pressImg = "D:\\logo.jpg";
        String targetImg = "D:\\2.jpg";
        int pos = 9;
        float alpha = 0.9F;
        try {
            CommUtil.waterMarkWithText(targetImg, "D:\\2.jpg", "iskyshop", "#FF0000", new Font("宋体", 1, 30), pos,
                    100.0F);
            System.out.println("图片水印完成！");
        } catch (Exception localException) {
        }
    }
}