package com.village.villageupload.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.file.entity.ChunkEntity;
import com.village.villageupload.file.entity.FileUploadEntity;
import com.village.villageupload.file.entity.FileUploadQueryEntity;
import com.village.villageupload.file.entity.FileUploadVoEntity;
import com.village.villageupload.utils.PageResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileUploadService extends IService<FileUploadEntity> {

    FileUploadEntity uploadFiles(MultipartFile file, ChunkEntity chunkEntity) throws Exception;

    FileUploadEntity findById(String id);

    void downloadFiles(String id, HttpServletResponse response);

    String customUpload(MultipartFile file, ChunkEntity chunkEntity) throws Exception;

    String merageFile(ChunkEntity chunkEntity) throws IOException;

    PageResource<FileUploadVoEntity> queryFilePage(FileUploadQueryEntity resource);

    List<FileUploadVoEntity> queryFile(FileUploadQueryEntity resource);

    FileUploadEntity addFilePath(FileUploadEntity resource);

    void filePreview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String id) throws IOException;

    void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void rename(FileUploadEntity resource);

    int delFile(String id);
}
