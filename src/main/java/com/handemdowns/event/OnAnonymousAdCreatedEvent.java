package com.handemdowns.event;

import com.handemdowns.persistence.model.Ad;
import org.springframework.context.ApplicationEvent;

public class OnAnonymousAdCreatedEvent extends ApplicationEvent {
    private Ad ad;

    public OnAnonymousAdCreatedEvent(Ad ad) {
        super(ad);
        this.ad = ad;
    }

    public Ad getAd() {
        return ad;
    }
}