package com.village.villageupload.file.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.common.UserUtil;
import com.village.villageupload.constant.Constant;
import com.village.villageupload.enums.OsEnum;
import com.village.villageupload.exception.FileUploadException;
import com.village.villageupload.exception.enums.FileUploadEnum;
import com.village.villageupload.file.entity.ChunkEntity;
import com.village.villageupload.file.entity.FileUploadEntity;
import com.village.villageupload.file.entity.FileUploadQueryEntity;
import com.village.villageupload.file.entity.FileUploadVoEntity;
import com.village.villageupload.file.mapper.FileUploadMapper;
import com.village.villageupload.file.service.FileUploadService;
import com.village.villageupload.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.village.villageupload.constant.Constant.CHAE_TWO;
import static com.village.villageupload.constant.Constant.ZREO;

@Service
public class FileUploadServiceImpl extends ServiceImpl<FileUploadMapper, FileUploadEntity> implements FileUploadService {

    @Value("${village.file.upload-path.windows}")
    private String windowsSavePath;

    @Value("${village.file.upload-path.linux}")
    private String linuxSavePath;

    @Value("${village.file.upload-path.customWindows}")
    private String customWindowsSavePath;

    @Value("${village.file.upload-path.customLinux}")
    private String customLinuxSavePath;

    @Autowired
    private FileUploadMapper fileUploadMapper;


    @Override
    public FileUploadEntity uploadFiles(MultipartFile file, ChunkEntity chunkEntity) throws Exception {
        chunkEntity.setRelativePath(CommonUtils.removeUrl(chunkEntity.getRelativePath()));
        String fileName = file.getOriginalFilename();
        // ?????????????????????
        if (ObjectUtils.isEmpty(fileName)) {
            throw new FileUploadException(FileUploadEnum.FILE_UPLOAD_ISEMPTY);
        }
        // ??????????????????
        String md5 = chunkEntity.getIdentifier();
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String newName = md5 + suffixName;
        String savePath = "";
        // ???????????????
        if (CommonUtils.getOsName().indexOf(OsEnum.WINDOWS.getName()) != -1){
            savePath = windowsSavePath+CHAE_TWO+UserUtil.getUserId();
        }else {
            savePath = linuxSavePath+CHAE_TWO+UserUtil.getUserId();
        }
//        String date = DateTimeFormatter.ofPattern("MM-dd").format(LocalDateTime.now().minusDays(1));
        savePath += chunkEntity.getRelativePath();
        savePath = CommonUtils.removeUrl(savePath); // ???????????????/
        File newFile = new File(savePath, newName);
        // ???????????????????????????????????????????????????
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        // ????????????
        file.transferTo(newFile);
        // ??????????????????
        FileUploadEntity fileUploadEntity = new FileUploadEntity();
        fileUploadEntity.setId(UuidUtils.uuid(true));
        fileUploadEntity.setOldName(fileName);
        fileUploadEntity.setName(newName);
        fileUploadEntity.setUrl(savePath);
        fileUploadEntity.setSuffix(suffixName);
        fileUploadEntity.setRelativePath(chunkEntity.getRelativePath());
        fileUploadEntity.setMd5(chunkEntity.getIdentifier());
        fileUploadEntity.setIsFile(1);
        fileUploadEntity.setCreateTime(new Date());
        fileUploadEntity.setUserId(UserUtil.getUserId());
        fileUploadEntity.setParentId(chunkEntity.getParentId());
        fileUploadEntity.setIsDel(0);
        if (fileUploadMapper.insert(fileUploadEntity) == 0){
            throw new FileUploadException(FileUploadEnum.FILE_UPLOAD_ISERROR);
        }
        return fileUploadEntity;
    }

    @Override
    public FileUploadEntity findById(String id) {
        return fileUploadMapper.selectById(id);
    }

    @Override
    public void downloadFiles(String id, HttpServletResponse response) {
        FileUploadEntity fileUploadEntity = findById(id);
        String filePateAll = fileUploadEntity.getUrl()+"/"+fileUploadEntity.getName();
        // ???????????????
        File file = new File(filePateAll);
        byte[] fileBytes = FileUtil.readBytes(file);
        DownloadUtil.download(fileUploadEntity.getOldName(), fileBytes, response);
    }

    @Override
    public String customUpload(MultipartFile file, ChunkEntity chunkEntity) throws Exception {
        String fileName = file.getOriginalFilename();
        // ?????????????????????
        if (ObjectUtils.isEmpty(fileName)) {
            throw new FileUploadException(FileUploadEnum.FILE_UPLOAD_ISEMPTY);
        }
        // ??????????????????
        String newName = chunkEntity.getIdentifier() + Constant.CHAE_ONE + chunkEntity.getChunkNumber();
        String savePath = "";
        // ???????????????
        if (CommonUtils.getOsName().indexOf(OsEnum.WINDOWS.getName()) != -1){
            savePath = customWindowsSavePath + chunkEntity.getIdentifier();
        }else {
            savePath = customLinuxSavePath + chunkEntity.getIdentifier();
        }
        File newFile = new File(savePath, newName);
        // ???????????????????????????????????????????????????
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        // ????????????
        file.transferTo(newFile);
        return "SUCCESS";
    }

    @Override
    public String merageFile(ChunkEntity chunkEntity) throws IOException {
        chunkEntity.setRelativePath(CommonUtils.removeUrl(chunkEntity.getRelativePath()));
        // ???????????????
        String fileName = chunkEntity.getFilename();
        // ???????????????
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        //?????????+?????????
        fileName = chunkEntity.getIdentifier()+suffixName;
        // ??????????????????????????????
        String customSavePath = "";
        // ????????????????????????
        String savePath = "";
        if (CommonUtils.getOsName().indexOf(OsEnum.WINDOWS.getName()) != -1){
            customSavePath = customWindowsSavePath + chunkEntity.getIdentifier() + CHAE_TWO;
            savePath = windowsSavePath + CHAE_TWO+UserUtil.getUserId() + chunkEntity.getRelativePath();
        }else {
            customSavePath = customLinuxSavePath + chunkEntity.getIdentifier() + CHAE_TWO;
            savePath = linuxSavePath + CHAE_TWO+UserUtil.getUserId() + chunkEntity.getRelativePath();
        }
        savePath = CommonUtils.removeUrl(savePath);
        File file = new File(savePath + CHAE_TWO + fileName);
        if (file.exists()){
            file.delete();
            System.out.println("???????????????????????????");
        }
        File filePath = new File(savePath);
        // ???????????????????????????????????????????????????
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // ????????????????????????
        BufferedOutputStream destOutputStream = new BufferedOutputStream(new FileOutputStream(savePath + CHAE_TWO + fileName));
        for (int i = 1; i <= chunkEntity.getTotalChunks() ; i++) {
            //????????????????????????????????????????????????
            byte[] fileBuffer = new byte[1024];//??????????????????
            int readBytesLength = 0; //?????????????????????
            File sourceFile = new File(customSavePath + chunkEntity.getIdentifier()+ Constant.CHAE_ONE + i);
            BufferedInputStream sourceInputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            while ((readBytesLength = sourceInputStream.read(fileBuffer))!=-1){
                destOutputStream.write(fileBuffer, 0 , readBytesLength);
            }
            sourceInputStream.close();
            System.out.println("???????????????????????????" + i + " / " + chunkEntity.getTotalChunks());
            //?????????????????????
            boolean delete = sourceFile.delete();
            if (delete){
                System.out.println(i+"????????????");
            }
        }
        File file1 = new File(customSavePath);
        file1.delete();
        destOutputStream.flush();
        destOutputStream.close();
        // ??????????????????
        FileUploadEntity fileUploadEntity = new FileUploadEntity();
        fileUploadEntity.setId(UuidUtils.uuid(true));
        fileUploadEntity.setOldName(chunkEntity.getFilename());
        fileUploadEntity.setName(fileName);
        fileUploadEntity.setUrl(savePath);
        fileUploadEntity.setSuffix(suffixName);
        fileUploadEntity.setRelativePath(chunkEntity.getRelativePath());
        fileUploadEntity.setMd5(chunkEntity.getIdentifier());
        fileUploadEntity.setIsFile(1);
        fileUploadEntity.setCreateTime(new Date());
        fileUploadEntity.setUserId(UserUtil.getUserId());
        fileUploadEntity.setParentId(chunkEntity.getParentId());
        fileUploadEntity.setIsDel(0);
        if (fileUploadMapper.insert(fileUploadEntity) == 0){
            throw new FileUploadException(FileUploadEnum.FILE_UPLOAD_ISERROR);
        }
        return chunkEntity.getFilename()+" ????????????";
    }

    @Override
    public PageResource<FileUploadVoEntity> queryFilePage(FileUploadQueryEntity resource) {
        resource.setUserId(UserUtil.getUserId());
        Page<FileUploadVoEntity> page = fileUploadMapper.queryFileListPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public List<FileUploadVoEntity> queryFile(FileUploadQueryEntity resource) {
        resource.setUserId(UserUtil.getUserId());
        return fileUploadMapper.queryFileList(resource);
    }

    @Override
    public FileUploadEntity addFilePath(FileUploadEntity resource){
        resource.setId(UuidUtils.uuid(true));
        resource.setUserId(UserUtil.getUserId());
        resource.setIsFile(0);
        resource.setOldName(resource.getName());
        resource.setRelativePath(CommonUtils.removeUrl(resource.getRelativePath()));
        resource.setUrl(CommonUtils.removeUrl(CommonUtils.getOsName().indexOf(OsEnum.WINDOWS.getName()) != -1 ? windowsSavePath + CHAE_TWO+UserUtil.getUserId() + resource.getRelativePath() : linuxSavePath + CHAE_TWO+UserUtil.getUserId() + resource.getRelativePath()));
        resource.setCreateTime(new Date());
        resource.setIsDel(0);
        fileUploadMapper.insert(resource);
        return resource;
    }

    @Override
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String body = scanner.hasNext() ? scanner.next() : "";

        JSONObject jsonObj = JSON.parseObject(body);

        if(Integer.valueOf(jsonObj.get("status").toString()) == 2)
        {
            String downloadUri = (String) jsonObj.get("url");

            URL url = new URL(downloadUri);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            FileUploadEntity fileUploadEntity = this.findById(jsonObj.get("id").toString());
            File savedFile = new File(fileUploadEntity.getUrl()+fileUploadEntity.getName());
            try (FileOutputStream out = new FileOutputStream(savedFile)) {
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                out.flush();
            }

            connection.disconnect();
        }
        System.out.println("??????");
    }

    @Override
    public void rename(FileUploadEntity resource) {
        fileUploadMapper.rename(resource);
    }

    @Override
    public int delFile(String id) {
        // ?????????????????????????????????????????????????????????????????????????????????????????????
        FileUploadEntity fileUploadEntity = fileUploadMapper.selectById(id);
        Integer sum = 0;
        if(ZREO.equals(fileUploadEntity.getIsFile())){
            // ?????????????????????????????????
            sum = fileUploadMapper.delFileBatch(id);
        }else {
            sum = fileUploadMapper.delFile(id);
        }

        return sum;
    }

    @Override
    public void filePreview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String id) throws IOException {
        FileUploadEntity fileUploadEntity = fileUploadMapper.selectById(id);
        String mime= MimeUtils.getMime(fileUploadEntity.getSuffix().replace(".",""));
        httpServletResponse.setHeader("Content-Type", mime);
//        String rangeString = httpServletRequest.getHeader("Range");//?????????video?????????????????????????????????null
//        if (StringUtils.isNotEmpty(rangeString)) {
//            long range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
//            httpServletResponse.setContentLength(Math.toIntExact(fileBean.getFileSize()));
//            httpServletResponse.setHeader("Content-Range", String.valueOf(range + (Math.toIntExact(fileBean.getFileSize()) - 1)));
//        }
        httpServletResponse.setHeader("Accept-Ranges", "bytes");

        String fileName = fileUploadEntity.getOldName();
        try {
            fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpServletResponse.addHeader("Content-Disposition", "fileName=" + fileName);// ???????????????
        OutputStream outputStream = null;
        outputStream = httpServletResponse.getOutputStream();
        try {
            String filePateAll = fileUploadEntity.getUrl()+"/"+fileUploadEntity.getName();
            // ???????????????
            File file = new File(filePateAll);
            long fileBytes = FileUtil.writeToStream(file,outputStream);

//            outputStream.write(fileBytes);

        }catch (Exception e){
            //org.apache.catalina.connector.ClientAbortException: java.io.IOException: ????????????????????????????????????????????????????????????
            e.printStackTrace();
            log.error("??????????????????????????????" + e);
        }finally {
            outputStream.close();
        }
    }
}
