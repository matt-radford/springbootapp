package com.springbootapp.repos;

import com.springbootapp.models.Model;
import org.springframework.data.repository.CrudRepository;

// ! Our interface extends CrudRepository, which is a interface provided by
// ! org.springframework.data.repository package. This will give our interface
// ! all the methods and final properties that CrudRepository has.
// ! Station is the type of model, and Integer is the ID type.
public interface UserRepo extends CrudRepository<Model, Integer> {}

