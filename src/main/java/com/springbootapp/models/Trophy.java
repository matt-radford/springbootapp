package com.springbootapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Entity @Table(name = "trophies") // ! persistence stuff
@Getter @Setter // ! lombok stuff
public class Trophy {

    @Id // ! primary key
    @Column // ! its a column in our lines table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ! SERIAL
    private Integer id;

    @Column(nullable = false)
    @NotNull // ! This belongs to javax.validation, and will be used
    // ! to determine if this field is valid or not.
    // ! You not only need this annotation, but also in your controller
    // ! you need to tell spring boot to only accept valid RequestBody.
    private String name;

    @Column(nullable = false) // ! nullable = false means must have a value.
    @NotNull
    private String country;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "club_trophy", // ! station_line -> make up sensible name
            joinColumns = {@JoinColumn(name = "trophy_id")},
            // ! inverse will be the other side of the relationship.
            inverseJoinColumns = {@JoinColumn(name = "club_id")}
    )
    @JsonIgnoreProperties("trophies")
    private List<Model> clubs;

}

