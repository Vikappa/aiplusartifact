package com.aiplus.aiplus.runners;

import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.repositories.UserDAO;
import com.aiplus.aiplus.entities.users.USER_ROLE;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserDAO userDao;

    public DatabaseInitializer(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userDao.count() == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@admin.it");
            adminUser.setName("ADMIN");
            adminUser.setSurname("ADMIN");
            adminUser.setRole(USER_ROLE.ADMIN);
            adminUser.setPassword(passwordEncoder.encode("password123"));

            userDao.save(adminUser);

            System.out.println("Utente amministratore creato: admin@admin.it / password123");
        }
    }
}
