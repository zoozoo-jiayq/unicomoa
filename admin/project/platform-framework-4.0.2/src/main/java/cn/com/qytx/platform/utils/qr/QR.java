package cn.com.qytx.platform.utils.qr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.swetake.util.Qrcode;

/**
 * 生成二维码工具类
 * <br/>User:黄普友
 * <br/>Date: 13-4-18
 * <br/>Time: 下午5:26
 */
public class QR  {
    /**
     * 生成二维码
     * @param filePath 二维码存放物理路径，如：d:\websites\qr
     * @param fileName 二维码图片名称
     * @param size     二维码图片尺寸 400表示宽，高都是400
     * @param content   二维码内容
     */
    public static void createQrZxing(String filePath,String fileName,int size,String content) throws Exception
    {
        try
        {

            File checkPath=new File(filePath);
            if(!checkPath.exists())
            {
                //目录不存在，则创建目录
                checkPath.mkdirs();
            }
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            File file = new File(filePath,fileName);
            createQrCode(new FileOutputStream(file),content,size,prefix);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }
    public static void createQr(String filePath,String fileName,int size,String content) throws Exception
    {
        try{
            Qrcode qrcode=new Qrcode();
            qrcode.setQrcodeErrorCorrect('M');
            qrcode.setQrcodeEncodeMode('B');
            qrcode.setQrcodeVersion(7);

            byte[] d =content.getBytes("utf-8");
            BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

            // createGraphics
            Graphics2D g = bi.createGraphics();

            // set background
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, size, size);

            g.setColor(Color.BLACK);
            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            if (d.length>0 && d.length <120){
                boolean[][] b = qrcode.calQrcode(d);
                for (int i=0;i<b.length;i++){
                    for (int j=0;j<b.length;j++){
                        if (b[j][i]) {
                            g.fillRect(j*3+pixoff,i*3+pixoff,3,3);
                        }
                    }
                }
            }

            g.dispose();
            bi.flush();

            File checkPath=new File(filePath);
            if(!checkPath.exists())
            {
                //目录不存在，则创建目录
                checkPath.mkdirs();
            }
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            File file = new File(filePath,fileName);
            ImageIO.write(bi,prefix, file);
        }
        catch (Exception ex)
        {
                ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param contents 字符串内容
     * @param width	   宽度
     * @param height   高度
     * @param imgPath  图片输出路径
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter
                    .writeToFile(bitMatrix, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void createQrCode(OutputStream outputStream, String content,int  size, String imageFormat){
        try{
            // Create the ByteMatrix for the QR-Code that encodes the given String.
            Hashtable hintMap = new Hashtable();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF8");
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hintMap.put(EncodeHintType.MARGIN, 1);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hintMap);

            // Make the BufferedImage that are to hold the QRCode
            int matrixWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);

            // Paint and save the image using the ByteMatrix
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrixWidth; i++){
                for (int j = 0; j < matrixWidth; j++){
                    if (byteMatrix.get(i, j)){
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, imageFormat, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
