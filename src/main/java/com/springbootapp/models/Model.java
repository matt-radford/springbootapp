package com.springbootapp.models;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

@Getter @Setter
@Entity  // ! Added this entity for our data layer
@Table(name = "clubs") // ! Name the table in our database
public class Model {


    @Id // ! This is the unique ID for our model. Every entity needs one.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer titlesCount;

    @Column
    private LocalDate dateFounded;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Comment> comments;
}