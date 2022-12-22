package com.example.workout.domain.member.entity;

import com.example.workout.global.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_idx", nullable = false)
    private Integer memberId;

    @Column(name = "member_email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public void updatePassword(String password){
        this.password = password;
    }
}
