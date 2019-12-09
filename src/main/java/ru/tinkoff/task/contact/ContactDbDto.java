package ru.tinkoff.task.contact;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
class ContactDbDto {
  private Integer contactId;
  private Integer applicationId;
  private Timestamp dtCreated;
  private String productName;
}
