package com.village.villageupload.file.controller;

import cn.hutool.core.util.ObjectUtil;
import com.village.villageupload.cache.UserCache;
import com.village.villageupload.common.UserUtil;
import com.village.villageupload.constant.Constant;
import com.village.villageupload.file.entity.ChunkEntity;
import com.village.villageupload.file.entity.FileUploadEntity;
import com.village.villageupload.file.entity.FileUploadQueryEntity;
import com.village.villageupload.file.service.FileUploadService;
import com.village.villageupload.login.entity.UserEntity;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import com.village.villageupload.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserCache userCache;
    /**
     * 上传文件
     * @param file
     * @return
     * @author Liuxx
     */
    @PostMapping("/upload")
    public BaseResponsePojo<Object> uploadFiles(ChunkEntity chunkEntity, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return BaseResponse.newErrorResponse(400,"文件不能为空!",null);
        }
        if (ObjectUtil.isNotEmpty(chunkEntity.getRelativePath()) && Constant.INDEX_OF_INEXISTENCE != Constant.ROLE_OUT_PATH.indexOf(chunkEntity.getRelativePath())) {
            return BaseResponse.newErrorResponse(400,"路径名称不合法!",null);
        }
        return BaseResponse.newSuccessResponse(fileUploadService.uploadFiles(file,chunkEntity));
    }

    /**
     * 上传文件
     * @param file
     * @return
     * @author Liuxx
     */
    @PostMapping("/customUpload")
    public BaseResponsePojo<Object> customUploadFiles(MultipartFile file, ChunkEntity chunkEntity) throws Exception {
        if (file.isEmpty()) {
            return BaseResponse.newErrorResponse(400,"文件不能为空!",null);
        }
        if (ObjectUtil.isNotEmpty(chunkEntity.getRelativePath()) && Constant.INDEX_OF_INEXISTENCE != chunkEntity.getRelativePath().indexOf(Constant.ROLE_OUT_PATH)) {
            return BaseResponse.newErrorResponse(400,"路径名称不合法!",null);
        }
        if(Constant.ONE.equals(chunkEntity.getTotalChunks())){
            fileUploadService.uploadFiles(file,chunkEntity);
        }else {
            fileUploadService.customUpload(file,chunkEntity);
        }
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 合并文件
     * @param chunkEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/merageFile")
    public BaseResponsePojo<Object> merageFile(@RequestBody ChunkEntity chunkEntity) throws IOException {
        return BaseResponse.newSuccessResponse(fileUploadService.merageFile(chunkEntity));
    }

    /**
     * 根据id查询文件信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public BaseResponsePojo<Object> findById(String id){
        return BaseResponse.newSuccessResponse(fileUploadService.findById(id));
    }

    /**
     * 下载文件
     * @param id
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam String id, HttpServletResponse response){
        fileUploadService.downloadFiles(id, response);
    }

    /**
     * 添加文件夹
     * @param resourse
     * @return
     */
    @PostMapping("addFilePath")
    public BaseResponsePojo<Object> addFilePath(@RequestBody FileUploadEntity resourse){
        return BaseResponse.newSuccessResponse(fileUploadService.addFilePath(resourse));
    }

    /**
     * 查询个人上传的文件
     * @param resource
     * @return
     */
    @GetMapping("/queryFile")
    public BaseResponsePojo<Object> queryFile(FileUploadQueryEntity resource){
        return BaseResponse.newSuccessResponse(fileUploadService.queryFile(resource));
    }

    /**
     * 文件保存回调
     * @param
     * @return
     */
    @PostMapping("/callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileUploadService.callBack(request, response);
    }

    @PostMapping("/rename")
    public BaseResponsePojo<Object> rename(@RequestBody FileUploadEntity resource){
        fileUploadService.rename(resource);
        return BaseResponse.newSuccessResponse();
    }

    @GetMapping("/delFile")
    public BaseResponsePojo<Object> delFile(@RequestParam("id") String id){
        fileUploadService.delFile(id);
        return BaseResponse.newSuccessResponse();
    }

    @GetMapping("/filePreview")
    public void filePreview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String id) throws IOException {
        fileUploadService.filePreview(httpServletRequest, httpServletResponse, id);
    }

    @GetMapping("/downExcel")
    public void down(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        Map map = new LinkedHashMap();
        map.put("序号",1);
        map.put("表头","skdjlajljl");
        list.add(map);
        DownloadUtil.downloadExcel(list,response);
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ChunkEntity chunkEntity = new ChunkEntity();
        aaa(chunkEntity);
        System.out.println(chunkEntity.toString());
    }

    public static <T> void aaa(T cl) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> c = cl.getClass();
        Method method = c.getMethod("setCreateUser",String.class);
        method.invoke(cl,"111");
    }

}
