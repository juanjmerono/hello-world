package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRest {
    
    @GetMapping("/hora")
    public ResponseEntity<Map<String,String>> root() {

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        Map<String,String> result = new HashMap<>();
        result.put("hour", String.format("%s %s",dtf1.format(localDate),dtf2.format(localTime)));
        return new ResponseEntity<>(result,HttpStatus.OK);         
    }

    @GetMapping("/memory-status")
    public Map<String,Long> getMemoryStatistics() {
        Map<String,Long> stats = new HashMap<>();
        stats.put("HeapSize",Runtime.getRuntime().totalMemory());
        stats.put("HeapMaxSize",Runtime.getRuntime().maxMemory());
        stats.put("HeapFreeSize",Runtime.getRuntime().freeMemory());
        return stats;
    }

}
