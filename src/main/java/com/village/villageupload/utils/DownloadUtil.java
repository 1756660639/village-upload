package com.village.villageupload.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.log.Log;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.village.villageupload.exception.FileUploadException;
import com.village.villageupload.exception.enums.FileUploadEnum;
import org.apache.tomcat.util.buf.CharsetUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 文件工具类
 */
public class DownloadUtil {

    private static final Log log = Log.get();

    private static final String UTF_8 = "UTF-8";


    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLUtil.encode(fileName) + "\"");
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 下载文件异常，具体信息为：{}", e.getMessage());
            throw new FileUploadException(FileUploadEnum.FILE_DOWNLOAD_ERROR);
        }
    }

    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     */
    public static void download(File file, HttpServletResponse response) {
        // 获取文件字节
        byte[] fileBytes = FileUtil.readBytes(file);
        //获取文件名称
        String fileName;
        try {
            fileName = URLEncoder.encode(file.getName(), UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error(">>> 下载文件异常，具体信息为：{}", e.getMessage());
            throw new FileUploadException(FileUploadEnum.FILE_DOWNLOAD_ERROR);
        }
        //下载文件
        download(fileName, fileBytes, response);
    }

    /**
     * 导出excel
     * @param list
     * @param response
     * @throws IOException
     */
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
        String tempPath = System.getProperty("java.io.tmpdir") + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(file);
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream out = response.getOutputStream();
        file.deleteOnExit();
        writer.flush(out, true);
        IoUtil.close(out);
    }

}
