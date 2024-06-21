package com.ismhac.jspace.dto.candidatePost.request;

import com.ismhac.jspace.model.enums.ApplyStatus;
import lombok.Data;

@Data
public class ApplyStatusUpdateRequest {
    private int postId;
    private int candidateId;
    private ApplyStatus applyStatus;
    private String notification;
}
