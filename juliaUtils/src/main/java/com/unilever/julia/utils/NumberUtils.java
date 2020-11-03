package com.unilever.julia.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {

    private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
    private static final String DECIMAL_FORMAT_PATTERN_BRAZIL = "R$ ###,###,##0.00";
    private static final DecimalFormatSymbols FORMAT_SYMBOLS_BRAZIL = new DecimalFormatSymbols(LOCALE_BRAZIL);
    private static final DecimalFormat DECIMAL_FORMAT_BRAZIL = new DecimalFormat(DECIMAL_FORMAT_PATTERN_BRAZIL, FORMAT_SYMBOLS_BRAZIL);

    private static String toCurrency(double value, int digits) {
        NumberUtils.DECIMAL_FORMAT_BRAZIL.setMaximumFractionDigits(digits);
        NumberUtils.DECIMAL_FORMAT_BRAZIL.setMinimumFractionDigits(digits);
        return NumberUtils.DECIMAL_FORMAT_BRAZIL.format(value);
    }

    public static String toCurrencyBrazil(double value, boolean prefixo) {
        String currency = toCurrency(value, 2);

        if (prefixo) {
            return currency;
        } else {
            currency = StringUtils.replace(currency, "R", "");
            currency = StringUtils.replace(currency, "$", "");
            currency = StringUtils.deleteWhitespace(currency);
            return currency;
        }
    }

    public static String toDecimalKG(double value) {
        String decimalKg = toCurrency(value, 3);
        decimalKg = StringUtils.replace(decimalKg, "R", "");
        decimalKg = StringUtils.replace(decimalKg, "$", "");
        decimalKg = StringUtils.deleteWhitespace(decimalKg);
        decimalKg = normalizeDecimal(decimalKg);

        return decimalKg;
    }

    public static String normalizeDecimal(String decimal) {
        decimal = StringUtils.replace(decimal, ",", ".");

        String[] split = StringUtils.split(decimal, ".");

        String number = "";

        for (int i = 0; i < split.length; i++) {
            String str = split[i];

            if( (i + 1) == split.length ) {
                number += "."+str;
            } else {
                number += str;
            }
        }

        return number;
    }

    public static double toGeoLatLng(String value) {
        double latLng = toDouble(value);
        return latLng;
    }

	public static double toDouble(String value) {
        if (StringUtils.isNotEmpty(value)) {
            try {
                // 10,500.00 -> 10500.00
                String temp = StringUtils.deleteWhitespace(value);

                // remove prefix
                for (Character it: temp.toCharArray()) {
                    if (!Character.isDigit(it) && !StringUtils.equalsAny(String.valueOf(it), ",", ".")) {
                        temp = StringUtils.replace(temp, String.valueOf(it), "");
                    }
                }

                //temp = StringUtils.replace(temp, "R$", "");
                temp = StringUtils.replace(temp, ",", ".");

                if (StringUtils.contains(temp, ".")) {
                    // ex: 4500.00 10.500.00
                    String[] split = StringUtils.split(temp, ".");
                    int count = 0;
                    int lastIndex = (split.length - 1);
                    StringBuilder builder = new StringBuilder();
                    for (String itSplit: split) {
                        if (count == lastIndex) {
                            builder.append(".").append(itSplit);
                        } else {
                            builder.append(itSplit);
                        }
                        count++;
                    }
                    return Double.valueOf(builder.toString());
                }

                return Double.valueOf(temp);
            } catch (Exception e) {
                return 0.0;
            }
        }
        return 0.0;
	}

    public static String toString(double value) {
        String valueReturn = "";
        try {
            valueReturn = String.valueOf(Double.valueOf(value).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueReturn;
    }

	public static Integer toInteger(String value) {
        if (StringUtils.isNotEmpty(value)) {
            try {
                return Integer.valueOf(StringUtils.deleteWhitespace(value));
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
	}

    public static Integer toIntegerReturnNull(String value) {
        Integer valueReturn = null;
        if (StringUtils.isNotEmpty(value)) {
            try {
                value = StringUtils.deleteWhitespace(value);
                valueReturn = Integer.valueOf(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueReturn;
    }

    public static double roundDecimal(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /*
    public static double arredondar3CasaDecimais(double valor) {
        BigDecimal valorRound = BigDecimal.valueOf(valor).setScale(3, BigDecimal.ROUND_HALF_UP);
        return valorRound.doubleValue();
    }

    public static double arredondar1CasaDecimal(double valor) {
        BigDecimal valorRound = BigDecimal.valueOf(valor).setScale(1, BigDecimal.ROUND_HALF_UP);
        return valorRound.doubleValue();
    }
    */

    public static double toPositiveValue(double value) {
        if (value < 0) {
            value = Math.abs(value);
        }
        return value;
    }

    public static double toNegativeValue(double value) {
        if (value > 0) {
            value *= -1;
        }
        return value;
    }

    public static long megabytesToKb(float size) {
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float result = (sizeMb * size);
        return Float.valueOf(result).longValue();
    }
}
