package com.handemdowns.web.error;

public class WebException extends RuntimeException {
    public WebException() {
        super("An Error occured.");
    }

    public WebException(String message) {
        super(message);
    }

    public static class Strings {
		public static final String ERROR = "An error occured, please retry the action or contact support.";

        public static final String AD_NOT_FOUND = "The requested ad could not be found.";
        public static final String AD_NOT_ACTIVE = "The requested ad is no longer active, it may have been claimed or put on hold.";
        public static final String AD_NOT_EDITABLE = "Only active ads or ads on hold can be edited.  If you recently created or edited an ad, please wait a few minutes.";
        public static final String AD_EXPIRED = "The requested ad is expired.";
        public static final String AD_PENDING = "The requested ad is pending, please wait while the ad is checked over.";

        public static final String INVALID_TOKEN = "Invalid token, if you copied this link please check if you copied all of it.";
		public static final String EXPIRED_TOKEN = "Your token has expired, please retry the action.";
        public static final String LINK_NOT_ACTIVE = "This link is no longer active.";
        public static final String UNAUTHORIZED = "You are not authorized to perform this action.";
    }
}