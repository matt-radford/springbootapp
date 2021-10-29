package com.springbootapp.controllers;

import com.springbootapp.models.Comment;
import com.springbootapp.models.Trophy;
import com.springbootapp.services.CommentService;
import com.springbootapp.services.TrophyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TrophyController {

    @Autowired
    private TrophyService trophyService;


    @GetMapping("/trophies")
    public Iterable<Trophy> getTrophies() {
        return trophyService.findAllTrophies();
    }

    @GetMapping("/trophies/{id}")
    public Optional<Trophy> getTrophy(@PathVariable Integer id) {
        return trophyService.findTrophy(id);
    }

    @DeleteMapping("/trophies/{id}")
    public void deleteTrophy(@PathVariable Integer id) {
        trophyService.deleteTrophy(id);
    }

    @PutMapping("/trophies/{id}")
    public Trophy putClub(@PathVariable Integer id, @RequestBody Trophy trophy) {
        return trophyService.updateTrophy(id, trophy);
    }

    @PostMapping("/trophies")
    // ! @RequestBody annotation gives us access to the body of the request (the thing we're posting)
    public Trophy postTrophy(@RequestBody Trophy trophy) {
        return trophyService.createTrophy(trophy);
    }

}