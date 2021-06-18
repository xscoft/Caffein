package com.caffein.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"writer", "target"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 3, nullable = false)
    private int score;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(length = 200)
    private String photo;

    @ManyToOne
    private Cafe target;

    @ManyToOne
    private Member writer;
}
