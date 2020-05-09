package dev.jedcua.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FormatUtils {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    private FormatUtils() { }

    public static String formatDate(final LocalDate date, final String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatAmount(final double amount) {
        return DECIMAL_FORMAT.format(amount);
    }
}
