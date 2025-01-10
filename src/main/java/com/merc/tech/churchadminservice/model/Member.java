package com.merc.tech.churchadminservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the member")
    private Long memberId;

    @NotBlank(message = "First name is mandatory")
    @Schema(description = "First Name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Schema(description = "Last Name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})[-.\\s]?[2-9]\\d{2}[-.\\s]?\\d{4}$",
            message = "Incorrect format for phone")
    @Schema(description = "Phone number of the member", example = "817-456-7878")
    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Incorrect format for email")
    @Schema(description = "Email of the member", example = "john@doe.com")
    private String email;

    private LocalDate joinedDate;

    @ManyToOne
    @JoinColumn(name = "churchId")
    @JsonBackReference
    private Church church;

    @PrePersist
    public void prePersist() {
        if (joinedDate == null) {
            joinedDate = LocalDate.now();
        }
    }

}
