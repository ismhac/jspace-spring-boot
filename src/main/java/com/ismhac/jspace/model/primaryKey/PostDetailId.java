package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Post;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDetailId implements Serializable {
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    Post post;
}
