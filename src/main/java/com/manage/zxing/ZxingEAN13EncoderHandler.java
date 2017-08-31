package com.manage.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.manage.constant.Constant;
import com.manage.dao.DBFunctions;
import com.manage.repository.NumberIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zxing生成条形码
 * Created by Administrator on 2017/8/3.
 */
public class ZxingEAN13EncoderHandler {
    @Autowired
    NumberIndexRepository repository;

    public  boolean checkStandardUPCEANChecksum(String s) throws FormatException {
        int length = s.length();
        if (length == 0) {
            return false;
        }

        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        sum *= 3;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }

    /**
     * 生成条形码图片
     * @param contents 690 123456789 5  中间9位为商品在库ID
     * @param width
     * @param height
     * @param imgPath
     */
    public void encode(String contents, int width, int height, String imgPath) throws FormatException {
        System.out.print("条码位："+contents);
        if(checkStandardUPCEANChecksum("00000"+contents)) {
            int codeWidth = 3 + // start guard
                    (7 * 6) + // left bars
                    5 + // middle guard
                    (7 * 6) + // right bars
                    3; // end guard
            codeWidth = Math.max(codeWidth, width);
            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                        BarcodeFormat.EAN_8, codeWidth, height, null);

                MatrixToImageWriter
                        .writeToPath(bitMatrix, "png", new File(imgPath).toPath());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       else {
            System.out.print("没有通过校验");
        }
    }

    public void encode2(String contents, int width, int height, String imgPath){
        String format = "png";// 图像类型
        String fileName = "zxing.png";
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;// 生成矩阵
        try {
            bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Path path = FileSystems.getDefault().getPath(imgPath,fileName );
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("输出成功.");
    }
    /**
     * 条形码数据采集
     * @return
     */
    public String generateZxing(){
        try {
            StringBuffer buffer=new StringBuffer();
            buffer.append("6");
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
            buffer.append(temp.substring(temp.length()-1));
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
/*        List<Integer> oddNum=new ArrayList<>();//奇数
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
        int c=totalEvenNum*3+totalOddNum;*/
        String strCode8="00000"+strCode12;
        int length = strCode8.length();
        int sum=0;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = (int) strCode8.charAt(i)- (int) '0';

            sum += digit;
        }
        sum *= 3;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) strCode8.charAt(i)- (int) '0';
            sum += digit;
        }
        int result=10-getSingleNum(sum);
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

