package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.NotificationTypeConverter;
import com.ismhac.jspace.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_notification")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(columnDefinition = "text")
    String content;
    @Convert(converter = NotificationTypeConverter.class)
    NotificationType type;
    String title;
}
