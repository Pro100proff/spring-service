package ru.tinkoff.task.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Getter
class Application {
  private Integer applicationId;
  private Timestamp dtCreated;
  private String productName;
}
