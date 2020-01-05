package com.shifat63.spring_boot_mongodb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

// Author: Shifat63

@Data
@EqualsAndHashCode(exclude = {"showroom"})
@ToString(exclude = {"showroom"})
@Document
public class Employee implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "First Name must not be empty")
    @Size(min = 1,max = 100, message = "First Name must be between 1 to 100 characters")
    private String firstName;

    @NotBlank(message = "Last Name must not be empty")
    @Size(min = 1,max = 100, message = "Last Name must be between 1 to 100 characters")
    private String lastName;

    @NotBlank(message = "ID card number must not be empty")
    @Size(min = 1,max = 100, message = "ID card number must be between 1 to 100 characters")
    private String idCardNo;

    @Past(message = "Date of birth must be any past date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @PastOrPresent(message = "Joining date must be any past or present date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate joiningDate;

    @NotNull(message = "Employee must belong to a showroom")
    @DBRef(lazy = true)
    private Showroom showroom;
}
