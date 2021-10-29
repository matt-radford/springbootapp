package com.springbootapp.services;

import com.springbootapp.models.Trophy;
import com.springbootapp.repos.TrophyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// ! Tells spring that this class is a service!
// ! This is where the 'business logic' is going to live.
// ! A service also is the class that's aware of the repository.
@Service
public class TrophyService {

    // ! Autowired annotation means spring will 'inject' a stationRepo
    // ! into our station service when we create it.
    @Autowired
    private TrophyRepo trophyRepo;

    public Iterable<Trophy> findAllTrophies() {
        return trophyRepo.findAll();
    }

    public  Trophy createTrophy(Trophy trophy) {
        return trophyRepo.save(trophy);
    }

    public Optional<Trophy> findTrophy(Integer id) {
        Optional<Trophy> optTrophy = trophyRepo.findById(id);
        if (optTrophy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trophy found");
        }
        return optTrophy;
    }

    public void deleteTrophy(Integer id) {
        Optional<Trophy> optTrophy = trophyRepo.findById(id);
        if (optTrophy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trophy found");
        }
        trophyRepo.deleteById(id);
    }

    public Trophy updateTrophy(Integer id, Trophy trophy) {
        Optional<Trophy> optTrophy = trophyRepo.findById(id);
        if (optTrophy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trophy found");
        }
        Trophy trophyToUpdate = optTrophy.get();
        trophyToUpdate.setName(trophy.getName());
        trophyToUpdate.setCountry(trophy.getCountry());
        return trophyRepo.save(trophyToUpdate);
    }
}