/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QRCodeDecoderHandler {
    public String decoderQRCode(String imgPath) {
        File imageFile = new File(imgPath);

        BufferedImage bufImg = null;
        String decodedData = null;
        try {
            bufImg = ImageIO.read(imageFile);

            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (DecodingFailedException dfe) {
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();
        }
        return decodedData;
    }

    public static void main(String[] args) {
        QRCodeDecoderHandler handler = new QRCodeDecoderHandler();
        String imgPath = "d:/Michael_QRCode.png";
        String decoderContent = handler.decoderQRCode(imgPath);
        System.out.println("����������£�");
        System.out.println(decoderContent);
        System.out.println("========decoder success!!!");
    }

    class J2SEImage implements QRCodeImage {
        BufferedImage bufImg;

        public J2SEImage(BufferedImage paramBufferedImage) {
            this.bufImg = paramBufferedImage;
        }

        public int getWidth() {
            return this.bufImg.getWidth();
        }

        public int getHeight() {
            return this.bufImg.getHeight();
        }

        public int getPixel(int x, int y) {
            return this.bufImg.getRGB(x, y);
        }
    }
}