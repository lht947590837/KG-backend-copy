package com.example.kgsystem.model.neo4j;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {
    private String name;

    private String id;

    private String lowAlarm;

    private String highAlarm;

    private String highhighAlarm;

    private String note;
}
