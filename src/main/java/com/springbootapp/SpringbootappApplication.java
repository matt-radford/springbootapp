package com.springbootapp;

import com.springbootapp.models.Model;
import com.springbootapp.repos.UserRepo;
import com.springbootapp.models.Trophy;
import com.springbootapp.repos.TrophyRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringbootappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootappApplication.class, args);
	}
	@Bean
	@Profile("!test")
	CommandLineRunner commandLineRunner(UserRepo userRepo, TrophyRepo trophyRepo) {
		return args -> {
			Model newClub = new Model();
			newClub.setName("Arsenal");
			newClub.setTitlesCount(13);
			newClub.setDateFounded(LocalDate.of(1886,01,01));

			Model newClub2 = new Model();
			newClub2.setName("Manchester United");
			newClub2.setTitlesCount(20);
			newClub2.setDateFounded(LocalDate.of(1878,01,01));

			Model newClub3 = new Model();
			newClub3.setName("Liverpool");
			newClub3.setTitlesCount(19);
			newClub3.setDateFounded(LocalDate.of(1892, 06, 03));

			userRepo.saveAll(List.of(newClub, newClub2, newClub3));

			Trophy newTrophy = new Trophy();
			newTrophy.setName("Premier League/First Division");
			newTrophy.setCountry("England");

			Trophy newTrophy2 = new Trophy();
			newTrophy2.setName("FA Cup");
			newTrophy2.setCountry("England");

			trophyRepo.saveAll(List.of(newTrophy, newTrophy2));
		};
	}
}
