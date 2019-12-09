package ru.tinkoff.task.contact;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Data
@Builder
@EqualsAndHashCode
@XmlRootElement
public class ApplicationDto {
  private Integer contactId;
  private Integer applicationId;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
  private Timestamp dtCreated;
  private String productName;

  public ApplicationDto(Integer contactId, Integer applicationId, Timestamp dtCreated, String productName) {
    this.contactId = contactId;
    this.applicationId = applicationId;
    this.dtCreated = dtCreated;
    this.productName = productName;
  }

  static ApplicationDto from(Application application){
    return ApplicationDto.builder()
        .applicationId(application.getApplicationId())
        .dtCreated(application.getDtCreated())
        .productName(application.getProductName())
        .build();
  }
}
