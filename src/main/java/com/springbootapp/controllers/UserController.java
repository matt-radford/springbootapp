package com.springbootapp.controllers;

import com.springbootapp.services.UserService;
import com.springbootapp.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;


@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/clubs")
    public Iterable<Model> getClubs() {
        return userService.findAllClubs();
    }

    @GetMapping("/clubs/{id}")
    public Optional<Model> getClub(@PathVariable Integer id) {
        return userService.findClub(id);
    }

    @DeleteMapping("/clubs/{id}")
    public void deleteClub(@PathVariable Integer id) {
        userService.deleteClub(id);
    }

    @PutMapping("/clubs/{id}")
    public Model putClub(@PathVariable Integer id, @RequestBody Model club) {
        return userService.updateClub(id, club);
    }

    @PostMapping("/clubs")
    // ! @RequestBody annotation gives us access to the body of the request (the thing we're posting)
    public Model postClub(@RequestBody Model club) {
        return userService.createClub(club);
    }

}