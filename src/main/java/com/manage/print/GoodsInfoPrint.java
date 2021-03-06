package com.manage.print;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * 标签信息
 * Created by Administrator on 2017/8/26.
 */
public class GoodsInfoPrint implements Printable {
    private String zxingcode;
    private String price;
    private String name;
    public GoodsInfoPrint(String zxingcode,String name,String price) {
        this.zxingcode=zxingcode;
        this.name=name;
        this.price=price;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        /*BufferedImage ImageNew = new BufferedImage(240, 75,
                BufferedImage.TYPE_INT_RGB);//图片大小为120*80*/
        double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
        double y = pageFormat.getImageableY();
        Graphics2D g2 = (Graphics2D) graphics;
g2.translate(x,y);
        g2.setColor(Color.black);
        Font font = new Font("宋体", Font.PLAIN, 6); //根据指定名称、样式和磅值大小，创建一个新 Font。
        g2.setFont(font);//设置标题打印字体
        float height=font.getSize2D();
        g2.drawString("¥"+price, 4, height+2);
        g2.drawString("¥"+price, 64, height+2);
        //g2.drawString("售价：¥"+price, 122, height+2);
        font=new Font("宋体", Font.PLAIN ,6);
        g2.setFont(font);//设置正文字体
        float height1=font.getSize2D();
        if(name.length()<=6) {
            g2.drawString(name, 4, height + height1 + 4);
            g2.drawString(name, 64, height + height1 + 4);
        }
        else{
            g2.drawString(name.substring(0,6), 4, height + height1 + 4);
            g2.drawString(name.substring(0,6), 64, height + height1 + 4);
            g2.drawString(name.substring(6,name.length()), 4, height + height1+height1 + 8);
            g2.drawString(name.substring(6,name.length()), 64, height + height1+height1 + 8);
        }
        //g2.drawString("特征："+name, 122, height+height1+2);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing2\\zxing.png"), 33, 5, null);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing2\\zxing.png"), 90, 5, null);
        //g2.drawImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png"), 125, 30, null);
        g2.setFont(new Font("宋体", Font.PLAIN, 6));
        g2.drawString(zxingcode, 4, 33);
        g2.drawString(zxingcode, 64, 33);
        //g2.drawImage(new Pic().loadImageLocal("D:\\goodspic\\"+zxingcode+".jpg"), 0, 0, null);
       // g2.drawImage(new Pic().loadImageLocal("D:\\goodspic\\"+zxingcode+".jpg"), 63, 0, null);
        switch (pageIndex) {
            case 0:
                return PAGE_EXISTS;  //0
            default:
                return NO_SUCH_PAGE;   //1
        }
    }
    /**
     * 图片放大缩小
     */
    public  BufferedImage  zoomInImage(BufferedImage  originalImage,int radio){
        int width=originalImage.getWidth()/radio;
        int height = originalImage.getHeight()/radio;
        BufferedImage newImage = new BufferedImage(width,height,originalImage.getType());
        Graphics g = newImage.getGraphics();

        g.drawImage(originalImage, 0,0,width,width,null);

        g.dispose();

        return newImage;

    }
}
