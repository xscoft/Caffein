package com.caffein.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class Cafe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cafeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String photo;

    @Column(length = 100, nullable = false)
    private String sido;

    @Column(length = 100, nullable = false)
    private String sigugun;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100, nullable = false)
    private String tel;

    @Column(length = 100, nullable = false)
    private int parking;

    @Column(length = 100, nullable = false)
    private String time;

    @Column(length = 100, nullable = false)
    private String dayoff;

    @Column(length = 100, nullable = false)
    private String menu;

    @Column(length = 100, nullable = false)
    private String location;

    @Column(length = 100)
    private String ig;

    @Column(length = 3)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    public void changeName(String name) {
        this.name = name;
    }

    public void changePhoto(String photo) {
        this.photo = photo;
    }

    public void changeTel(String tel) {
        this.tel = tel;
    }

    public void changeParking(int parking) {
        this.parking = parking;
    }

    public void changeTime(String time) {
        this.time = time;
    }

    public void changeDayoff(String dayoff) {
        this.dayoff = dayoff;
    }

    public void changeMenu(String menu) {
        this.menu = menu;
    }

    public void changeIg(String ig) {
        this.ig = ig;
    }

    public void changeScore(int score) {
        this.score = score;
    }
}