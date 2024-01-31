package com.posts.app.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Date {
    public static LocalDateTime getCurrent(String zoneId) {
        return LocalDateTime.now(ZoneId.of(zoneId));
    }
}
