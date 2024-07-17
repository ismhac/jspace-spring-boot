package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.ExperienceConverter;
import com.ismhac.jspace.model.converter.GenderConverter;
import com.ismhac.jspace.model.converter.LocationConverter;
import com.ismhac.jspace.model.enums.Experience;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.Rank;
import com.ismhac.jspace.model.primaryKey.CandidateProfileId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_candidate_profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateProfile extends BaseEntity {

    @EmbeddedId
    CandidateProfileId id;

    @Convert(converter = GenderConverter.class)
    Gender gender;

    @Convert(converter = ExperienceConverter.class)
    Experience experience; //

    @Column(name = "min_salary")
    Integer minSalary; //

    @Column(name = "max_salary")
    Integer maxSalary; //

    @Column(name = "rank")
    Rank rank; //

    @Convert(converter = LocationConverter.class)
    Location location;

    @Column(name = "detail_address")
    String detailAddress;

    @Column(name = "skills_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    String skills;

    @Column(name = "education_info", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    String educationInfo;

    @Column(name = "experience_info", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    String experienceInfo;
}
