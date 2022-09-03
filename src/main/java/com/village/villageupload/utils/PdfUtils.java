package com.village.villageupload.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

@RestController
public class PdfUtils {
    public static void main(String[] args) throws Exception {

//        waterMark("D:\\pdf\\123.pdf", "D:\\pdf\\123Demo.pdf", "仅限际恒锐智使用，其他无效");
    }

    @GetMapping("/pdf")
    public void pdf(HttpServletResponse response) throws IOException, DocumentException {
        waterMark("http://10.198.6.181:31900/cscec81-cloud/202209/01/20220901-135851-826-4674.pdf", "仅限际恒锐智使用，其他无效",response);
    }

    public static void waterMark(String inputFile, String waterMarkName, HttpServletResponse response) throws IOException, DocumentException {
        URL url = new URL(inputFile);
        PdfReader reader = new PdfReader(url);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        PdfGState gs = new PdfGState();
        //设置透明度
        gs.setFillOpacity(0.3f);
        BaseFont font = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        for (int i = 1; i < total; i++) {
            //**注意！这里是获取pdf最顶层，否则会使得部分pdf文档无法显示水印
            content = stamper.getOverContent(i);
            content.beginText();
            content.setGState(gs);
            content.setColorFill(BaseColor.LIGHT_GRAY);
            content.setFontAndSize(font, 30);
            // 开始写入水印
            float pageWidth = reader.getPageSize(i).getWidth()/2;
            float pageHigh = reader.getPageSize(i).getHeight()/2;
            content.showTextAligned(Element.ALIGN_CENTER, waterMarkName, pageWidth, pageHigh, 45);
            content.endText();
        }
        stamper.close();
        filePreview(response,outputStream.toByteArray());
        outputStream.close();
    }

    /**
     * @param inputFile 你的PDF文件地址
     * @param waterMarkName 你的水印
     * @return
     */
//    public static void waterMark(String inputFile, String waterMarkName, HttpServletResponse response) {
//        try {
//            URL url = new URL(inputFile);
//            PdfReader reader = new PdfReader(url);
//            // 输出文件字符流
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            PdfStamper stamper = new PdfStamper(reader, outputStream);
//            //这里的字体设置比较关键，这个设置是支持中文的写法
//            BaseFont base = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
//            int total = reader.getNumberOfPages() + 1;
//
//            PdfContentByte under;
//            Rectangle pageRect = null;
//            PdfGState gs = new PdfGState();
//            // 设置透明度为0.2
//            gs.setFillOpacity(0.3f);
//            for (int i = 1; i < total; i++) {
//                pageRect = stamper.getReader().getPageSizeWithRotation(i);
//                // 计算水印X,Y坐标
//                float x = pageRect.getWidth()/2;
//                float y = pageRect.getHeight()/2;
//                // 获得PDF最顶层
//                under = stamper.getOverContent(i);
//                // 获得PDF最底层
////                under = stamper.getUnderContent(i);
//                under.beginText();
//                under.saveState();
//                under.setLineWidth(0.1f);
//                under.setGState(gs);
//                under.setFontAndSize(base, 30);
//                // 颜色设置
//                under.setColorFill(BaseColor.GREEN);
//                // 水印文字成45度角倾斜
//                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x, y, 45);
//                // 添加水印文字
//                under.endText();
//                under.stroke();
//            }
//            stamper.close();
//            filePreview(response,outputStream.toByteArray());
////            DownloadUtil.download("ss.pdf", outputStream.toByteArray(), response);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void filePreview(HttpServletResponse httpServletResponse, byte[] fileData) throws IOException {
        String mime= MimeUtils.getMime("pdf");
        httpServletResponse.setHeader("Content-Type", mime);
        httpServletResponse.setHeader("Accept-Ranges", "bytes");

        String fileName = "ss.pdf";
        try {
            fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpServletResponse.addHeader("Content-Disposition", "fileName=" + fileName);// 设置文件名
        OutputStream outputStream = null;
        outputStream = httpServletResponse.getOutputStream();
        try {

            outputStream.write(fileData);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            outputStream.close();
        }
    }
}
