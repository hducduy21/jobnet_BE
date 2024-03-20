package com.jobnet.post.utils;

import java.math.BigInteger;

public class SalaryUtils {

    public static BigInteger parseSalary(String salaryString) {
        if (salaryString == null || salaryString.isEmpty())
            throw new IllegalStateException("Salary string cannot be null or empty.");

        if (salaryString.length() <= 6)
            throw new IllegalStateException("Salary string length must be greater than 6");

        String cleanedSalaryString = salaryString.substring(0, salaryString.length() - 6) + "000000";
        return new BigInteger(cleanedSalaryString);
    }

    public static boolean checkFormat(String salaryString) {
        return salaryString.length() > 6;
    }
}
