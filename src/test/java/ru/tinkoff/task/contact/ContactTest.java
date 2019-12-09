package ru.tinkoff.task.contact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ContactTest {

  private Contact subj;
  private Application latestApplication;

  @Before
  public void setUp() {
    LocalDateTime firstDateTime = LocalDateTime.now().minusDays(1);
    LocalDateTime latestDateTime = LocalDateTime.now();
    Application application1 = new Application(1, Timestamp.valueOf(firstDateTime), "1");
    latestApplication = new Application(2, Timestamp.valueOf(latestDateTime), "2");
    subj = new Contact(1, Arrays.asList(application1, latestApplication));
  }

  @Test
  public void getLastApplicationShouldReturnLatestApplication() {
    assertEquals(latestApplication, subj.getLastApplication());
  }
}