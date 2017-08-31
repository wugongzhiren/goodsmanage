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
    public GoodsInfoPrint(String zxingcode) {
        this.zxingcode=zxingcode;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        /*BufferedImage ImageNew = new BufferedImage(240, 75,
                BufferedImage.TYPE_INT_RGB);//图片大小为120*80*/
        System.out.print("页号"+pageIndex);
        double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
        double y = pageFormat.getImageableY();
        System.out.print("可成像区域"+x);
        System.out.print("可成像区域"+y);
        Graphics2D g2 = (Graphics2D) graphics;
g2.translate(x,y);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing2\\"+zxingcode+".jpg"), 0, 0, null);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing2\\"+zxingcode+".jpg"), 63, 0, null);
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
