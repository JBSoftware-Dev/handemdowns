package com.handemdowns.persistence.model.converter;

import com.handemdowns.persistence.model.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status attribute) {
        switch (attribute) {
            case ACTIVE:
                return "ACTIVE";
            case HOLD:
                return "HOLD";
            case PENDING:
                return "PENDING";
            case AWAITING_APPROVAL:
                return "AWAITING_APPROVAL";
            case EXPIRED:
                return "EXPIRED";
            case REPORTED:
                return "REPORTED";
            default:
                throw new IllegalArgumentException("Unknown status attribute: " + attribute);
        }
    }

    @Override
    public Status convertToEntityAttribute(String dbValue) {
        switch (dbValue) {
            case "ACTIVE":
                return Status.ACTIVE;
            case "HOLD":
                return Status.HOLD;
            case "PENDING":
                return Status.PENDING;
            case "AWAITING_APPROVAL":
                return Status.AWAITING_APPROVAL;
            case "EXPIRED":
                return Status.EXPIRED;
            case "REPORTED":
                return Status.REPORTED;
            default:
                throw new IllegalArgumentException("Unknown status db value: " + dbValue);
        }
    }
}