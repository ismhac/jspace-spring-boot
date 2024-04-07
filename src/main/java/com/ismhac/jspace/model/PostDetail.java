package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.JobTypeConverter;
import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.primaryKey.PostDetailId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_post_detail")
public class PostDetail extends BaseEntity {

    @EmbeddedId
    PostDetailId id;

    String title;

    @Column(name = "job_type")
    @Convert(converter = JobTypeConverter.class)
    JobType jobType;

    String location;

    String description;

    String pay;

    int quantity;
}
