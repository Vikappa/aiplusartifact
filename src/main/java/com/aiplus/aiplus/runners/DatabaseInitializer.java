package com.aiplus.aiplus.runners;

import com.aiplus.aiplus.entities.stockentities.BrandTonica;
import com.aiplus.aiplus.entities.stockentities.ColoreGuarnizione;
import com.aiplus.aiplus.entities.stockentities.Flavour;
import com.aiplus.aiplus.entities.stockentities.GinFlavour;
import com.aiplus.aiplus.entities.users.User;
import com.aiplus.aiplus.entities.users.USER_ROLE;
import com.aiplus.aiplus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private GinFlavourDAO ginFlavourDAO;

    @Autowired
    private FlavourDAO flavourDAO;

    @Autowired
    private ColoreGuarnizioneDAO coloreGuarnizioneDAO;

    @Autowired
    private BrandTonicaDAO brandTonicaDAO;

    public DatabaseInitializer(UserDAO userDao, GinFlavourDAO ginFlavourDAO) {
        this.userDao = userDao;
        this.ginFlavourDAO = ginFlavourDAO;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        System.out.println("Applicazione avviata completamente. Inizializzazione del database in corso...");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Create admin user if none exists
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

        // Populate FLAVOURS if the table is empty
        if (ginFlavourDAO.count() == 0) {
            for (GINFLAVOUR flavourEnum : GINFLAVOUR.values()) {
                GinFlavour flavour = new GinFlavour();
                flavour.setName(flavourEnum.name());
                ginFlavourDAO.save(flavour);
            }
            System.out.println("Sapori Gin initializzati");
        }

        if(flavourDAO.count()==0){
            for (FLAVOURS fflavourENUM : FLAVOURS.values()){
                Flavour fflavour = new Flavour();
                fflavour.setName(fflavourENUM.name());
                flavourDAO.save(fflavour);
            }
            System.out.println("Sapori Alimenti inizializzati");
        }

        if(coloreGuarnizioneDAO.count()==0){
            for(COLORS_GUARNIZIONI garnishColor : COLORS_GUARNIZIONI.values()){
                ColoreGuarnizione color = new ColoreGuarnizione();
                color.setName(garnishColor.name());
                coloreGuarnizioneDAO.save(color);
            }
        }

        if(brandTonicaDAO.count() == 0) {
            for (BASE_GIN_BRANDS brand : BASE_GIN_BRANDS.values()) {
                BrandTonica brandTonica = new BrandTonica();
                brandTonica.setName(brand.name());
                brandTonica.setDescription(brand.name());
                brandTonicaDAO.save(brandTonica);
            }
        }
    }
}


enum GINFLAVOUR {
    SECCO, SPEZIATO, FRUTTATO, FLOREALE
}
enum FLAVOURS {
    DOLCE, SALATO, SPEZIATO, FRUTTATO, FLOREALE, AMARO, SECCO;
}

enum COLORS_GUARNIZIONI {
    ROSSO, ROSA, GIALLO, BLU, VERDE, GIALLO_SCURO, TRASPARENTE;
}

enum BASE_GIN_BRANDS {
    SCHWEPPES, FEVER_TREE, CANADA_DRY, SPECTACULAR_Q;
}
