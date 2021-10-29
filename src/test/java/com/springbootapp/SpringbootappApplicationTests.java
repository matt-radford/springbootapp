package com.springbootapp;


import com.springbootapp.models.Model;
import com.springbootapp.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SpringbootApplicationTests {



	@Test
	void contextLoads() {
	}


//	@Disabled
//  @Test
//	public void getClubs_ReturnClubs_Success() throws Exception {
//		Model club = new Model();
//		club.setName("Liverpool");
//		club.setDateFounded(LocalDate.of(1892,06,03));
//		club.setTitlesCount(19);
//
//		RequestBuilder  request = MockMvcRequestBuilders
//				.post("/clubs")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(club));
//
//		ResultActions resultActions = mockMvc.perform(request);
//
//		resultActions
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value(1));
//
//		List<Model> clubs = (List<Model>) userRepo.findAll();
//		assertEquals(1,clubs.size());
//  }
}

