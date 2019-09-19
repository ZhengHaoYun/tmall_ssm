package com.zhy.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zheng Haoyun
 * @date 2019-09-15 12:00
 */
public class UploadedImageFile{
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
