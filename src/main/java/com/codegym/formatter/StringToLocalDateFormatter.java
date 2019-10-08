package com.codegym.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class StringToLocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String date, Locale locale) throws ParseException {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
