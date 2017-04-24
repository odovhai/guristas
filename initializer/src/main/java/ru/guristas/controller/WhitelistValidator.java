package ru.guristas.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Controller
@RequestMapping("/initializer/")
@ResponseBody
public class WhitelistValidator {

    @Value("${users.filename}")
    private String fileName;


    @RequestMapping(method = RequestMethod.GET, path = "ping")
    public String ping() {
        return "pong";
    }


    @RequestMapping(method = RequestMethod.GET, path = "validateUser/{id}")
    public boolean validateUser(@PathVariable String id) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.anyMatch(s -> s.equals(id));
        } catch (IOException e) {
           return false;
        }
    }
}
