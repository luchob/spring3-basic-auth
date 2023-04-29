package eu.balev.security.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  @GetMapping("/pages/anonymous")
  public Message anonymous() {
    return new Message("All Anonymous");
  }

  @GetMapping("/pages/all")
  public Message all() {
    return new Message("All Users");
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping("/pages/managers")
  public Message managers() {
    return new Message("All Managers");
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @GetMapping("/pages/employees")
  public Message employees() {
    return new Message("All Employees");
  }
}

record Message(String msg) {
}
