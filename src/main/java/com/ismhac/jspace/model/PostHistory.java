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
@Table(name = "tbl_post_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    Post post;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;
}
