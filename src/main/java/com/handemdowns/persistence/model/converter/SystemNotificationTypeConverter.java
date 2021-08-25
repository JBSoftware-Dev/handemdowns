package com.handemdowns.persistence.model.converter;

import com.handemdowns.persistence.model.SystemNotificationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SystemNotificationTypeConverter implements AttributeConverter<SystemNotificationType, String> {
    @Override
    public String convertToDatabaseColumn(SystemNotificationType attribute) {
        switch (attribute) {
            case NEW_USER:
                return "NEW_USER";
            case NEW_FOLLOWERS:
                return "NEW_FOLLOWERS";
            case NEW_SUPPORT_TICKET:
                return "NEW_SUPPORT_TICKET";
            case DEPLOYMENT:
                return "DEPLOYMENT";
            case SERVER_REBOOT:
                return "SERVER_REBOOT";
			case CRITICAL_ERROR:
				return"CRITICAL_ERROR";
            default:
                throw new IllegalArgumentException("Unknown status attribute: " + attribute);
        }
    }

    @Override
    public SystemNotificationType convertToEntityAttribute(String dbValue) {
        switch (dbValue) {
            case "NEW_USER":
                return SystemNotificationType.NEW_USER;
            case "NEW_FOLLOWERS":
                return SystemNotificationType.NEW_FOLLOWERS;
            case "NEW_SUPPORT_TICKET":
                return SystemNotificationType.NEW_SUPPORT_TICKET;
            case "DEPLOYMENT":
                return SystemNotificationType.DEPLOYMENT;
            case "SERVER_REBOOT":
                return SystemNotificationType.SERVER_REBOOT;
			case "CRITICAL_ERROR":
				return SystemNotificationType.CRITICAL_ERROR;
            default:
                throw new IllegalArgumentException("Unknown status db value: " + dbValue);
        }
    }
}