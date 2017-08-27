package com.manage.print;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/20.
 */
public class Pic {
    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成条码图片
     */
    public static void makeZxingPic(String zxingcode,String name,String price) {
        BufferedImage ImageNew = new BufferedImage(120, 80,
                BufferedImage.TYPE_INT_RGB);//图片大小为120*80
        Graphics2D g2 = (Graphics2D) ImageNew.getGraphics();

        g2.setColor(Color.white);//图片背景为白色
        g2.fillRect(0, 0, 120, 80);//背景面积及公位置
        g2.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 14); //根据指定名称、样式和磅值大小，创建一个新 Font。
        g2.setFont(font);//设置标题打印字体
        float height=font.getSize2D();
        g2.drawString("售价：¥"+price, 4, height+2);
        //g2.drawString("售价：¥"+price, 122, height+2);
        font=new Font("宋体", Font.BOLD ,10);
        g2.setFont(font);//设置正文字体
        float height1=font.getSize2D();
        g2.drawString("特征："+name, 4, height+height1+4);
        //g2.drawString("特征："+name, 122, height+height1+2);
        g2.drawImage(zoomInImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png")), 5, 38, null);
        //g2.drawImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png"), 125, 30, null);
        g2.setFont(new Font("宋体", Font.PLAIN, 12));
        g2.drawString(zxingcode, 36, 74);
        //g2.drawString(zxingcode, 140, 65);
        /*g2.setStroke(new BasicStroke(4.0f));//线条粗细
        g2.setColor(Color.blue);//线条颜色
       g2.drawLine(100, 100, 150, 100);//线条起点及终点位置*/

        File outFile = new File("d:/goodspic/"+zxingcode+".jpg");
        try {
            ImageIO.write(ImageNew, "jpg", outFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 写图片
    }

    /**
     * 图片放大缩小
     */
    public static BufferedImage  zoomInImage(BufferedImage  originalImage){
        BufferedImage newImage = new BufferedImage(156,24,originalImage.getType());
        Graphics g = newImage.getGraphics();

        g.drawImage(originalImage, -20,0,156,24,null);

        g.dispose();

        return newImage;

    }
}
