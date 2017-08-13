package com.manage.print;

import com.manage.bean.Goods;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 热敏小票打印机方法
 * Created by Administrator on 2017/8/12.
 */
public class MyPrinter implements Printable {
    private List<Goods> goods=new ArrayList<Goods>(); //商品列表
    private String operatorName="操作员1"; //操作员
    private String orderId; //订单编号
    private String totalGoodsNum; //商品总数
    private String totalPrice; //总金额
    private String favorablePrice;//优惠金额
    private String actualCollection; //实收款
    private String giveChange; //找零
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(Color.black);//设置打印颜色为黑色
        //打印起点坐标
        double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
        double y = pageFormat.getImageableY();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 y坐标。
        Font font = new Font("宋体", Font.BOLD, 10); //根据指定名称、样式和磅值大小，创建一个新 Font。

        g2.setFont(font);//设置标题打印字体
        float heigth = font.getSize2D();//获取字体的高度
        //设置小票的标题标题
        g2.drawString("****时尚购物店", (float) x + 25, (float) y + heigth);


        float line = 2 * heigth; //下一行开始打印的高度
        g2.setFont(new Font("宋体", Font.PLAIN, 8));//设置正文字体
        heigth = font.getSize2D();// 字体高度

        line += 2;
        //设置操作员
        g2.drawString("操作员:" + operatorName, (float) x + 20, (float) y + line);
        line += heigth;

        //设置订单号
        g2.drawString("订单号:" + orderId, (float) x + 20, (float) y + line);
        line += heigth + 2;

//设置标题
        g2.drawString("名称", (float) x + 20, (float) y + line);
        g2.drawString("单价", (float) x + 60, (float) y + line);
        g2.drawString("数量", (float) x + 90, (float) y + line);
        g2.drawString("小计", (float) x + 120, (float) y + line);
        line += heigth;

        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[]{4.0f}, 0.0f));
//在此图形上下文的坐标系中使用当前颜色在点 (x1, y1) 和 (x2, y2) 之间画一条线。 即绘制虚线
        g2.drawLine((int) x, (int) (y + line), (int) x + 158, (int) (y + line));
        line += heigth;

        g2.drawString("钱票请当面点清，离开柜台恕不负责", (float) x + 15, (float) y + line);
        switch (pageIndex) {
            case 0:
                return PAGE_EXISTS;  //0
            default:
                return NO_SUCH_PAGE;   //1
        }
    }
    public void myprint(){
        try {
            //Book 类提供文档的表示形式，该文档的页面可以使用不同的页面格式和页面 painter
            Book book = new Book(); //要打印的文档

            //PageFormat类描述要打印的页面大小和方向
            PageFormat pf = new PageFormat();  //初始化一个页面打印对象
            pf.setOrientation(PageFormat.PORTRAIT); //设置页面打印方向，从上往下，从左往右

            //设置打印纸页面信息。通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
            Paper paper = new Paper();
            paper.setSize(158,30000);// 纸张大小
            paper.setImageableArea(0,0,158,30000);// A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
            pf.setPaper(paper);

            book.append(new MyPrinter(),pf);

            PrinterJob job = PrinterJob.getPrinterJob();   //获取打印服务对象

            job.setPageable(book);  //设置打印类

            job.print(); //开始打印
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
