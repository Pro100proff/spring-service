package ru.tinkoff.task.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.task.contact.ApplicationDto;
import ru.tinkoff.task.contact.ContactService;

import javax.inject.Inject;

@RestController
@Api(value = "contact")
@RequestMapping("/contact")
public class ContactController {

  private ContactService contactService;

  @Inject
  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  @ApiOperation(value = "Get last application for contact", response = ApplicationDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved operation"),
      @ApiResponse(code = 500, message = "Incorrect contact_id or server runtime exception")
  }
  )
  @GetMapping("/{id}/last_application")
  public ApplicationDto getLastApplication(@PathVariable(value = "id") Integer contactId) {
    return contactService.getLastApplication(contactId);
  }
}
