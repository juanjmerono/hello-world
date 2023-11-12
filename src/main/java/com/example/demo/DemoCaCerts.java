package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCaCerts {
    

    private KeyStore loadKeyStore() throws Exception {
        String relativeCacertsPath = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + relativeCacertsPath;
        FileInputStream is = new FileInputStream(filename);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        String password = "changeit";
        keystore.load(is, password.toCharArray());

        return keystore;
    }    

    @GetMapping("/cacerts")
    public List<String> cacerts() {
        try {
            return Collections.list(loadKeyStore().aliases());
        } catch (Exception ex) {
            return Arrays.asList(new String[]{ex.getMessage()});
        }
    }

}
