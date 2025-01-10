package com.merc.tech.churchadminservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the church")
    private Long churchId;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the church", example = "Dallas Spanish Oak Cliff")
    private String name;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})[-.\\s]?[2-9]\\d{2}[-.\\s]?\\d{4}$",
    message = "Incorrect format for phone")
    @Schema(description = "Phone number of the church", example = "817-456-7878")
    private String phone;

    @OneToMany(mappedBy = "church",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Member> memberList = new ArrayList<>();
}
