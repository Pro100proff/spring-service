package ru.tinkoff.task.contact;

import java.util.Comparator;
import java.util.List;

public class Contact {
  private Integer contactId;
  private List<Application> applications;

  public Contact(Integer contactId, List<Application> applications) {
    this.contactId = contactId;
    this.applications = applications;
  }

  public Application getLastApplication(){
    return applications.stream()
        .max(Comparator.comparing(Application::getDtCreated))
        .orElseThrow(() -> new IllegalStateException("Не найдена ни одна заявка!"));
  }
}
