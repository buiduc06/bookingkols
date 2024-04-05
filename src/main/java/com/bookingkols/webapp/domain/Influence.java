package com.bookingkols.webapp.domain;

import com.bookingkols.webapp.model.Gender;
import com.bookingkols.webapp.model.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Influences")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Influence {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column
    private Double costMin;

    @Column
    private Double costMax;

    @Column
    private String profilePicture;

    @Column(columnDefinition = "text")
    private String introduce;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "InfluencePlatforms",
            joinColumns = @JoinColumn(name = "influenceId"),
            inverseJoinColumns = @JoinColumn(name = "platformId")
    )
    private Set<Platform> platforms;

    @ManyToMany
    @JoinTable(
            name = "InfluenceCategories",
            joinColumns = @JoinColumn(name = "influenceId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "InfluenceIndustries",
            joinColumns = @JoinColumn(name = "influenceId"),
            inverseJoinColumns = @JoinColumn(name = "industryId")
    )
    private Set<Industry> industries;

    @OneToMany(mappedBy = "influence")
    private Set<Video> videos;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
