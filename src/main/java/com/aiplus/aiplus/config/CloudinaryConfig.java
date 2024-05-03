package com.aiplus.aiplus.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public class CloudinaryConfig {
    @Getter
    private static Cloudinary cloudinary;

    @Value("cloudinary.name")
    private static String cloudName;
    @Value("cloudinary.key")
    private static String apikey;

    @Value("cloudinary.secret")
    private static String apiSecret;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apikey,
                "api_secret", apiSecret));
    }

}
