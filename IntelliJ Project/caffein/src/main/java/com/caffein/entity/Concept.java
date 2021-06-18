package com.caffein.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Concept extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conceptId;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne
    private Cafe target;

    public void changeName(String name) {
        this.name = name;
    }
}