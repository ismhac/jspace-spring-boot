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
@Table(name = "tbl_resume")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "user_id", nullable = false)
    Candidate candidate;
    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    File file;
    @Builder.Default
    Boolean useYesNo = true;
}
