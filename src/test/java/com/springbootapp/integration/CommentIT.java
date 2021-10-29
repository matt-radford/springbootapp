package com.springbootapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapp.models.Comment;
import com.springbootapp.models.Model;
import com.springbootapp.repos.CommentRepo;
import com.springbootapp.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource (
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CommentIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private UserRepo userRepo;

  private Model savedClub;

  @BeforeEach
  public void setup() {
    Model newClub = new Model();
    newClub.setName("Liverpool");
    newClub.setTitlesCount(19);
    newClub.setDateFounded(LocalDate.of(1892, 06, 03));

    savedClub = userRepo.save(newClub);

    Comment newComment = new Comment();
    newComment.setId(1);
    newComment.setBody("TDD is great!");
    newComment.setClub(savedClub);

    commentRepo.save(newComment);
  }

  @Test
  public void postComment_ReturnsComment_Success() throws Exception {
    // given
    String expected = "TDD is great!";
    Comment comment = new Comment();
    comment.setBody(expected);
    comment.setClub(savedClub);

    RequestBuilder request = MockMvcRequestBuilders
            .post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment));

    // when
    ResultActions resultActions = mockMvc.perform(request);

    // then
    resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.body").value(expected));
  }

  @Test
  public void postComment_ReturnsException_UnprocessableEntity() throws Exception {
    Comment comment = new Comment();
    RequestBuilder request = MockMvcRequestBuilders
            .post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment));

    ResultActions resultActions = mockMvc.perform(request);

    resultActions
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void getComments_ReturnsComments_Success() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders.get("/comments");

    ResultActions resultActions = mockMvc.perform(request);

    MvcResult result = resultActions
            .andExpect(status().isOk())
            // ! jsonPath lets you query json data.
            // ! The $ symbol starts from the top of our json response.
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].body").value("TDD is great!"))
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }
}