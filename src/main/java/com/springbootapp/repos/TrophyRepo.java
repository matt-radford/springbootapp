package com.springbootapp.repos;

import com.springbootapp.models.Trophy;
import org.springframework.data.repository.CrudRepository;

public interface TrophyRepo extends CrudRepository<Trophy, Integer> {
//   ! It's possible to write your own methods, with sql code, in here!
}
