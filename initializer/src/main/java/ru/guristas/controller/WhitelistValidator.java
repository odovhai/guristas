package ru.guristas.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
public class WhitelistValidator {

    private final static Logger ANALYS_LOGGER = LoggerFactory.getLogger("analytics");

    @Value("${users.filename}")
    private String fileName;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "ping")
    public String ping() {
        return "pong";
    }


    @RequestMapping(method = RequestMethod.GET, path = "validateUser/{id}")
    public ResponseEntity<Boolean> validateUser(@PathVariable String id) {
        log.info("Validating for id={}", id);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.header("Access-Control-Allow-Origin", "*");
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            boolean result = stream.anyMatch(s -> s.equals(id));
            log.info("Validation status for id=[{}] is [{}]", id, result);
            ANALYS_LOGGER.info("[{}]:[{}]", id, result);
            return builder.body(result);
        } catch (IOException e) {
            log.error(String.format("Error during validation for id=[%s]", id), e);
            return builder.body(null);
        }
    }
}
