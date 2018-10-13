package cn.plou.web.system.permission.verificationCode.service;

import java.awt.Color;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;


public class VerificationCodeService {


    public static List<String> getVerificationCode(ServletRequest request) {

        List<String>list=new ArrayList<String>();

        BufferedImage image = new BufferedImage(80, 32, BufferedImage.TYPE_INT_BGR);

        //得到一支画笔

        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.white);

        graphics.fillRect(0, 0, 80, 32);

        String randomString = "";

        Random random = new Random();

        for (int i = 0; i < 5; i++) {

            //	0~9随机数

            int num = random.nextInt(10);

            //A-Z，a-z随机字母

//            char ch = (char) (random.nextInt(52) + 65);

//            String[] array = {num + "", ch + ""};
            String[] array = {num + ""};

            //随机取出一个

            randomString += array[random.nextInt(1)];


        }

        //干扰线
        int x = random.nextInt(4), y = 0;
        int x1 = 80 - random.nextInt(4), y1 = 0;
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            y = random.nextInt(32 - random.nextInt(4));
            y1 = random.nextInt(32 - random.nextInt(4));
            graphics.drawLine(x, y, x1, y1);
        }


        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * 80 * 32);//噪点数量
        for (int i = 0; i < area; i++) {
            int xxx = random.nextInt(80);
            int yyy = random.nextInt(32);
            int rgb = getRandomColor().getRGB();
            image.setRGB(xxx, yyy, rgb);
        }

        //设置画笔的颜色

        graphics.setColor(Color.black);

        //设置字体的属性

        graphics.setFont(new Font(Font.SERIF, Font.TYPE1_FONT, 25));
        // graphics.fillRect(0,0,70,25);


        //将随机字符画到图片上

        graphics.drawString(randomString, 5, 22);

        UUID uuid = UUID.randomUUID();

        String path = request.getServletContext().getRealPath("/upload")+(uuid.toString().replace("-",""))+".png";
//        String path = System.getProperty("user.dir")+"/src/main/resources/upload/"+(uuid.toString().replace("-",""))+".png";
        File file = new File(path);

        try {

            ImageIO.write(image, "png", file);

            System.out.println("成功");
            System.out.println(randomString);

        } catch (IOException e) {


            e.printStackTrace();

            System.out.println("失败");

        }

        list.add(randomString);
        list.add(path);
      return list;
    }

    private static Color getRandomColor(){
        Color[] color={Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,       //定义颜色数组
                Color.ORANGE,Color.YELLOW,Color.RED,Color.PINK,Color.LIGHT_GRAY};
        Random r=new Random();
        Color c=color[ r.nextInt(8)];
        return c;
    }

//    public static void main(String[] args) {
//        System.out.println(getVerificationCode());
//    }

}