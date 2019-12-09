package ru.tinkoff.task.contact;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactRepositoryImpl implements ContactRepository {

  JdbcTemplate jdbcTemplate;

  @Inject
  public ContactRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Contact get(Integer contactId) {
    List<ContactDbDto> applicationDbDtos = new ArrayList<>(jdbcTemplate.query(
        "select c.id as contact, a.id as application, a.dt_created, a.product_name from contact c join application a on c.id = a.contact_id where contact_id = ?",
        new Object[]{contactId},
        (rs, row) -> ContactDbDto.builder()
            .contactId(rs.getInt("contact"))
            .applicationId(rs.getInt("application"))
            .dtCreated(rs.getTimestamp("dt_created"))
            .productName(rs.getString("product_name"))
            .build()));
    return new Contact(
        contactId,
        applicationDbDtos.stream()
            .map(a -> Application.builder()
                .applicationId(a.getApplicationId())
                .dtCreated(a.getDtCreated())
                .productName(a.getProductName())
                .build()
            ).collect(Collectors.toList()));
  }
}
