package com.shifat63.spring_boot_mongodb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Data
@EqualsAndHashCode(exclude = {"productSet", "employeeSet"})
@ToString(exclude = {"productSet", "employeeSet"})
@Document
public class Showroom implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 100, message = "Name must be between 1 to 100 characters")
    private String name;

    @NotBlank(message = "Address must not be empty")
    @Size(min = 1,max = 200, message = "Address must be between 1 to 200 characters")
    private String address;

    @DBRef(lazy = true)
    private Set<Product> productSet = new HashSet<>();

    @DBRef(lazy = true)
    private Set<Employee> employeeSet = new HashSet<>();
}
