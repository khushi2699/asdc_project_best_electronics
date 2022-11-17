package com.best.electronics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 4887904943282174032L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String fName;

    @NotEmpty
    private String lName;

    @NotEmpty
    @NaturalId
    @Email
    private String emailAddress;

    @NotEmpty
    @Size(min = 8, message = "Length must be more than 8")
    private String password;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String address;
}
