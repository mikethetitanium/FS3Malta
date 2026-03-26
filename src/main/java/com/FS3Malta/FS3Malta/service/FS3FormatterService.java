package com.FS3Malta.FS3Malta.service;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Formats FS3Report fields into fixed-width strings for char-box display.
 * Each method returns a string padded/truncated to exactly n characters.
 */
@Component("fmt")
public class FS3FormatterService {

    public String pad(String value, int length) {
        if (value == null) value = "";
        if (value.length() >= length) return value.substring(0, length);
        return String.format("%-" + length + "s", value);
    }

    public String money(BigDecimal value, int length) {
        if (value == null) value = BigDecimal.ZERO;
        String s = String.format("%.2f", value);
        return pad(s, length);
    }

    public String dateDay(LocalDate date) {
        if (date == null) return "  ";
        return date.format(DateTimeFormatter.ofPattern("dd"));
    }

    public String dateMonth(LocalDate date) {
        if (date == null) return "  ";
        return date.format(DateTimeFormatter.ofPattern("MM"));
    }

    public String dateYear(LocalDate date) {
        if (date == null) return "    ";
        return date.format(DateTimeFormatter.ofPattern("yyyy"));
    }

    public String num(Integer value, int length) {
        if (value == null) return pad("", length);
        return pad(value.toString(), length);
    }

    public String ch(String padded, int index) {
        if (padded == null || index >= padded.length()) return " ";
        return String.valueOf(padded.charAt(index));
    }
}
