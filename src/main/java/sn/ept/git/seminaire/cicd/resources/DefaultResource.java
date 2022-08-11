package sn.ept.git.seminaire.cicd.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DefaultResource {


    @GetMapping({"","/","/index"})
    public ResponseEntity<String> index() {
        return ResponseEntity
                .ok()
                .body("Welcome to my Todo app");
    }
}
