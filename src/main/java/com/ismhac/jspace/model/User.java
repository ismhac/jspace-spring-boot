package com.ismhac.jspace.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String username;

    String password;

    @Column(unique = true)
    String email;

    String name;

    String picture;

    @Column(name = "picture_id")
    String pictureId;

    String background;

    @Column(name = "background_id")
    String backgroundId;

    String phone;

    boolean activated;

    private String position;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    Role role;
}
