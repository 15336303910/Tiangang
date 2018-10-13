package cn.plou.web.common.utils;


import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private String savePath;

    private String saveUrl;

    /**
     * 上传验证,并初始化文件目录
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String validateFields(HttpServletRequest request, String dirName, Boolean genDate) {

        savePath = request.getServletContext().getRealPath("/");

        String ymd = null;
        //savePath = request.getServletContext().getContextPath();
        //saveUrl = Constant.FILE_MAPPING_URL;
        // 获取内容类型
        String contentType = request.getContentType();

        File uploadDir = new File(savePath);
        if (contentType == null || !contentType.startsWith("multipart")) {
            return "请求不包含multipart/form-data流";
        } else if (!ServletFileUpload.isMultipartContent(request)) {
            return "请选择文件";
        } else if (!uploadDir.isDirectory()) {// 检查目录
            return "上传目录[" + savePath + "]不存在";
        } else if (!uploadDir.canWrite()) {
            return "上传目录[" + savePath + "]没有写权限";
        } else {
            // 创建文件夹
            savePath += dirName + "";
            saveUrl += dirName + "";
            File saveDirFile = new File(savePath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            if (genDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                ymd = sdf.format(new Date());
                //savePath += ymd + "/";
                savePath += ymd + "/";
                //saveUrl = "/"+ymd;
                //saveUrl = "/"+ymd + "/";
            }
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            return ymd;
        }
    }

    public String upload(HttpServletRequest request, String path, MultipartFile file) {
        String info = validateFields(request, path, true);
        /*if (info.length() > 0)
            throw new BusinessException(Constant.DEFAULT_ERROR_STATUS_CODE, info);*/
        try {
            //File temp = new File(request.getServletContext().getRealPath("")+new SimpleDateFormat("yyyyMMdd").format(new Date()), String.valueOf(System.currentTimeMillis()) + getExtensionName(file.getOriginalFilename()));
            //File temp = new File(savePath,String.valueOf(System.currentTimeMillis())+getExtensionName(file.getOriginalFilename()));
            //File temp = new File(getExtensionName(file.getOriginalFilename()));
            File temp = new File(request.getServletContext().getRealPath("")+"/"+info+"/"+file.getOriginalFilename());
            file.transferTo(temp);
            //return "/"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+temp.getName();
            //return temp.getName();
            return info+"/"+temp.getName();
        } catch (IOException e) {
            throw new BusinessException(Constant.DEFAULT_ERROR_STATUS_CODE, "上传文件失败");
        }
    }

    public String upload(HttpServletRequest request, String path, MultipartFile file, String fileName, Boolean genDate) {

        String info = validateFields(request, path, genDate);

        if (info.length() > 0)
            throw new BusinessException(Constant.DEFAULT_ERROR_STATUS_CODE, info);

        try {
            File temp = new File(savePath, fileName);
            file.transferTo(temp);
            //return saveUrl + temp.getName();
            return temp.getName();
        } catch (IOException e) {
            throw new BusinessException(Constant.DEFAULT_ERROR_STATUS_CODE, "上传文件失败");
        }
    }

    /**
     * 获取文件拓展名
     */
    private String getExtensionName(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot);//包含.
            }
        }
        return fileName;
    }

    private String getNameWithoutExtension(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    /**
     * 查询指定文件夹下的文件
     */
    public String searchFile(String path, String fileName,ServletRequest request) {
        //savePath = Constant.DEFAULT_UPLOAD_DIR;
        savePath = request.getServletContext().getRealPath("/upload");
        File dir = new File(savePath + path);
        String[] files = dir.list();
        if (files != null) {
            for (String file : files) {
                if (fileName.equals(getNameWithoutExtension(file)))
                    return file;
            }
        }
        return null;
    }
}
