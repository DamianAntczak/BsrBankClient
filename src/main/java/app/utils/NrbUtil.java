package app.utils;

public class NrbUtil {

    public static final String BANK_CODE = "00116869";
    public static final int ACCOUNT_NUMBER_LENGTH = 16;
    public static final String PL_CODE = "2521";

    public static String getBankCodeFromNrb(String nrb) {
        return nrb.substring(2, 10);
    }

    public static boolean validateNrb(String nrb) {
        nrb = nrb.replaceAll("\\s","");

        if (nrb.length() != 26)
            return false;

        String checkSum = nrb.substring(0,2);
        String accountNumber = nrb.substring(2);
        String reversedDigits = accountNumber + PL_CODE + checkSum;

        return modString(reversedDigits,97) == 1;
    }

    public static String format(String nrb) {
        StringBuilder builder = new StringBuilder();
        builder.append(nrb);
        builder.insert(2, " ");
        builder.insert(7, " ");
        builder.insert(12, " ");
        builder.insert(17, " ");
        builder.insert(22, " ");
        builder.insert(27, " ");
        nrb = builder.toString();
        return nrb;
    }

    private static int modString(String x, int y) {
        if (x == null || x.isEmpty()) {
            return 0;
        }
        String firstDigit = x.substring(0, x.length() - 1);
        int lastDigit = Integer.parseInt(x.substring(x.length() - 1));
        return (modString(firstDigit, y) * 10 + lastDigit) % y;
    }
}
