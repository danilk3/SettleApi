package com.settle.settleapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nick_name")
    private String nickName;

    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "participants")
    private Set<Event> activeEvents = new HashSet<>();
}
