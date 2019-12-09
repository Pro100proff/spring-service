package ru.tinkoff.task.contact;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {

  private ContactServiceImpl subj;

  @Mock
  private ContactRepository repository;


  @Before
  public void setUp(){
    subj = new ContactServiceImpl();
    subj.setRepository(repository);
  }

  @Test
  public void getLastOrderByClientShouldReturnOrder() {
    int contactId = 1;
    int applicationId = 2;
    LocalDateTime dtCreated = LocalDateTime.now();
    String productName = "1";
    ApplicationDto expectedApplication = ApplicationDto.builder()
        .contactId(contactId)
        .applicationId(applicationId)
        .dtCreated(Timestamp.valueOf(dtCreated))
        .productName(productName)
        .build();
    when(repository.get(contactId)).thenReturn(generateContact(contactId, applicationId, productName, dtCreated));
    Assert.assertEquals(expectedApplication, subj.getLastApplication(contactId));
    verify(repository).get(contactId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getLastOrderByClientShouldThrowExceptionForIncorrectId(){
    subj.getLastApplication(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getLastOrderByClientShouldThrowExceptionForNonExistOrder(){
    when(repository.get(anyInt())).thenReturn(null);
    subj.getLastApplication(1);
  }

  private Contact generateContact(int contactId, int applicationId, String productName, LocalDateTime dtCreated){
    LocalDateTime firstDateTime = dtCreated.minusDays(1);
    Application firstApplication = new Application(applicationId - 1, Timestamp.valueOf(firstDateTime), productName);
    Application latestApplication = new Application(applicationId, Timestamp.valueOf(dtCreated), productName);
    return new Contact(1, Arrays.asList(firstApplication, latestApplication));
  }
}