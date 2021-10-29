package com.springbootapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import com.springbootapp.models.Model;
import com.springbootapp.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
// Tells spring boot which profile to use.
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
// Needed to get everything configured correctly.
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// This will tell hibernate to remove your database between EACH test.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test") // This means our test is using the Test profile.

public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        Model newClub = new Model();
        newClub.setName("Liverpool");
        newClub.setTitlesCount(19);
        newClub.setDateFounded(LocalDate.of(1892, 06, 03));

        Model newClub2 = new Model();
        newClub2.setName("Arsenal");
        newClub2.setTitlesCount(13);
        newClub2.setDateFounded(LocalDate.of(1886, 01, 01));

        userRepo.saveAll(List.of(newClub, newClub2));
    }

    @Test
    public void ReturnHelloWorld() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get("/hello");

        ResultActions resultActions = mockMvc.perform(request);

        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();

        String actual = result.getResponse().getContentAsString();
        String expected = "Hello World!";

        assertEquals(expected, actual);

    }

    @Test
    public void getClubs_ReturnsClubs_Success() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/clubs");

        ResultActions resultActions = mockMvc.perform(request);

        MvcResult result = resultActions
                .andExpect(status().isOk())
                // ! jsonPath lets you query json data.
                // ! The $ symbol starts from the top of our json response.
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Liverpool"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void postClub_ReturnsClub_Success() throws Exception {
        // given
        Model club = new Model();
        club.setName("Chelsea");
        club.setDateFounded(LocalDate.parse("1905-03-10"));
        club.setTitlesCount(6);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/clubs")
                // Say its json data:
                .contentType(MediaType.APPLICATION_JSON)
                // Turn my station into a json string:
                .content(objectMapper.writeValueAsString(club));

        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));

        // We could also check the repo too..
        List<Model> clubs = (List<Model>) userRepo.findAll();
        assertEquals(3, clubs.size());
    }

    @Test
    public void getClub_ReturnsException_NotFound() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .get("/clubs/5");

        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        resultActions
                .andExpect(status().isNotFound());

        // ? We will use this when it comes to unit testing.
        // assertThrows(
        //   // ! then
        //   // First argument: exception class
        //   ResponseStatusException.class,
        //   // ! when
        //   // Second argument: lambda to run some code
        //     () -> {
        //
        //     }
        // );
    }

    @Test
    public void deleteStation_ReturnsNoContent_Success() throws Exception {
        // given
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/stations/1");

        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    public void putClub_ReturnsClub_Success() throws Exception {

        // given
        Model club = new Model();
        club.setName("Renamed Club");
        club.setDateFounded(LocalDate.parse("1901-11-17"));
        club.setTitlesCount(1);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/clubs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(club));

        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Renamed Station"))
                .andExpect(jsonPath("$.dateFounded").value("1901-11-17"))
                .andExpect(jsonPath("$.titlesCount").value(1));
    }

    @Test
    public void putStation_ReturnsException_NotFound() throws Exception {
        // given
        Model club = new Model();
        club.setName("Renamed Club");
        club.setDateFounded(LocalDate.parse("1901-11-17"));
        club.setTitlesCount(3);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/clubs/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(club));

        // when
        ResultActions resultActions = mockMvc.perform(request);

        // then
        MvcResult result = resultActions
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
