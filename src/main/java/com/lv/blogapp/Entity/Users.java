package com.lv.blogapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Users {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY )
    private Long  id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role Role ;

    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password + ", Role=" + Role + "]";
    }




}
