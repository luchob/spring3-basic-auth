package eu.balev.security.model.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String userName;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<UserRoleEntity> roles = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public UserEntity setId(Long id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public List<UserRoleEntity> getRoles() {
    return roles;
  }

  public UserEntity setRoles(List<UserRoleEntity> roles) {
    this.roles = roles;
    return this;
  }

  public String getUserName() {
    return userName;
  }

  public UserEntity setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        ", roles=" + roles +
        '}';
  }
}
