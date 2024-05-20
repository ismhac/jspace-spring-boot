package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.Skill;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PostSkillId {

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id", nullable = false)
    private Skill skill;
}
