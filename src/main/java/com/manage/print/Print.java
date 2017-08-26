package com.manage.print;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 条码打印
 * Created by Administrator on 2017/8/5.
 */
public class Print {
    /**
     *条码打印
     * @param filePath
     * @param printName 打印机名
     * @param count
     */
    public static void printCommon(String filePath,String printName,int count) {
        String printname="BTP-2300E";
        FileInputStream psStream = null;
        try {
            psStream = new FileInputStream(filePath);
        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        }
        if (psStream == null) {
            return;
        }
        //设置打印数据的格式，此处为图片gif格式
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.GIF;
        //创建打印数据
//      DocAttributeSet docAttr = new HashDocAttributeSet();//设置文档属性
//      Doc myDoc = new SimpleDoc(psStream, psInFormat, docAttr);
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);

        //设置打印属性
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(count));//打印份数

        //查找所有打印服务
        //PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);
        PrintService myPrinter =PrintServiceLookup.lookupDefaultPrintService();
        // this step is necessary because I have several printers configured
        //将所有查找出来的打印机与自己想要的打印机进行匹配，找出自己想要的打印机
        //PrintService myPrinter = null;
       /* for (int i = 0; i < services.length; i++) {
            System.out.println("service found: " + services[i]);
            String svcName = services[i].toString();
            if (svcName.contains(printname)) {
                myPrinter = services[i];
                System.out.println("my printer found: " + svcName);
                System.out.println("my printer found: " + myPrinter);
                break;
            }
        }*/

        //可以输出打印机的各项属性
        AttributeSet att = myPrinter.getAttributes();

        for (Attribute a : att.toArray()) {

            String attributeName;
            String attributeValue;

            attributeName = a.getName();
            attributeValue = att.get(a.getClass()).toString();

            System.out.println(attributeName + " : " + attributeValue);
        }

        if (myPrinter != null) {
            DocPrintJob job = myPrinter.createPrintJob();//创建文档打印作业
            try {
                job.print(myDoc, aset);//打印文档

            } catch (Exception pe) {
                pe.printStackTrace();
            }
        } else {
            System.out.println("no printer services found");
        }
    }

    /**
     * 生成条码图片
     */
    public static void makeZxingPic(String zxingcode,String name,String price) {
        BufferedImage ImageNew = new BufferedImage(240, 75,
                BufferedImage.TYPE_INT_RGB);//图片大小为120*80
        Graphics2D g2 = (Graphics2D) ImageNew.getGraphics();
        g2.setColor(Color.white);//图片背景为白色
        g2.fillRect(0, 0, 240, 75);//背景面积及公位置
        g2.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 12); //根据指定名称、样式和磅值大小，创建一个新 Font。
        g2.setFont(font);//设置标题打印字体
        float height=font.getSize2D();
        g2.drawString("售价：¥"+price, 20, height);
        g2.drawString("售价：¥"+price, 122, height);
        font=new Font("宋体", Font.BOLD, 12);
        g2.setFont(font);//设置正文字体
        float height1=font.getSize2D();
        System.out.println(height);
        System.out.println(height1);
        g2.drawString("特征："+name, 20, height+height1+4);
        g2.drawString("特征："+name, 122, height+height1+4);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png"), 5, 34, null);
        g2.drawImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png"), 125, 34, null);
        g2.setFont(new Font("宋体", Font.PLAIN, 12));
        g2.drawString(zxingcode, 20, 75);
        g2.drawString(zxingcode, 140, 75);
        /*g2.setStroke(new BasicStroke(4.0f));//线条粗细
        g2.setColor(Color.blue);//线条颜色
       g2.drawLine(100, 100, 150, 100);//线条起点及终点位置*/

        File outFile = new File("d:/goodsinfo.png");
        try {
            ImageIO.write(ImageNew, "png", outFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 写图片
    }

    /**
     * 生成条码图片
     */
    public static void makeZxingPicCol2(String zxingcode,String name,String price) {
        BufferedImage ImageNew = new BufferedImage(300, 90,
                BufferedImage.TYPE_INT_RGB);//图片大小为150*90
        Graphics2D g2 = (Graphics2D) ImageNew.getGraphics();
        g2.setColor(Color.white);//图片背景为白色
        g2.fillRect(0, 0, 300, 90);//背景面积及公位置
        g2.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 12); //根据指定名称、样式和磅值大小，创建一个新 Font。
        g2.setFont(font);//设置标题打印字体
        float height=font.getSize2D();
        g2.drawString("售价：¥"+price, 20, height+2);

        font=new Font("宋体", Font.BOLD, 12);
        g2.setFont(font);//设置正文字体
        float height1=font.getSize2D();
        g2.drawString("特征："+name, 20, height+height1+7);


        g2.drawImage(new Pic().loadImageLocal("D:\\zxing\\zxing_EAN13.png"), 0,40 , null);
        g2.setFont(new Font("宋体", Font.PLAIN, 12));
        g2.drawString(zxingcode, 25, 75);

        /*g2.setStroke(new BasicStroke(4.0f));//线条粗细
        g2.setColor(Color.blue);//线条颜色
       g2.drawLine(100, 100, 150, 100);//线条起点及终点位置*/

        File outFile = new File("d:/goodsinfo.png");
        try {
            ImageIO.write(ImageNew, "png", outFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 写图片
    }
}
