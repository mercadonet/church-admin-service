package com.merc.tech.churchadminservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@DynamoDbBean
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the church")
    @Getter(onMethod = @__({@DynamoDbPartitionKey}))
    private Long churchId;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of the church", example = "Dallas Spanish Oak Cliff")
    @Getter(onMethod = @__({@DynamoDbAttribute("name")}))
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
