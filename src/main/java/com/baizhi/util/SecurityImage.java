package com.baizhi.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

public class SecurityImage {
    /**
     * �����֤��ͼƬ

     * @param securityCode

     * @return

     */
    public static BufferedImage createImage(String securityCode){

         

        int codeLength = securityCode.length();//��֤�볤��

        int fontSize = 20;//�����С

        int fontWidth = fontSize+1;

         

        //ͼƬ���

        int width = codeLength*fontWidth+6;

        int height = fontSize*2+1;

        //ͼƬ

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);//���ñ���ɫ

        g.fillRect(0, 0, width, height);//��䱳��

        g.setColor(Color.LIGHT_GRAY);//���ñ߿���ɫ

        g.setFont(new Font("Arial", Font.BOLD, height-2));//�߿�������ʽ

        g.drawRect(0, 0, width-1, height-1);//���Ʊ߿�

        //�������

        Random rand = new Random();

        g.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < codeLength*6; i++) {

            int x = rand.nextInt(width);

            int y = rand.nextInt(height);

            g.drawRect(x, y, 1, 1);//����1*1��С�ľ���

        }

        //������֤��

        int codeY = height-10;

        g.setColor(new Color(19,148,246));

        g.setFont(new Font("Georgia", Font.BOLD, fontSize));
        for(int i=0;i<codeLength;i++){
        	double deg=new Random().nextDouble()*20;
        	g.rotate(Math.toRadians(deg), i*16+13,codeY-7.5);
            g.drawString(String.valueOf(securityCode.charAt(i)), i*16+5, codeY);
            g.rotate(Math.toRadians(-deg), i*16+13,codeY-7.5);
        }
       
        g.dispose();//�ر���Դ

        return image;

    }

     

    /**

     * ������֤��ͼƬ������ʽ

     * @param securityCode

     * @return

     */

    private static ByteArrayInputStream getImageAsInputStream(String securityCode){

        BufferedImage image = createImage(securityCode);

        return convertImageToStream(image);

    }

     

    /**

     * ��BufferedImageת����ByteArrayinputStream

     * @param image

     * @return

     */

    private static ByteArrayInputStream convertImageToStream(BufferedImage image){

        ByteArrayInputStream inputStream = null;

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);

        try {

            encoder.encode(image);

            byte[] b = outStream.toByteArray();

            inputStream = new ByteArrayInputStream(b);

        } catch (Exception e) {
        	e.printStackTrace();
        }

        return inputStream;

    }

}
