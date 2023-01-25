package yst.ymodule;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberToWords {


    private static String[] yekan = new String[]{" یک ", " دو ", " سه ", " چهار ", " پنج ", " شش ", " هفت ", " هشت ", " نه "};
    private static String[] dahgan = new String[]{" بیست ", " سی ", " چهل ", " پنجاه ", " شصت ", " هفتاد ", " هشتاد ", " نود "};
    private static String[] sadgan = new String[]{" یکصد ", " دویست ", " سیصد ", " چهارصد ", " پانصد ", " ششصد ", " هفتصد ", " هشتصد ", " نهصد "};
    private static String[] dah = new String[]{" ده ", " یازده ", " دوازده ", " سیزده ", " چهارده ", " پانزده ", " شانزده ", " هفده ", " هیجده ", " نوزده "};

    public static String onWorkFa(final BigDecimal num, final String Unit) {
        return onDo(num, 0) + " " + Unit;
    }

    private static String onDo(BigDecimal num, int level) {
        if (num == null) {
            return "";
        }
        // convert negative number to positive and get wordify value
        if (num.compareTo(new BigDecimal(0)) < 0) {
            num = num.negate();
            return "منفی " + onDo(num, level);
        }
        if (num.compareTo(new BigDecimal(0)) == 0) {
            if (level == 0) {
                return "صفر";
            } else {
                return "";
            }

        }
        String result = "";
        if (level > 0) {
            result += " و ";
            level -= 1;
        }

        if (num.compareTo(new BigDecimal(10)) < 0) {
            result += yekan[num.add(new BigDecimal(1).negate()).intValue()];
        } else if (num.compareTo(new BigDecimal(20)) < 0) {
            result += dah[num.add(new BigDecimal(10).negate()).intValue()];
        } else if (num.compareTo(new BigDecimal(100)) < 0) {
            result += dahgan[num.divide(new BigDecimal(10)).add(new BigDecimal(2).negate()).intValue()] + onDo(num.remainder(new BigDecimal(10)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000)) < 0) {
            result += sadgan[num.divide(new BigDecimal(100)).add(new BigDecimal(1).negate()).intValue()] + onDo(num.remainder(new BigDecimal(100)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000000)) < 0) {
            result += onDo(num.divide(new BigDecimal(1000)), level) + " هزار " + onDo(num.remainder(new BigDecimal(1000)), level + 1);
        } else if (num.compareTo(new BigDecimal(1000000000)) < 0) {
            result += onDo(num.divide(new BigDecimal(1000000)), level) + " میلیون " + onDo(num.remainder(new BigDecimal(1000000)), level + 1);
        } else if (num.compareTo(new BigDecimal(Long.valueOf("1000000000000"))) < 0) {
            result += onDo(num.divide(new BigDecimal(Long.parseLong("1000000000"))), level) + " میلیارد " + onDo(num.remainder(new BigDecimal(Long.parseLong("1000000000"))), level + 1);
        } else if (num.compareTo(new BigDecimal(Long.valueOf("1000000000000000"))) < 0) {
            result += onDo(num.divide(new BigDecimal(Long.parseLong("1000000000000"))), level) + " تریلیارد " + onDo(num.remainder(new BigDecimal(Long.parseLong("1000000000000"))), level + 1);
        }
        return result;
    }


    private static final String[] tensNames = {"", " ten", " twenty", " thirty", " forty",
            " fifty", " sixty", " seventy", " eighty", " ninety"};

    private static final String[] numNames = {"", " one", " two", " three", " four", " five",
            " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen",
            " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0)
            return soFar;
        return numNames[number] + " hundred" + soFar;
    }

    public static String onWorkEn(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "zero";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }
}