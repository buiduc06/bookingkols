package com.bookingkols.web.influence.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InfluenceDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private Gender gender;

    private Double height;

    private Double weight;

    private Double costMin;

    private Double costMax;

    @Size(max = 255)
    private String profilePicture;

    private String introduce;

    private Status status;

    private List<Long> platforms;

    private List<Long> categories;

    private List<Long> industries;

}
