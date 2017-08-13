package com.manage.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.manage.constant.Constant;
import com.manage.dao.DBFunctions;
import com.manage.repository.NumberIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Zxing生成条形码
 * Created by Administrator on 2017/8/3.
 */
public class ZxingEAN13EncoderHandler {
    @Autowired
    NumberIndexRepository repository;
    /**
     * 生成条形码图片
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
     * 条形码数据采集
     * @return
     */
    public String generateZxing(){
        try {
            StringBuffer buffer=new StringBuffer();
            buffer.append("690");
           /* Numindex numindex=repository.findAll().get(0);*/
            //获取序号最新值
            String strIndex=String.valueOf(DBFunctions.getNumIndex());
            //更新序号值
            int re=DBFunctions.updateIndex();
            if(re==0){
                return Constant.RESULT_FAIL;
            }
            String indexID=strIndex.substring(strIndex.length()-5);
            buffer.append(indexID);
           /* numindex.setIndexID(Integer.parseInt(indexID)+1);
            repository.save(numindex);*/
            String temp= String.valueOf(System.currentTimeMillis());
            buffer.append(temp.substring(temp.length()-4));
            buffer.append( MakeCheckCode(buffer.toString()));
            return buffer.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }


    }

    /**
     * 生成校验码
     * @return
     */
    public int MakeCheckCode(String strCode12){
        //String strCode12=zxingCode12.toString();
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

