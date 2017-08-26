package com.manage.print;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Created by Administrator on 2017/8/26.
 */
public class GoodsInfoPrint implements Printable {
   private String price;
    private String name;
    private String zxingcode;
    public GoodsInfoPrint(String price,String name,String zxingcode) {
        this.price=price;
        this.name=name;
        this.zxingcode=zxingcode;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        /*BufferedImage ImageNew = new BufferedImage(240, 75,
                BufferedImage.TYPE_INT_RGB);//图片大小为120*80*/
        double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
        double y = pageFormat.getImageableY();
        Graphics2D g2 = (Graphics2D) graphics;

        g2.setColor(Color.white);//图片背景为白色
        g2.fillRect(0, 0, 100, 60);//背景面积及公位置
        g2.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 6); //根据指定名称、样式和磅值大小，创建一个新 Font。
        g2.setFont(font);//设置标题打印字体
        float height=font.getSize2D();
        g2.drawString("售价：¥"+price, (int)x+20, (int)y+height+2);
        g2.drawString("售价：¥"+price, 83, (int)y+height+2);
        font=new Font("宋体", Font.BOLD, 6);
        g2.setFont(font);//设置正文字体
        float height1=font.getSize2D();
        g2.drawString("特征："+name, (int)x+20, height+height1+2);
        g2.drawString("特征："+name, 83, height+height1+2);

        g2.drawImage(zoomInImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png")), (int)x, 18, null);
        g2.drawImage(zoomInImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png")), 75, 18, null);
        g2.setFont(new Font("宋体", Font.PLAIN, 8));
        g2.drawString(zxingcode, (int)x+20, 48);
        g2.drawString(zxingcode, 63, 48);

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
    public  BufferedImage  zoomInImage(BufferedImage  originalImage){
        BufferedImage newImage = new BufferedImage(56,20,originalImage.getType());
        Graphics g = newImage.getGraphics();

        g.drawImage(originalImage, 0,0,56,20,null);

        g.dispose();

        return newImage;

    }
}
