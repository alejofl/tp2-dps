package com.rt.springboot.app.adapter.driving.dto;

import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.UUID;

public class CommonFieldsMapper {
    @Named("uuidToString")
    static String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Named("bigDecimalToString")
    static String bigDecimalToString(BigDecimal value) {
        return value != null ? String.format("$ %s", value) : null;
    }
}
