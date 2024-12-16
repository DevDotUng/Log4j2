package com.log4j2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/log4j2")
public class Log4j2Controller {

    private static final String LOG_FILE_PATH = "logs/spring.log";

    @GetMapping("/fail")
    public void log4j2() {
        int n = 1/0;
    }

    @GetMapping("/success")
    public String log4j2Success() {
        return "OK";
    }

    @GetMapping("/api/logs")
    public ResponseEntity<?> getLogs() {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(LOG_FILE_PATH));
            StringBuilder sb = new StringBuilder();
            for (String line: allLines) {
                sb.append(line);
            }
            sb.insert(0, "[ ");
            sb.append(" ]");
            String logs = sb.toString().replace("}{", "}, {");

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(logs);
            JSONArray jsonArray = (JSONArray) obj;

            return ResponseEntity.ok(jsonArray);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to read log file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
