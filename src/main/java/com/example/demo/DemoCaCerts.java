package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCaCerts {
    
    @GetMapping("/testcrt")
    public String testCrt(@RequestParam(name="host",defaultValue = "fake.ssl") String host) throws Exception {
        URL url = new URL("https://"+host);
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        String content = "";
        if (con!=null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {                
                String input = "";
                while ((input = br.readLine()) != null){
                    content += input;
                }
            } catch (IOException e) {
                content = e.getMessage();
                e.printStackTrace();
            }   
        }
        return content;
    }

}
