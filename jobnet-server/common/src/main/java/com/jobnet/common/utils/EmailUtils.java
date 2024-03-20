package com.jobnet.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    public static String extractDomain(String email) {
        String domain = null;
        if (email != null && !email.isEmpty()) {
            Pattern pattern = Pattern.compile("@(\\S+)$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.find()) {
                domain = matcher.group(1);
            }
        }
        return domain;
    }

    public static boolean isEmailMatchingDomain(String email, String domain) {
        if (email == null || domain == null || email.isEmpty() || domain.isEmpty())
            return false;

        String extractedDomain = extractDomain(email);
        return extractedDomain != null && extractedDomain.equalsIgnoreCase(domain);
    }
}
