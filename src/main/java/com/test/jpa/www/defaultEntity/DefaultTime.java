package com.test.jpa.www.defaultEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultTime {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
    protected static String time = LocalDateTime.now().format(format);
    public DefaultTime() {
    }

    public LocalDate getLocalDateInMyFormat(String dateTime) {
        return LocalDate.parse(dateTime, format);
    }

    public LocalDateTime getLocalDateTimeInMyFormat(String dateTime){
        return LocalDateTime.parse(dateTime, format);
    }

    public LocalDateTime getLocalDateTimeInMyFormatNow (){
        return LocalDateTime.parse(time, format);
    }

    public LocalDate getLocalDateInMyFormatNow() {
        return LocalDate.parse(time, format);
    }

}
