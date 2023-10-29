package com.project.euquero.execptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseException {

    private String message;
    private LocalDateTime timestamp;
    private String details;

}
