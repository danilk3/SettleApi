package com.settle.settleapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String name;

    private LocalDate date;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    Set<User> participants = new HashSet<>();

    public void addUser(User user) {
        participants.add(user);
    }
}
