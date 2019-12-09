package ru.tinkoff.task.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.task.AppStarter;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = AppStarter.class)
@AutoConfigureMockMvc
public class ContactControllerTest {

  @Inject
  private MockMvc mvc;

  @Inject
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Test
  public void getLastApplicationByContactShouldReturnApplication() throws Exception {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    int contactId = 2;
    int applicationId = 10;
    LocalDateTime dtCreated = LocalDateTime.now();
    String productName = "productName";
    saveContactWithApplication(contactId, applicationId, dtCreated, productName);
    mvc.perform(get("/contact/"+ contactId+"/last_application")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("contactId", is(contactId)))
        .andExpect(jsonPath("applicationId", is(applicationId)))
        .andExpect(jsonPath("dtCreated", is(dtCreated.toString())))
        .andExpect(jsonPath("productName", is(productName)));
  }

  private void saveContactWithApplication(int contactId, int applicationId, LocalDateTime dtCreated, String productName) {

    jdbcTemplate.update("insert into contact\n" +
        "values (:contactId);", new MapSqlParameterSource("contactId", contactId));
    for (int id = applicationId; id <= applicationId + 5; id++) {
      jdbcTemplate.update(
          "insert into application\n" +
              "values (:applicationId, :contactId, :dtCreated, :productName);",
          new MapSqlParameterSource()
              .addValue("contactId", contactId)
              .addValue("applicationId", id)
              .addValue("productName", productName)
              .addValue("dtCreated", dtCreated));
      dtCreated = dtCreated.minusDays(1);
    }
  }
}