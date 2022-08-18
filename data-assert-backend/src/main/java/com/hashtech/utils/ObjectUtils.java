package com.hashtech.utils;

import com.hashtech.common.ResourceCodeClass;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

public class ObjectUtils {

    /**
     * 判断文件流是否为图片
     * @author  maoc
     * @create  2022/8/18 11:40
     * @desc
     **/
    public static boolean isImg(MultipartFile file){

        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(null==read){
                return false;
            }
            int width = read.getWidth();
            int height = read.getHeight();
            return width>0&&height>0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否为文档
     * @author  maoc
     * @create  2022/8/18 11:41
     * @desc
     **/
    public static boolean isExcel(MultipartFile file){
        BufferedInputStream bis = null;
        try{
            bis = new BufferedInputStream(file.getInputStream());
            if(bis.available()==0){
                return false;
            }
            //OLE2:xls//OOXML:xlsx
            FileMagic fileMagic = FileMagic.valueOf(bis);
            return (FileMagic.OLE2 == fileMagic || FileMagic.OOXML== fileMagic);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(null!=bis){
                try {bis.close();} catch (IOException e) {}
            }
        }
        //throw new AppException(rc10000000.getCode(), rc10000000.getMessage()+"文件类型错误，仅支持xls和xlsx");
    }
}
