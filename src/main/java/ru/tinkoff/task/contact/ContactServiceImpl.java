package ru.tinkoff.task.contact;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

@Service
public class ContactServiceImpl implements ContactService {

  private ContactRepository repository;

  @Inject
  public void setRepository(ContactRepository repository) {
    this.repository = repository;
  }

  @Override
  public ApplicationDto getLastApplication(Integer contactId) {
    if(Objects.isNull(contactId) || contactId <= 0){
      throw new IllegalArgumentException("Некорректный формат для идентификатора :" + contactId);
    }
    Contact contact = repository.get(contactId);
    if(Objects.isNull(contact)){
      throw new IllegalArgumentException("Не найден контакт с ид :" + contactId);
    }
    Application lastApplication = contact.getLastApplication();
    return ApplicationDto.builder()
        .contactId(contactId)
        .applicationId(lastApplication.getApplicationId())
        .productName(lastApplication.getProductName())
        .dtCreated(lastApplication.getDtCreated())
        .build();
  }
}
