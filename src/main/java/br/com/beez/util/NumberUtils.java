package br.com.beez.util;

import java.text.DecimalFormat;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class NumberUtils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public static String format(double value) {
        return DECIMAL_FORMAT.format(value);
    }

}
