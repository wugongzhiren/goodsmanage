package com.manage.print;

import com.manage.bean.Goods;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 热敏小票打印机方法
 * Created by Administrator on 2017/8/12.
 */
public class TicksInfo implements Printable {
    private List<Goods> goods = new ArrayList<Goods>(); //商品列表
    private String operatorName; //操作员
    private String orderId; //订单编号
    private String totalPrice; //总金额
    private String favorablePrice;//优惠金额
    private String actualCollection; //实收款
    private String giveChange; //找零
    private String vipSalePrice;//会员折扣额
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String saleDate;
    private String payWay;

    public TicksInfo(List<Goods> goods, String operatorName, String orderId, String totalPrice, String favorablePrice, String actualCollection, String giveChange, String vipSalePrice,String payWay) {
        this.goods = goods;
        this.operatorName = operatorName;
        this.orderId = orderId;

        this.totalPrice = totalPrice;
        this.favorablePrice = favorablePrice;
        this.actualCollection = actualCollection;
        this.giveChange = giveChange;
        this.vipSalePrice = vipSalePrice;
        Date currentTime = new Date();
        this.saleDate = sdf.format(currentTime);
        if("1".equals(payWay)){
            this.payWay="现金";
        }
        if("2".equals(payWay)){
            this.payWay="支付宝";
        }
        if("3".equals(payWay)){
            this.payWay="微信";
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(Color.black);//设置打印颜色为黑色
        //打印起点坐标
        double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
        double y = pageFormat.getImageableY();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 y坐标。
        Font font = new Font("宋体", Font.BOLD, 14); //根据指定名称、样式和磅值大小，创建一个新 Font。

        g2.setFont(font);//设置标题打印字体
        float heigth = font.getSize2D();//获取字体的高度
        //设置小票的标题标题
        //g2.drawString("默默百货店", (float) x + 65, (float) y + heigth);
        g2.drawString("默默百货店", (float) x + 65,  heigth);

        float line = 2 * heigth; //下一行开始打印的高度
        g2.setFont(new Font("宋体", Font.PLAIN, 8));//设置正文字体
        heigth = font.getSize2D();// 字体高度

        line += 2;

        //设置订单号
        g2.drawString("订单号:" + orderId,   20, (float) y + line);
        line += heigth;
        //设置订单时间
        g2.drawString("时间 :" + saleDate, (float) x + 20, (float) y + line);
        line += heigth + 2;
        //设置标题
        g2.drawString("名称", (float) x + 20, (float) y + line);
        g2.drawString("单价(元)", (float) x + 80, (float) y + line);
        g2.drawString("数量", (float) x + 120, (float) y + line);
        g2.drawString("小计(元)", (float) x + 160, (float) y + line);
        line += heigth;

        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[]{4.0f}, 0.0f));
//在此图形上下文的坐标系中使用当前颜色在点 (x1, y1) 和 (x2, y2) 之间画一条线。 即绘制虚线
        g2.drawLine((int) x, (int) (y + line), (int) x + 188, (int) (y + line));
        line += heigth;
//设置商品清单
        if (goods != null && goods.size() > 0) {
            for (Goods gdf : goods) {
                g2.drawString(gdf.getGoodsName(), (float) x + 15, (float) y + line);
                g2.drawString(gdf.getPrice().toString(), (float) x + 80, (float) y + line);
                g2.drawString(gdf.getGoodsCount() + "", (float) x + 135, (float) y + line);
                g2.drawString(gdf.getSumPrice(), (float) x + 165, (float) y + line);
                line += heigth;
            }
        }
        g2.drawLine((int) x, (int) (y + line), (int) x + 188, (int) (y + line));
        line += heigth + 2;
        //g2.drawString("商品总数:"+totalGoodsNum+ "件",(float)x+15,(float)y+line);
        g2.drawString("合计:" + totalPrice + " 元", (float) x + 150, (float) y + line);
        line += heigth;
        boolean flag = false;
        if (!"0".equals(favorablePrice)) {
            System.out.print("促销优惠"+favorablePrice);
            g2.drawString("促销优惠:" + favorablePrice + "元", (float) x + 15, (float) y + line);
            flag = true;
        }
        if (!"0".equals(vipSalePrice)) {
            System.out.print("会员折扣:"+vipSalePrice);
            g2.drawString("会员折扣:" + vipSalePrice + "元", (float) x + 100, (float) y + line);
            flag = true;
        }
        if (flag) {
            line += heigth;
        }
        g2.drawString("实收:" +payWay+" " +actualCollection + "元", (float) x + 15, (float) y + line);
        g2.drawString("找零:" + giveChange + "元", (float) x + 150, (float) y + line);
        line += heigth;
        g2.drawString("为了您的权益，钱票请当面点清", (float) x + 45, (float) y + line);
        line += heigth;
        g2.drawString("", (float) x + 45, (float) y + line);
        switch (pageIndex) {
            case 0:
                return PAGE_EXISTS;  //0
            default:
                return NO_SUCH_PAGE;   //1
        }
    }

}
