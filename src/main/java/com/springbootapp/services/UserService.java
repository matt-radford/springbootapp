package com.springbootapp.services;

import com.springbootapp.models.Model;
import com.springbootapp.repos.UserRepo;
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
public class UserService {

    // ! Autowired annotation means spring will 'inject' a stationRepo
    // ! into our station service when we create it.
    @Autowired
    private UserRepo userRepo;

    public Iterable<Model> findAllClubs() {
        return userRepo.findAll();
    }

    public  Model createClub(Model club) {
        return userRepo.save(club);
    }

    public Optional<Model> findClub(Integer id) {
        Optional<Model> optModel = userRepo.findById(id);
        if (optModel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No club found");
        }
        return optModel;
    }

    public void deleteClub(Integer id) {
        Optional<Model> optModel = userRepo.findById(id);
        if (optModel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No club found");
        }
        userRepo.deleteById(id);
    }

    public Model updateClub(Integer id, Model club) {
        Optional<Model> optModel = userRepo.findById(id);
        if (optModel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No club found");
        }
        Model clubToUpdate = optModel.get();
        clubToUpdate.setName(club.getName());
        clubToUpdate.setTitlesCount(club.getTitlesCount());
        clubToUpdate.setDateFounded(club.getDateFounded());
        return userRepo.save(clubToUpdate);
    }
}