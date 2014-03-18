/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.tools;

import com.swetake.util.Qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class QRCodeEncoderHandler {
    public void encoderQRCode(String content, String imgPath) {
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(7);

            System.out.println(content);
            byte[] contentBytes = content.getBytes("gb2312");

            BufferedImage bufImg = new BufferedImage(140, 140, 1);

            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, 140, 140);

            gs.setColor(Color.BLACK);

            int pixoff = 2;

            if ((contentBytes.length > 0) && (contentBytes.length < 120)) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; ++i) {
                    for (int j = 0; j < codeOut.length; ++j)
                        if (codeOut[j][i] != false)
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                }
            } else {
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
            }

            gs.dispose();
            bufImg.flush();

            File imgFile = new File(imgPath);

            ImageIO.write(bufImg, "png", imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String imgPath = "D:/code.png";

        String content = "http://localhost/store_1.htm";

        QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
        handler.encoderQRCode(content, imgPath);

        System.out.println("encoder QRcode success");
    }
}