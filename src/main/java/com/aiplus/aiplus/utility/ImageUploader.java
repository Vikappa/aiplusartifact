package com.aiplus.aiplus.utility;

import com.aiplus.aiplus.config.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

public class ImageUploader {

    public String uploadImage(String filePath) {
        try {
            Cloudinary cloudinary = CloudinaryConfig.getCloudinary();
            File toUpload = new File(filePath);
            Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
