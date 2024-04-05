package com.bookingkols.webapp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VideoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fileName;

    @Size(max = 255)
    private String fileType;

    private Integer fileSize;

    @Size(max = 255)
    private String filePath;

    private Long influence;

}
