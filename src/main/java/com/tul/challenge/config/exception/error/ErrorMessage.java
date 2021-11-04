package com.tul.challenge.config.exception.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter @Setter @Builder
public class ErrorMessage {
    private String code ;
    private List<Map<String, String >> messages ;
    String date;
}