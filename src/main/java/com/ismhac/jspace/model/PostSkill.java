package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.PostSkillId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_post_skill")
public class PostSkill {
    @EmbeddedId
    PostSkillId id;
}
