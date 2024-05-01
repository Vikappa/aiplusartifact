package com.aiplus.aiplus;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiplusApplication.class, args);
	}

}
