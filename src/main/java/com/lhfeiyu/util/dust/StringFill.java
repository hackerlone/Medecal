package com.lhfeiyu.util.dust;

/**
 * 字符串补齐
 * @author zengt
 */
public class StringFill {
    public static String stringFill(String source, int fillLength, char fillChar, boolean isLeftFill) {
        if (source == null || source.length() >= fillLength)
            return source;
        char[] c = new char[fillLength];
        char[] s = source.toCharArray();
        int len = s.length;
        if (isLeftFill) {
            int fl = fillLength - len;
            for (int i = 0; i < fl; i++) {
                c[i] = fillChar;
            }
            System.arraycopy(s, 0, c, fl, len);
        } else {
            System.arraycopy(s, 0, c, 0, len);
            for (int i = len; i < fillLength; i++) {
                c[i] = fillChar;
            }
        }
        return String.valueOf(c);
    }
}
