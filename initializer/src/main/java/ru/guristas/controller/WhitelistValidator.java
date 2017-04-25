package ru.guristas.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Controller
@RequestMapping("/initializer/")
@ResponseBody
@CrossOrigin(origins = {"*"}, maxAge = 3600)
public class WhitelistValidator {

    @Value("${users.filename}")
    private String fileName;


    @RequestMapping(method = RequestMethod.GET, path = "ping")
    public String ping() {
        return "pong";
    }


    @RequestMapping(method = RequestMethod.GET, path = "validateUser/{id}")
    public ResponseEntity<Boolean> validateUser(@PathVariable String id) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.header("Access-Control-Allow-Origin", "*");
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return builder.body(stream.anyMatch(s -> s.equals(id)));
        } catch (IOException e) {
            return builder.body(null);
        }
    }
}
