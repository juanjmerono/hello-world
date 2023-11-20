package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<CertificateDTO> cacerts() {
        try {
            KeyStore ks = loadKeyStore();
            return Collections.list(ks.aliases()).stream().map(c -> {
                X509Certificate crt = gCertificate(ks,c);
                return new CertificateDTO(crt.getSubjectDN().getName(), crt.getIssuerDN().getName());
            }).filter(c -> c.getSubjectDN().equals("CN=superuser.com")).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Arrays.asList();
        }
    }

    private X509Certificate gCertificate(KeyStore ks,String c) {
        try {
            return (X509Certificate)ks.getCertificate(c);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }


    class CertificateDTO {
        String subjectDN;
        String issuerDN;

        CertificateDTO(String subjectDN, String issuerDN) {
            this.subjectDN = subjectDN; this.issuerDN = issuerDN;
        }

        public String getSubjectDN() { return subjectDN; }
        public String getIssuerDN() { return issuerDN; }

    }

}
