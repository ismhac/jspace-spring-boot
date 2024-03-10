package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CandidateId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_candidate")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidate extends BaseEntity{

    @EmbeddedId
    CandidateId id;

    @Column(name = "name")
    String name;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "phone_number")
    String phoneNumber;
}
