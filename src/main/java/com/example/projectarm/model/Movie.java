package com.example.projectarm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "movie_id")
        private Integer id;

        @NotBlank
        private String feature;

        @NotBlank
        private String synopsis;

        @NotNull
        @DateTimeFormat(iso = ISO.DATE)
        private LocalDate premieredate;

        @NotBlank
        private String youtubetrailerId;

        private String coveredroute;

        @NotEmpty
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_gender"))
        private List<Gender> genders;

        @Transient
        private MultipartFile frontpage;

}
