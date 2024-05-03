package com.aiplus.aiplus.utility;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class ImageUploader {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageUploader(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(String filePath) {
        try {
            File toUpload = new File(filePath);
            Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
