package com.manage.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Zxing生成条形码
 * Created by Administrator on 2017/8/3.
 */
public class ZxingEAN13EncoderHandler {
    /**
     * 编码
     * @param contents 690 123456789 5  中间9位为商品在库ID
     * @param width
     * @param height
     * @param imgPath
     */
    public void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                    .writeToPath(bitMatrix, "png", new File(imgPath).toPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成校验码
     * @return
     */
    public int MakeCheckCode(Long zxingCode12){
        String strCode12=zxingCode12.toString();
        List<Integer> oddNum=new ArrayList<>();//奇数
        List<Integer>  evenNum=new ArrayList<>();//偶数
        List<Integer> num =new ArrayList<>();
        for(int i=0;i<strCode12.length();i++){
            int temp=Integer.parseInt(strCode12.substring(i,i+1));
            if(temp%2==0){
                //为偶数
                evenNum.add(temp);
            }
            else {
                oddNum.add(temp);
            }
        }
        int totalOddNum=0;
        for(int j=0;j<oddNum.size();j++){
            totalOddNum=totalOddNum+oddNum.get(j);
        }
        int totalEvenNum=0;
        for(int m=0;m<evenNum.size();m++){
            totalEvenNum=totalEvenNum+evenNum.get(m);
        }
        int c=totalEvenNum*3+totalOddNum;
        int result=10-getSingleNum(c);
        if(result==10){
            return 0;
        }
        else{
            return result;
        }


    }

    /**
     * 求整数的个位数字
     * @return
     */
    public int getSingleNum(int num){
        String numStr=num+"";
        return Integer.parseInt(numStr.substring(numStr.length()-1,numStr.length()));
    }
}

