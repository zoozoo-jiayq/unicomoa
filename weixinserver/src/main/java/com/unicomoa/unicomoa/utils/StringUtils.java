package com.unicomoa.unicomoa.utils;


import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.cglib.core.ReflectUtils;

import sun.misc.BASE64Decoder;


public class StringUtils extends org.springframework.util.StringUtils {

    // from USER_INFO to UserInfo
    public static String transformToJava(String s) {
        s = s.replace('_', ' ');
        s = s.toLowerCase();
        s = StringUtils.capitaliseAllWords(s);
        s = StringUtils.remove(s, ' ');
        return s;
    }
    
	// from UserInfo to user_info
	public static String transformToDB(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}

		s = capitalize(s);
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(s);
		
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toLowerCase());
			sb.append(matcher.end() == s.length() ? "" : "_");
		}
		
		return sb.toString();
	}
  
    public static String formatNumber(Object o, String pattern) {
        Number num = Variant.valueOf(o).numberValue();
        if (num == null)
            return null;
        DecimalFormat fmt = new DecimalFormat(pattern);
        return fmt.format(num.doubleValue());
    }
    
    public static String formatDate(Date date, String pattern) {
		if (date == null)
			return null;
		return new SimpleDateFormat(pattern).format(date);
	}
    
    public static String toQueryString(Map<String, Object> params) {
		if (params == null || params.isEmpty())
			return "";

		int i = 0;
		StringBuilder sb = new StringBuilder();
		for (Entry<String,Object> entry : params.entrySet()) {
			String key = entry.getKey();
			if(key == null || "".equals(key))
				continue;
			
			if (i != 0)
				sb.append("&");
			Object value= entry.getValue();
			String sValue = value != null ? value.toString() : "";
			sValue = encodeURIComponent(sValue, "utf-8");
			sb.append(key + "=" + sValue);
			i++;
		}

		return sb.toString();
	}
    
    public static String encodeURIComponent(String url, String encoding) {
		if (url == null)
			return null;
		if (encoding == null)
			encoding = "UTF-8";
		try {
			return URLEncoder.encode(url, encoding);
		} catch (Exception e) {
			throw Exceptions.wrap(e);
		}
	}
    
    public static String safeSubString(String s, int fromIdx) {
		return safeSubString(s, fromIdx, -1);
	}

	public static String safeSubString(String s, int fromIdx, int toIdx) {
		if (s == null)
			return null;
		if(fromIdx >= s.length())
			return null;
		
		if (fromIdx < 0)
			fromIdx = 0;
		if (toIdx < 0 || toIdx >= s.length())
			toIdx = s.length();
		return s.substring(fromIdx, toIdx);
	}
    
	public static String genRandomNumber(int length){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++){
			sb.append(new Random().nextInt(10));
		};
		
		return sb.toString();
	}
    
    public static String getUUID(){
    	return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static String generateInviteCode(){
    	return genRandomNumber(6);
    }
    
    /**
     * 高位自动补0
     * @param no
     * @param length
     * @return
     */
    public static String generateFixLengthNo(int no,int length){
    	String str = "0000000000000"+no;
    	if(str.length()>length){
    		return str.substring(str.length()-length);
    	}
    	throw Exceptions.code("系统错误");
    }
    

    public static double roundDown(double d, int i){
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(i, BigDecimal.ROUND_DOWN).doubleValue();
	}
    
    //保留两位小数，不做四舍五入
    public static String subDouble(double d){
    	String str = d+"";
    	int index = str.indexOf(".");
    	if(index>=0){
    		str+="00";
    		return str.substring(0, index+3);
    	}else{
    		return str+".00";
    	}
    			
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static final String[] EMPTY_STRING_LIST = new String[0];

    private static final char gsN2H[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final int gsHI[] = { 4096, 256, 16 };

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
    
    public static String buildWordList(String prefix, int start, int count,
            String postfix) {
        if (prefix == null)
            prefix = "";
        if (postfix == null)
            postfix = "";
        StringBuilder buf = new StringBuilder();
        int i;
        for (i = start; i < count; i++) {
            buf.append(prefix + (start + i) + postfix);
            if (i != count - 1)
                buf.append(",");
        }
        return buf.toString();
    }

    public static String appendQueryString(String url, String str) {
        if (str == null || str.length() <= 0)
            return url;

        int pos = url.indexOf('?');
        if (pos < 0) {
            return url += "?" + str;
        } else if (url.endsWith("?")) {
            return url + str;
        } else {
            return url += "&" + str;
        }
    }

    public static String buildWordList(String prefix, int count) {
        return buildWordList(prefix, 0, count, null);
    }

    /**
     * test if string ends with a certain char
     * 
     * @param s
     *            may be null
     * @param c
     * @return true for string end with a certain character
     */
    public static boolean endWith(String s, char c) {
        if (s == null || s.length() <= 0)
            return false;
        return s.charAt(s.length() - 1) == c;
    }

    public static boolean isStringNull(Object o) {
        if (o == null)
            return true;
        if (o instanceof String)
            return o.toString().length() <= 0;
        return false;
    }

    /**
     * test if string starts with a certain string, ignoring case both strings
     * can not be null
     */
    public static boolean startsWithIgnoreCase(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;
        if (s1.length() < s2.length())
            return false;
        return s1.substring(0, s2.length()).equalsIgnoreCase(s2);
    }

    public static boolean endsWithIgnoreCase(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 < len2)
            return false;
        return s1.substring(len1 - len2, len1).equalsIgnoreCase(s2);
    }

    public static boolean containsSub(String s, String s2) {
        return safeIndexOf(s, s2) >= 0;
    }

    public static int safeIndexOf(String s, String s2) {
        if (s == null)
            return -1;
        return s.indexOf(s2);
    }

    public static int safeIndexOf(String s, String s2, int pos) {
        if (s == null || s.length() <= pos)
            return -1;
        return s.indexOf(s2, pos);
    }

    public static int safeLastIndexOf(String s, String s2) {
        if (s == null)
            return -1;
        return s.lastIndexOf(s2);
    }

    public static int safeLastIndexOf(String s, String s2, int pos) {
        if (s == null || s.length() <= pos)
            return -1;
        return s.lastIndexOf(s2, pos);
    }

    /**
     * abc --> Abc
     * 
     * @param name
     * @return name with the first character changed to upper case
     */
    public static String capitalize(String name) {
        if (name == null || name.length() <= 0)
            return name;

        char first;
        first = name.charAt(0);
        if (Character.isUpperCase(first))
            return name;

        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * Abc --> abc
     * 
     * @param name
     * @return a string start with lower case character
     */
    public static String decapitalize(String name) {
        if (name == null || name.length() <= 0) {
            return name;
        }

        char first;
        first = name.charAt(0);
        if (Character.isLowerCase(first))
            return name;

        char chars[] = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * split a string into sub pieces and strip them, then return those pieces
     * with non-null value.
     * 
     * @param str
     *            the string to be splited
     * @param ch
     *            the separator character
     * @return null if no non-null pieces after strip
     */
    public static List<String> stripedSplit(String str, char splitChar) {
    	List<String> ret = new ArrayList<String>();
        if (str == null)
            return ret;
        int i, n = str.length();
        int pos = 0;
        int pos2 = 0;
        int state = 0;
        for (i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (c == splitChar) {
                if (pos < pos2) {
                    String s = str.substring(pos, pos2);
                    ret.add(s);
                }
                pos = i + 1;
                pos2 = pos;
                state = 0;
            } else {
                switch (state) {
                case 0:
                    if (isBlankChar(c)) {
                        pos++;
                        pos2 = pos;
                    } else {
                        state = 1;
                        pos2++;
                    }
                    break;
                case 1:
                    if (!isBlankChar(c)) {
                        pos2 = i + 1;
                    }
                }
            }
        }
        if (pos < pos2)
            ret.add(str.substring(pos, pos2));
        return ret;
    }

    /**
     * split a string into sub pieces, for example: &x --> [empty,x] y&x -->
     * [y,x]
     * 
     * @param str
     * @param ch
     *            seperation character
     * @return string
     */
    public static String[] split(String str, char ch) {
        if (str == null)
            return EMPTY_STRING_LIST;

        int pos1, pos2;
        List<String> list = new ArrayList<String>();
        pos1 = 0;
        do {
            pos2 = str.indexOf(ch, pos1);
            if (pos2 < 0)
                break;
            list.add(str.substring(pos1, pos2));
            pos1 = pos2 + 1;
        } while (true);
        list.add(str.substring(pos1));
        return (String[]) list.toArray(EMPTY_STRING_LIST);
    }
    
    public static List<String> splitToList(String str, String sub) {
        if (str == null)
            return new ArrayList<String>(0);

        StringTokenizer tokenizer = new StringTokenizer(str, sub);
        int n = tokenizer.countTokens();
        List<String> ret = new ArrayList<String>(n);
 
        for (int i = 0; i < n; i++) {
            ret.add(tokenizer.nextToken());
        }
        return ret;
    }

    /**
     * combine several string into a single one
     * 
     * @param parts
     * @param sep
     *            seperation character
     * @return combined string
     */
    public static String joinArray(String[] parts, String sep) {
        StringBuilder buf = new StringBuilder();
        int i, n = parts.length;
        for (i = 0; i < n; i++) {
            buf.append(parts[i]);
            if (i != n - 1)
                buf.append(sep);
        }
        return buf.toString();
    }

    /**
     * Joins the elements of the provided iterator into a single string
     * containing the provided elements. No delimiter is added before or after
     * the list. A null separator is the same as a blank String.
     * 
     * @param iterator
     *            the iterator of values to join together
     * @param separator
     *            the separator character to use
     * @return the joined String
     */
    public static String join(Object o, String separator) {
        if (o == null)
            return null;
        Iterator<?> iterator = null;
        if (o instanceof Collection) {
            iterator = ((Collection<?>) o).iterator();
        } else if (o instanceof Iterator) {
            iterator = (Iterator<?>) o;
        } else if (o instanceof Object[]) {
            iterator = Arrays.asList((Object[]) o).iterator();
        } else {
            return null;
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder buf = new StringBuilder(256); // Java default is 16,
                                                    // probably too small
        while (iterator.hasNext()) {
            buf.append(iterator.next());
            if (iterator.hasNext()) {
                buf.append(separator);
            }
        }
        return buf.toString();
    }

    /**
     * return a string with sorted parts
     * 
     * @param str
     * @param sep
     * @return a string with sorted parts
     */
    public static String toOrderedString(String str, char sep) {
        String[] parts = split(str, sep);
        Arrays.sort(parts);
        return joinArray(parts, new String(sep + ""));
    }

    /**
     * Splits the string on every token into an array of stack frames.
     * 
     * @param string
     *            the string to split
     * @param onToken
     *            the token to split on
     * @return the resultant array
     */
    public static String[] splitOn(String string, String onToken) {
        StringTokenizer tokenizer = new StringTokenizer(string, onToken);
        String[] result = new String[tokenizer.countTokens()];

        for (int i = 0; i < result.length; i++) {
            result[i] = tokenizer.nextToken();
        }

        return result;
    }

    public static String[] toWordList(String str) {
        if (str == null)
            return EMPTY_STRING_LIST;

        // int begin = 0;
        int end = str.length();
        int pos1, pos2;

        ArrayList<String> wordList = new ArrayList<String>();
        pos1 = 0;
        while (true) {
            // skip leading blank
            for (; pos1 < end; pos1++) {
                if (!isBlankChar(str.charAt(pos1)))
                    break;
            }

            // end of string
            if (pos1 == end)
                break;

            // find the end of the word
            for (pos2 = pos1 + 1; pos2 < end; pos2++) {
                if (isBlankChar(str.charAt(pos2)))
                    break;
            }
            wordList.add(str.substring(pos1, pos2));

            pos1 = pos2;
        }
        return (String[]) wordList.toArray(EMPTY_STRING_LIST);
    }

    /**
     * return the minimum length of strings in strs
     * 
     * @param strs
     *            a string list
     * @return length of the shortest string
     */
    public static int minLength(String[] strs) {
        int i, n = strs.length;
        if (n <= 0)
            return 0;

        int len = strs[0].length();
        for (i = 1; i < n; i++) {
            String str = strs[i];
            if (str == null)
                continue;
            int strlen = str.length();
            if (strlen < len)
                len = strlen;
        }
        return len;
    }

    /**
     * return the maximum length of strings in strs
     * 
     * @param strs
     *            a string list
     * @return length of the longest string
     */
    public static int maxLength(String[] strs) {
        int len = 0;
        int i, n = strs.length;
        for (i = 0; i < n; i++) {
            String str = strs[i];
            if (str == null)
                continue;
            int strlen = str.length();
            if (strlen > len)
                len = strlen;
        }
        return len;
    }

    /**
     * if a string matches with other string at pos
     * 
     * @param str
     * @param other
     * @param pos
     *            start index in str
     * @param ignoreCase
     * @return true for match
     */
    public static boolean match(String str, String other, int pos,
            boolean ignoreCase) {
        int len = other.length();
        return str.regionMatches(ignoreCase, pos, other, 0, len);
    }

    /**
     * replace substring
     * 
     * @param str
     * @param oldSubs
     * @param newSubs
     * @param ignoreCase
     * @return a string with parts replaced
     */
    public static String replace(String str, String[] oldSubs,
            String[] newSubs, boolean ignoreCase) {
        if (str == null)
            return null;

        // string shorter than old sub will not be changed
        int minSubLen = minLength(oldSubs);
        if (minSubLen > str.length())
            return str;

        StringBuilder buf = new StringBuilder();

        int pos;
        int len = str.length();
        int i, n = oldSubs.length;
        for (pos = 0; pos < len; pos++) {
            for (i = 0; i < n; i++) {
                if (match(str, oldSubs[i], pos, ignoreCase))
                    break;
            }
            if (i == n) {
                // not match here
                buf.append(str.charAt(pos));
            } else {
                buf.append(newSubs[i]);
                pos += oldSubs[i].length() - 1;// pos++ will be called laterly
            }
        }
        return buf.toString();
    }

    /**
     * replace substring
     * 
     * @param str
     * @param oldSub
     * @param newSub
     * @return a string with parts replaced
     */
    public static String replace(String str, String oldSub, String newSub) {
        if (str == null)
            return null;

        if (oldSub == null)
            return str;

        if (newSub == null)
            newSub = "";

        int pos1 = 0, pos2 = 0;
        pos2 = str.indexOf(oldSub);
        // no oldSuc occur
        if (pos2 < 0)
            return str;

        StringBuilder buf = new StringBuilder();
        int len = oldSub.length();
        do {
            buf.append(str.substring(pos1, pos2));
            buf.append(newSub);
            pos1 = pos2 + len;
            pos2 = str.indexOf(oldSub, pos1);
        } while (pos2 >= 0);
        if (pos1 < str.length())
            buf.append(str.substring(pos1));

        return buf.toString();
    }

    /**
     * replace substring
     * 
     * @param str
     * @param oldSub
     * @param newSub
     * @return a StringBuilder with parts replaced
     */
    public static StringBuilder replaceForBuffer(StringBuilder str,
            String oldSub, String newSub) {

        if (str == null)
            return null;

        int pos;
        pos = str.indexOf(oldSub, 0);
        // no oldSuc occur
        if (pos < 0)
            return str;

        int len = oldSub.length();

        do {
            str.replace(pos, pos + len, newSub);
            pos = str.indexOf(oldSub, pos + newSub.length());
        } while (pos >= 0);

        return str;
    }

    public static String replace(String str, Map<String, String> map) {
        if (str == null || str.length() <= 0)
            return str;

        StringBuilder buf = new StringBuilder(str);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String oldSub = entry.getKey().toString();
            if (oldSub == null)
                continue;
            String newSub = (String) entry.getValue();
            if (newSub == null)
                newSub = "";
            replaceForBuffer(buf, oldSub, newSub);
        }
        return buf.toString();
    }

    public boolean isBlankStr(String s) {
        if (s == null)
            return true;
        int i, n = s.length();
        for (i = 0; i < n; i++) {
            if (!isBlankChar(s.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * test if a character is a blank char
     * 
     * @param c
     * @return boolean
     */
    public static boolean isBlankChar(char c) {
        return c == ' ' || c == '\t' || c == 160 || c == '\r' || c == '\n';
    }

    /**
     * strip blank chars from s
     * 
     * @param s
     * @return null if s is empty
     */
    public static String strip(String s) {
        if (s == null)
            return null;

        int beg, end, len;
        len = s.length();
        if (len <= 0)
            return null;

        for (beg = 0; beg < len; beg++) {
            if (!isBlankChar(s.charAt(beg)))
                break;
        }

        for (end = len; end > beg; end--) {
            if (!isBlankChar(s.charAt(end - 1)))
                break;
        }

        if (beg >= end)
            return null;

        if (beg == 0 && end == s.length())
            return s;

        return s.substring(beg, end);
    }

    public static String stripBound(String s, String bound) {
        if (s == null)
            return null;
        if (bound == null)
            return null;

        int beg, end, len;
        len = s.length();
        if (len <= 0)
            return null;

        for (beg = 0; beg < len; beg++) {
            if (bound.indexOf(s.charAt(beg)) < 0)
                break;
        }

        for (end = len; end > beg; end--) {
            if (bound.indexOf(s.charAt(end - 1)) < 0)
                break;
        }

        if (beg >= end)
            return null;

        if (beg == 0 && end == s.length())
            return s;

        return s.substring(beg, end);
    }

    public static String[] stripArray(String[] s) {
        if (s == null)
            return null;
        int i, n = s.length;
        for (i = 0; i < n; i++) {
            s[i] = strip(s[i]);
        }
        return s;
    }

    /**
     * modify a string at a certain position
     * 
     * @param str
     * @param pos
     * @param c
     * @return string
     */
    public static String setAt(String str, int pos, char c) {
        char ary[] = str.toCharArray();
        ary[pos] = c;
        return new String(ary);
    }

    /**
     * Checks whether the String contains only digit characters. Null and blank
     * string will return false.
     * 
     * @param str
     *            the string to check
     * @return boolean contains only unicode numeric
     */
    public static boolean isDigits(String str) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * test if a character is alpha or numeric
     * 
     * @param c
     * @return boolean
     */
    public static boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9');
    }

    /**
     * test if a character is alpha
     * 
     * @param c
     * @return boolean
     */
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * test if a character can be used in a alpha variable name
     * 
     * @param c
     * @return boolean
     */
    public static boolean isAlphaVariable(char c) {
        return c == '_' || isAlpha(c);
    }

    /**
     * test if a character can be used in a variable name
     * 
     * @param c
     * @return boolean
     */
    public static boolean isVariable(char c) {
        return Character.isJavaIdentifierPart(c);// //return c == '_' ||
                                                 // isAlphaNumeric(c);
    }

    /**
     * test if a character is a digit number
     * 
     * @param c
     * @return boolean
     */
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * test if a character maybe belong to a floating point string
     * 
     * @param c
     * @return boolean
     */
    public static boolean maybeNumber(char c) {
        return isNumber(c) || c == '.' || c == 'e' || c == 'E';
    }

    /**
     * test if a character maybe start of a floating point string
     * 
     * @param c
     * @return boolean
     */
    public static boolean maybeNumberStart(char c) {
        return isNumber(c) || c == '.' || c == '-';
    }

    /**
     * append object into a StringBuilder, if obj is array, append all childs
     * 
     * @param buf
     * @param obj
     */
    public static void append(StringBuilder buf, Object obj) {
        if (obj != null) {
            if (obj.getClass().isArray()) {
                int i, n;
                n = Array.getLength(obj);
                for (i = 0; i < n; i++) {
                    buf.append(Array.get(obj, i));
                    if (i != n - 1)
                        buf.append(',');
                }
            } else {
                buf.append(obj);
            }
        }
    }

    /**
     * Escapes any values it finds into their String form. So a tab becomes the
     * characters '\\' and 't'.
     * 
     * @param str
     *            String to escape values in
     * 
     * @return String with escaped values
     * @throws NullPointerException
     *             if str is null
     */
    public static String escapeUnicode(String str) {
        // improved with code from cybertiger@cyberiantiger.org
        // unicode from him, and defaul for < 32's.
        int sz = str.length();
        StringBuilder buffer = new StringBuilder(2 * sz);
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                buffer.append("\\u" + Integer.toHexString(ch));
            } else if (ch > 0xff) {
                buffer.append("\\u0" + Integer.toHexString(ch));
            } else if (ch > 0x7f) {
                buffer.append("\\u00" + Integer.toHexString(ch));
            } else if (ch < 32) {
                switch (ch) {
                case '\b':
                    buffer.append('\\');
                    buffer.append('b');
                    break;
                case '\n':
                    buffer.append('\\');
                    buffer.append('n');
                    break;
                case '\t':
                    buffer.append('\\');
                    buffer.append('t');
                    break;
                case '\f':
                    buffer.append('\\');
                    buffer.append('f');
                    break;
                case '\r':
                    buffer.append('\\');
                    buffer.append('r');
                    break;
                default:
                    if (ch > 0xf) {
                        buffer.append("\\u00" + Integer.toHexString(ch));
                    } else {
                        buffer.append("\\u000" + Integer.toHexString(ch));
                    }
                    break;
                }
            } else {
                switch (ch) {
                case '\'':
                    buffer.append('\\');
                    buffer.append('\'');
                    break;
                case '"':
                    buffer.append('\\');
                    buffer.append('"');
                    break;
                case '\\':
                    buffer.append('\\');
                    buffer.append('\\');
                    break;
                default:
                    buffer.append(ch);
                    break;
                }
            }
        }
        return buffer.toString();
    }

    public static String quote(String s) {
        if (s == null)
            return null;

        StringBuilder sb = new StringBuilder(s.length() + 10);
        sb.append('"');
        sb.append(escape(s));
        sb.append('"');
        return sb.toString();
    }

    /**
     * Escapes any values it finds into their String form. So a tab becomes the
     * characters '\\' and 't'.
     * 
     * @param str
     *            String to escape values in
     * 
     * @return String with escaped values
     * @throws NullPointerException
     *             if str is null
     */
    public static String escape(String src) {
        if (isEmpty(src)) {
            return src;
        }
        int len = src.length();
        StringBuilder buf = null;
        for (int i = 0; i < len; i++) {
            char ch = src.charAt(i);
            String rep;
            switch (ch) {
            case '\\':
                rep = "\\\\";
                break;
            case '\"':
                rep = "\\\"";
                break;
            case '\'':
                rep = "\\\'";
                break;
            case '\t':
                rep = "\\t";
                break;
            case '\n':
                rep = "\\n";
                break;
            case '\r':
                rep = "\\r";
                break;
            case '\b':
                rep = "\\b";
                break;
            case '\f':
                rep = "\\f";
                break;
            default:
                rep = null;
                break;
            }
            if (rep != null) {
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(src.substring(0, i));
                    }
                }
                buf.append(rep);
            } else {
                if (buf != null) {
                    buf.append(ch);
                }
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return src;
    }

    public static String unescape(String value) {
        if (isEmpty(value)) {
            return value;
        }
        StringBuilder buf = null;
        int i,len = value.length() - 1;
        for (i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch == '\\') {
                int j = i;
                i++;
                ch = value.charAt(i);
                switch (ch) {
                case '\\':
                    ch = '\\';
                    break;
                case '\"':
                    ch = '\"';
                    break;
                case '\'':
                    ch = '\'';
                    break;
                case 't':
                    ch = '\t';
                    break;
                case 'n':
                    ch = '\n';
                    break;
                case 'r':
                    ch = '\r';
                    break;
                case 'b':
                    ch = '\b';
                    break;
                case 'f':
                    ch = '\f';
                    break;
                default:
                    j--;
                }
                if (buf == null) {
                    buf = new StringBuilder(len);
                    if (j > 0) {
                        buf.append(value.substring(0, j));
                    }
                }
                buf.append(ch);
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        if (buf != null) {
            if(i == len)
                buf.append(value.charAt(len));
            return buf.toString();
        }
        return value;
    }

    public static String escapeXml(String value) {
        if (isEmpty(value)) {
            return value;
        }
        int len = value.length();
        StringBuilder buf = null;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            switch (ch) {
            case '<':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&lt;");
                break;
            case '>':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&gt;");
                break;
            case '\"':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&quot;");
                break;
            case '\'':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&apos;");
                break;
            case '&':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&amp;");
                break;
            case 160:
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&#160;");
                break;
            default:
                if (buf != null) {
                    buf.append(ch);
                }
                break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String escapeXmlValue(String value) {
        if (isEmpty(value)) {
            return value;
        }
        int len = value.length();
        StringBuilder buf = null;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            switch (ch) {
            case '<':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&lt;");
                break;
            case '>':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&gt;");
                break;
            case '&':
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&amp;");
                break;
            case 160:
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(value.substring(0, i));
                    }
                }
                buf.append("&#160;");
                break;
            default:
                if (buf != null) {
                    buf.append(ch);
                }
                break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    static int hex2int(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else {
            return -1;
        }
    }

    static final int _unescapeXml(char c2, char c3, char c4, char c5) {
        if (c2 >= '0' && c2 <= '9') {
            int c = c2 - '0';
            if (c3 >= '0' && c3 <= '9') {
                c = c * 10 + (c3 - '0');
                if (c4 >= '0' && c4 <= '9') {
                    c = c * 10 + (c4 - '0');
                    if (c5 == ';')
                        return c;
                }
            }
        }
        return -1;
    }

    public static String unescapeXml(String value) {
        if (isEmpty(value)) {
            return value;
        }
        StringBuilder buf = null;
        int len = value.length();
        int len3 = len - 3;
        int len4 = len - 4;
        int len5 = len - 5;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch == '&' && i < len3) {
                int j = i;
                char ch1 = value.charAt(i + 1);
                switch (ch1) {
                case 'l':
                    if (value.charAt(i + 2) == 't'
                            && value.charAt(i + 3) == ';') {
                        i += 3;
                        if (buf == null) {
                            buf = new StringBuilder(len3);
                            if (j > 0) {
                                buf.append(value.substring(0, j));
                            }
                        }
                        buf.append('<');
                    } else if (buf != null) {
                        buf.append('&');
                    }
                    break;
                case 'g':
                    if (value.charAt(i + 2) == 't'
                            && value.charAt(i + 3) == ';') {
                        i += 3;
                        if (buf == null) {
                            buf = new StringBuilder(len3);
                            if (j > 0) {
                                buf.append(value.substring(0, j));
                            }
                        }
                        buf.append('>');
                    } else if (buf != null) {
                        buf.append('&');
                    }
                    break;
                case 'a':
                    if (i < len4 && value.charAt(i + 2) == 'm'
                            && value.charAt(i + 3) == 'p'
                            && value.charAt(i + 4) == ';') {
                        i += 4;
                        if (buf == null) {
                            buf = new StringBuilder(len4);
                            if (j > 0) {
                                buf.append(value.substring(0, j));
                            }
                        }
                        buf.append('&');
                    } else if (i < len5 && value.charAt(i + 2) == 'p'
                            && value.charAt(i + 3) == 'o'
                            && value.charAt(i + 4) == 's'
                            && value.charAt(i + 5) == ';') {
                        i += 5;
                        if (buf == null) {
                            buf = new StringBuilder(len5);
                            if (j > 0) {
                                buf.append(value.substring(0, j));
                            }
                        }
                        buf.append('\'');
                    } else if (buf != null) {
                        buf.append('&');
                    }
                    break;
                case 'q':
                    if (i < len5 && value.charAt(i + 2) == 'u'
                            && value.charAt(i + 3) == 'o'
                            && value.charAt(i + 4) == 't'
                            && value.charAt(i + 5) == ';') {
                        i += 5;
                        if (buf == null) {
                            buf = new StringBuilder(len5);
                            if (j > 0) {
                                buf.append(value.substring(0, j));
                            }
                        }
                        buf.append('\"');
                    } else if (buf != null) {
                        buf.append('&');
                    }
                    break;
                case '#':
                    if (i < len5) {
                        int cc = _unescapeXml(value.charAt(i + 2),
                                value.charAt(i + 3), value.charAt(i + 4),
                                value.charAt(i + 5));
                        if (cc >= 0) {
                            i += 5;
                            if (buf == null) {
                                buf = new StringBuilder(len5);
                                if (j > 0) {
                                    buf.append(value.substring(0, j));
                                }
                            }
                            buf.append((char) cc);
                        } else if (buf != null) {
                            buf.append('&');
                        }
                    } else if (buf != null) {
                        buf.append('&');
                    }
                    break;
                default:
                    if (buf != null) {
                        buf.append('&');
                    }
                    break;
                }
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    /**
     * Builds a string consisting of all characters between 'start' and 'end'.
     * Both the start and end characters are included in the string.
     * 
     * @param start
     *            the starting character
     * @param end
     *            the ending character
     * @return a string consisting of all the specified characters.
     **/
    public static String charRange(char start, char end) {
        if (start <= end) {
            int len = end - start + 1;
            char[] buf = new char[len];

            for (int i = 0; i < len; i++) {
                buf[i] = (char) (start + i);
            }
            return new String(buf);
        } else {
            return "";
        }
    }

    /**
     * Reverses the sequence of characters contained in the string.
     * 
     * @param s
     *            the original string
     * @return a string with the characters from the original string reversed.
     * @see java.lang.StringBuilder#reverse()
     */
    public static String reverse(String s) {
        if (s == null)
            return null;
        StringBuilder buf = new StringBuilder(s);
        buf.reverse();
        return buf.toString();
    }

    /**
     * Centers the string within a string of the specified length, padding it
     * with blanks.
     * 
     * @param str
     *            the original string
     * @param len
     *            the length of the new string
     * @return a string with the original string centered in it.
     */
    public static String center(String str, int len) {
        return center(str, len, ' ');
    }

    /**
     * Centers the string within a string of the specified length, padding it
     * with the specified character.
     * 
     * @param str
     *            the original string
     * @param len
     *            the length of the new string
     * @param pad
     *            the pad character
     * 
     * @return a string with the original string centered in it.
     */
    public static String center(String str, int len, char pad) {
        int start;
        int ls = str.length();
        if (len < 1) {
            return "";
        }

        char[] buf = new char[len];
        for (int i = 0; i < len; i++) {
            buf[i] = pad;
        }

        if (len > ls) {
            start = (len - ls) / 2;
            str.getChars(0, ls, buf, start);
        } else {
            start = (ls - len) / 2;
            str.getChars(start, start + len, buf, 0);
        }

        return new String(buf);
    }

    /**
     * limit the length of a string, for example: limitLen("abcde",4) --> a...
     * 
     * @param str
     * @param maxLen
     *            maximum length of the string
     * @return resultant string
     */
    public static String limitLen(String str, int maxLen) {
        if (str == null || maxLen <= 0)
            return "";
        if (str.length() <= maxLen)
            return str;
        int n = maxLen <= 3 ? 1 : maxLen;
        return str.substring(0, n) + "...";
    }

    public static String safeString(Object obj) {
        if (obj == null)
            return "";
        return obj.toString();
    }

    public static boolean isEquals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str1.equals(str2);
        }
    }

    /**
     * <p>
     * Checks whether the String a valid Java number. Valid numbers include
     * hexadecimal marked with the "0x" qualifier, scientific notation and
     * numbers marked with a type qualifier (e.g. 123L).
     * </p>
     * <p>
     * Null and blank string will return false.
     * </p>
     * 
     * @param str
     *            the string to check
     * @return true if the string is a correctly formatted number
     */
    public static boolean isNumber(String str) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // Deal with any possible sign up front
        int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1) {
            if (chars[start] == '0' && chars[start + 1] == 'x') {
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // Checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // Don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // Loop to the next to last char or to the last char if we need another
        // digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // Two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // We've already taken care of hex.
                if (hasExp) {
                    // Two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // We need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // No type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // Can't have an E at the last byte
                return false;
            }
            if (!allowSigns
                    && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                // Not allowing L with an exponoent
                return foundDigit && !hasExp;
            }
        }
        // allowSigns is true iff the val ends in 'E'
        // Found digit it to make sure weird stuff like '.' and '1E-' doesn't
        // pass
        return !allowSigns && foundDigit;
    }

    // must handle Long, Float, Integer, Float, Short,
    // BigDecimal, BigInteger and Byte
    // useful methods:
    // Byte.decode(String)
    // Byte.valueOf(String,int radix)
    // Byte.valueOf(String)
    // Double.valueOf(String)
    // Float.valueOf(String)
    // new Float(String)
    // Integer.valueOf(String,int radix)
    // Integer.valueOf(String)
    // Integer.decode(String)
    // Integer.getInteger(String)
    // Integer.getInteger(String,int val)
    // Integer.getInteger(String,Integer val)
    // new Integer(String)
    // new Double(String)
    // new Byte(String)
    // new Long(String)
    // Long.getLong(String)
    // Long.getLong(String,int)
    // Long.getLong(String,Integer)
    // Long.valueOf(String,int)
    // Long.valueOf(String)
    // new Short(String)
    // Short.decode(String)
    // Short.valueOf(String,int)
    // Short.valueOf(String)
    // new BigDecimal(String)
    // new BigInteger(String)
    // new BigInteger(String,int radix)
    // Possible inputs:
    // 45 45.5 45E7 4.5E7 Hex Oct Binary xxxF xxxD xxxf xxxd
    // plus minus everything. Prolly more. A lot are not separable.

    /**
     * <p>
     * Turns a string value into a java.lang.Number. First, the value is
     * examined for a type qualifier on the end (
     * <code>'f','F','d','D','l','L'</code>). If it is found, it starts trying
     * to create succissively larger types from the type specified until one is
     * found that can hold the value.
     * </p>
     * <p>
     * If a type specifier is not found, it will check for a decimal point and
     * then try successively larger types from Integer to BigInteger and from
     * Float to BigDecimal.
     * </p>
     * <p>
     * If the string starts with "0x" or "-0x", it will be interpreted as a
     * hexadecimal integer. Values with leading 0's will not be interpreted as
     * octal.
     * </p>
     * 
     * @param val
     *            String containing a number
     * @return Number created from the string
     * @throws NumberFormatException
     *             if the value cannot be converted
     */
    public static Number parseNumber(String val) {
        if(val == null)
            return null;
        if(val.length() == 0)
            throw Exceptions.code("util.err_empty_string_not_number"); 
        if (val.startsWith("--")) {
            // this is protection for poorness in java.lang.BigDecimal.
            // it accepts this as a legal value, but it does not appear
            // to be in specification of class. OS X Java parses it to
            // a wrong value.
            return null;
        }
        if (val.startsWith("0x") || val.startsWith("-0x")) {
            return Integer.decode(val);
        }
        char lastChar = val.charAt(val.length() - 1);
        String mant;
        String dec;
        String exp;
        int decPos = val.indexOf('.');
        int expPos = val.indexOf('e') + val.indexOf('E') + 1;

        if (decPos > -1) {

            if (expPos > -1) {
                if (expPos < decPos)
                    throw Exceptions.code("util.err_not_valid_number"); 
                dec = val.substring(decPos + 1, expPos);
            } else {
                dec = val.substring(decPos + 1);
            }
            mant = val.substring(0, decPos);
        } else {
            if (expPos > -1) {
                mant = val.substring(0, expPos);
            } else {
                mant = val;
            }
            dec = null;
        }
        if (!Character.isDigit(lastChar)) {
            if (expPos > -1 && expPos < val.length() - 1) {
                exp = val.substring(expPos + 1, val.length() - 1);
            } else {
                exp = null;
            }
            // Requesting a specific type..
            String numeric = val.substring(0, val.length() - 1);
            boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
            switch (lastChar) {
            case 'l':
            case 'L':
                if (dec == null
                        && exp == null
                        && isDigits(numeric.substring(1))
                        && (numeric.charAt(0) == '-' || Character
                                .isDigit(numeric.charAt(0)))) {
                    try {
                        return Long.valueOf(numeric);
                    } catch (NumberFormatException nfe) {
                        // Too big for a long
                    }
                    return new BigInteger(numeric);

                }
                throw Exceptions.code("util.err_not_valid_number"); // (val
                                                                                // +
                                                                                // " is not a valid number.");
            case 'f':
            case 'F':
                try {
                    Float f = Float.valueOf(numeric);
                    if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                        // If it's too big for a float or the float value = 0
                        // and the string
                        // has non-zeros in it, then float doens't have the
                        // presision we want
                        return f;
                    }

                } catch (NumberFormatException nfe) {
                }
                // Fall through
            case 'd':
            case 'D':
                try {
                    Double d = Double.valueOf(numeric);
                    if (!(d.isInfinite() || (d.floatValue() == 0.0D && !allZeros))) {
                        return d;
                    }
                } catch (NumberFormatException nfe) {
                }
                try {
                    return new BigDecimal(numeric);
                } catch (NumberFormatException e) {
                }
                // Fall through
            default:
              return 0;

            }
        } else {
            // User doesn't have a preference on the return type, so let's start
            // small and go from there...
            if (expPos > -1 && expPos < val.length() - 1) {
                exp = val.substring(expPos + 1, val.length());
            } else {
                exp = null;
            }
            if (dec == null && exp == null) {
                // Must be an int,long,bigint
                try {
                    return Integer.decode(val);
                } catch (NumberFormatException nfe) {
                }
                try {
                    return Long.valueOf(val);
                } catch (NumberFormatException nfe) {
                }
                return new BigInteger(val);

            } else {
                // Must be a float,double,BigDec
                boolean allZeros = isAllZeros(mant) && isAllZeros(exp);
                try {
                    Float f = Float.valueOf(val);
                    if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                        return f;
                    }
                } catch (NumberFormatException nfe) {
                }
                try {
                    Double d = Double.valueOf(val);
                    if (!(d.isInfinite() || (d.doubleValue() == 0.0D && !allZeros))) {
                        return d;
                    }
                } catch (NumberFormatException nfe) {
                }

                return new BigDecimal(val);

            }

        }
    }

    /**
     * @param s
     *            the String to check
     * @return if it is all zeros or null
     */
    static boolean isAllZeros(String s) {
        if (s == null) {
            return true;
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                return false;
            }
        }
        return s.length() > 0;
    }

    /**
     * Find the earliest index of any of a set of potential substrings. Null
     * string will return -1.
     * 
     * @param str
     *            the string to check
     * @param searchStrs
     *            the strings to search for
     * @return the earliest index of any of the strings
     * @throws NullPointerException
     *             if any of searchStrs[i] is null
     */
    public static int indexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }
        int sz = searchStrs.length;

        // String's can't have a MAX_VALUEth index.
        int ret = Integer.MAX_VALUE;

        int tmp = 0;
        for (int i = 0; i < sz; i++) {
            tmp = str.indexOf(searchStrs[i]);
            if (tmp == -1) {
                continue;
            }

            if (tmp < ret) {
                ret = tmp;
            }
        }

        return (ret == Integer.MAX_VALUE) ? -1 : ret;
    }

    /**
     * Find the latest index of any of a set of potential substrings. Null
     * string will return -1.
     * 
     * @param str
     *            the string to check
     * @param searchStrs
     *            the strings to search for
     * @return the last index of any of the strings
     * @throws NullPointerException
     *             if any of searchStrs[i] is null
     */
    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }
        int sz = searchStrs.length;
        int ret = -1;
        int tmp = 0;
        for (int i = 0; i < sz; i++) {
            tmp = str.lastIndexOf(searchStrs[i]);
            if (tmp > ret) {
                ret = tmp;
            }
        }
        return ret;
    }

    /**
     * Remove the last newline, and everything after it from a String.
     * 
     * @param str
     *            String to chomp the newline from
     * @return String without chomped newline
     * @throws NullPointerException
     *             if str is null
     */
    public static String chomp(String str) {
        return chomp(str, "edu.thu");
    }

    /**
     * Remove the last value of a supplied String, and everything after it from
     * a String.
     * 
     * @param str
     *            String to chomp from
     * @param sep
     *            String to chomp
     * @return String without chomped ending
     * @throws NullPointerException
     *             if str or sep is null
     */
    public static String chomp(String str, String sep) {
        int idx = str.lastIndexOf(sep);
        if (idx != -1) {
            return str.substring(0, idx);
        } else {
            return str;
        }
    }

    /**
     * Remove a newline if and only if it is at the end of the supplied string.
     * 
     * @param str
     *            String to chomp from
     * @return String without chomped ending
     * @throws NullPointerException
     *             if str is null
     */
    public static String chompLast(String str) {
        return chompLast(str, "edu.thu");
    }

    /**
     * Remove a value if and only if the string ends with that value.
     * 
     * @param str
     *            String to chomp from
     * @param sep
     *            String to chomp
     * @return String without chomped ending
     * @throws NullPointerException
     *             if str or sep is null
     */
    public static String chompLast(String str, String sep) {
        if (str.length() == 0) {
            return str;
        }
        String sub = str.substring(str.length() - sep.length());
        if (sep.equals(sub)) {
            return str.substring(0, str.length() - sep.length());
        } else {
            return str;
        }
    }

    /**
     * Remove everything and return the last value of a supplied String, and
     * everything after it from a String.
     * 
     * @param str
     *            String to chomp from
     * @param sep
     *            String to chomp
     * @return String chomped
     * @throws NullPointerException
     *             if str or sep is null
     */
    public static String getChomp(String str, String sep) {
        int idx = str.lastIndexOf(sep);
        if (idx == str.length() - sep.length()) {
            return sep;
        } else if (idx != -1) {
            return str.substring(idx);
        } else {
            return "";
        }
    }

    /**
     * Remove the first value of a supplied String, and everything before it
     * from a String.
     * 
     * @param str
     *            String to chomp from
     * @param sep
     *            String to chomp
     * @return String without chomped beginning
     * @throws NullPointerException
     *             if str or sep is null
     */
    public static String prechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        if (idx != -1) {
            return str.substring(idx + sep.length());
        } else {
            return str;
        }
    }

    /**
     * Remove and return everything before the first value of a supplied String
     * from another String.
     * 
     * @param str
     *            String to chomp from
     * @param sep
     *            String to chomp
     * @return String prechomped
     * @throws NullPointerException
     *             if str or sep is null
     */
    public static String getPrechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        if (idx != -1) {
            return str.substring(0, idx + sep.length());
        } else {
            return "";
        }
    }

    /**
     * Remove the last character from a String. If the String ends in \r\n, then
     * remove both of them.
     * 
     * @param str
     *            String to chop last character from
     * @return String without last character
     * @throws NullPointerException
     *             if str is null
     */
    public static String chop(String str) {
        if ("".equals(str)) {
            return "";
        }
        if (str.length() == 1) {
            return "";
        }
        int lastIdx = str.length() - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);
        if (last == '\n') {
            if (ret.charAt(lastIdx - 1) == '\r') {
                return ret.substring(0, lastIdx - 1);
            }
        }
        return ret;
    }

    /**
     * Remove edu.thu from end of a String if it's there. If a \r precedes it,
     * then remove that too.
     * 
     * @param str
     *            String to chop a newline from
     * @return String without newline
     * @throws NullPointerException
     *             if str is null
     */
    public static String chopNewline(String str) {
        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);
        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r') {
                lastIdx--;
            }
        } else {
            lastIdx++;
        }
        return str.substring(0, lastIdx);
    }

    /**
     * Repeat a string n times to form a new string.
     * 
     * @param str
     *            String to repeat
     * @param repeat
     *            int number of times to repeat
     * @return String with repeated string
     * @throws NegativeArraySizeException
     *             if repeat < 0
     * @throws NullPointerException
     *             if str is null
     */
    public static String repeat(String str, int repeat) {
        StringBuilder buffer = new StringBuilder(repeat * str.length());
        for (int i = 0; i < repeat; i++) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * Capitalise all the words in a string. Uses Character.isWhitespace as a
     * separator between words. Null will return null.
     * 
     * @param str
     *            the string to capitalise
     * @return capitalised string
     */
    public static String capitaliseAllWords(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuilder buffer = new StringBuilder(sz);
        boolean space = true;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (Character.isWhitespace(ch)) {
                buffer.append(ch);
                space = true;
            } else if (space) {
                buffer.append(Character.toTitleCase(ch));
                space = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Get the String that is nested in between two instances of the same
     * String. If str is null, will return null
     * 
     * @param str
     *            the string containing nested-string
     * @param tag
     *            the string before and after nested-string
     * @return the string that was nested, or null
     * @throws NullPointerException
     *             if tag is null
     */
    public static String getNestedString(String str, String tag) {
        return getNestedString(str, tag, tag);
    }

    /**
     * Get the string that is nested in between two strings.
     * 
     * @param str
     *            the string containing nested-string
     * @param open
     *            the string before nested-string
     * @param close
     *            the string after nested-string
     * @return the string that was nested, or null
     * @throws NullPointerException
     *             if open or close is null
     */
    public static String getNestedString(String str, String open, String close) {
        if (str == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * How many times is the substring in the larger string. Null returns 0.
     * 
     * @param str
     *            the string to check
     * @param sub
     *            the substring to count
     * @return the number of occurances, 0 if the string is null
     * @throws NullPointerException
     *             if sub is null
     */
    public static int countMatches(String str, String sub) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * Reverses a string that is delimited by a specific character. The strings
     * between the delimiters are not reversed. Thus java.lang.String becomes
     * String.lang.java (if the delimiter is '.').
     * 
     * @param str
     *            the string to reverse
     * @param delimiter
     *            the delimiter to use
     * @return the reversed string
     */
    public static String reverseDelimitedString(String str, String delimiter) {
        // could implement manually, but simple way is to reuse other,
        // probably slower, methods.
        String[] strs = splitOn(str, delimiter);
        reverseArray(strs);
        return joinArray(strs, delimiter);
    }

    /**
     * Reverses an array. TAKEN FROM CollectionsUtils.
     * 
     * @param array
     *            the array to reverse
     */
    private static void reverseArray(Object[] array) {
        int i = 0;
        int j = array.length - 1;
        Object tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * Find the Levenshtein distance between two strings. This is the number of
     * changes needed to change one string into another. Where each change is a
     * single character modification.
     * 
     * This implemmentation of the levenshtein distance algorithm is from
     * http://www.merriampark.com/ld.htm
     * 
     * @param s
     *            the first String
     * @param t
     *            the second String
     * @return int result distance
     * @throws NullPointerException
     *             if s or t is null
     */
    public static int getLevenshteinDistance(String s, String t) {
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1
        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);

            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);

                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // Step 6
                int a = d[i - 1][j] + 1;
                int b = d[i][j - 1] + 1;
                int c = d[i - 1][j - 1] + cost;
                if (b < a) {
                    a = b;
                }
                if (c < a) {
                    a = c;
                }
                d[i][j] = a;
            }
        }

        // Step 7
        return d[n][m];
    }

    /**
     * Checks if the String contains only certain chars.
     * 
     * @param str
     *            the String to check
     * @param valid
     *            an array of valid chars
     * @return true if it only contains valid chars and is non-null
     */
    public static boolean containsOnly(String str, char[] valid) {
        if (str == null || valid == null) {
            return false;
        }

        int strSize = str.length();
        int validSize = valid.length;

        for (int i = 0; i < strSize; i++) {
            boolean contains = false;
            for (int j = 0; j < validSize; j++) {
                if (valid[j] == str.charAt(i)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                return false;
            }
        }

        return true;
    }

    /**
     * checks if the string does not contain anly invalid chars
     */
    public static boolean notContains(String str, char[] invalid) {
        if (str == null || invalid == null)
            return true;

        int i, n = invalid.length;
        for (i = 0; i < n; i++) {
            if (str.indexOf(invalid[i]) >= 0)
                return false;
        }
        return true;
    }

    public static Map<String, String> stringToMap(String value, String gap,
            String delimiter) {
        Map<String, String> map = new java.util.LinkedHashMap<String, String>();
        if (value != null) {
            String key;
            String val;
            StringTokenizer st = new StringTokenizer(value, delimiter);
            while (st.hasMoreTokens()) {
                key = st.nextToken(gap);
                val = st.nextToken(delimiter);
                val = StringUtils.strip(val.substring(1));
                val = StringUtils.stripBound(val, "\"");
                key = StringUtils.strip(key);
                if (key.startsWith(delimiter))
                    key = key.substring(delimiter.length());
                key = StringUtils.stripBound(key, "\"");
                map.put(key, val);
            }
        }
        return map;
    }

    public static Map<String, String> stringToMap(String value) {
        return stringToMap(value, "=", " \t\n\r\f");
    }

    public static String mapToString(Map<String, Object> map, String gap,
            String delimiter) {
        StringBuilder buf = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            buf.append(entry.getKey()).append(gap).append(entry.getValue());
            if (it.hasNext())
                buf.append(delimiter);
        }
        return buf.toString();
    }

    public static String mapToString(Map<String, Object> map) {
        return mapToString(map, "=", ",");
    }

    public static boolean isBits(String s) {

        if (s == null)
            return true;

        int i, n = s.length();
        for (i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c != '1' && c != '0')
                return false;
        }

        return true;
    }

    public static String bitsOr(String a, String b) {
        int aLen, bLen;
        if (a == null)
            return b;

        aLen = a.length();

        if (b == null)
            return a;

        bLen = b.length();

        // assure aLen >= bLen
        if (aLen < bLen) {
            String tmpS = a;
            a = b;
            b = tmpS;
            int tmpL = aLen;
            aLen = bLen;
            bLen = tmpL;
        }

        char[] buf = a.toCharArray();

        for (int i = 0; i < bLen; i++) {
            if (b.charAt(i) == '1')
                buf[i] = '1';
        }

        return new String(buf);
    }

    public static String bitsAnd(String a, String b) {
        check(isBits(a));
        check(isBits(b));

        int i, aLen, bLen;
        if (a == null)
            return null;
        aLen = a.length();

        if (b == null)
            return null;
        bLen = b.length();

        if (aLen < bLen) {
            String tmpS = a;
            a = b;
            b = tmpS;
            int tmpL = aLen;
            aLen = bLen;
            bLen = tmpL;
        }

        char[] buf = a.toCharArray();

        for (i = 0; i < bLen; i++) {
            if (b.charAt(i) == '0')
                buf[i] = '0';
        }

        // not matched bits are set to 0
        for (; i < aLen; i++)
            buf[i] = '0';

        return new String(buf);
    }

    public static String bitsNot(String a) {
        check(isBits(a));

        if (a == null)
            return null;

        char[] buf = a.toCharArray();
        int i, n = buf.length;
        for (i = 0; i < n; i++) {
            if (buf[i] == '0')
                buf[i] = '1';
            else
                buf[i] = '0';
        }

        return new String(buf);
    }

    public static String toHex(byte[] binaryData) {
        if (binaryData == null)
            return null;

        int n = binaryData.length;
        char[] buffer = new char[binaryData.length * 2];

        for (int i = 0; i < n; i++) {
            int low = (int) (binaryData[i] & 0x0f);
            int high = (int) ((binaryData[i] & 0xf0) >> 4);
            buffer[i * 2] = gsN2H[high];
            buffer[i * 2 + 1] = gsN2H[low];
        }

        return new String(buffer);
    }

    public static String toUnicode(String s) {
        StringBuilder buf = new StringBuilder(s.length());
        char ca[] = s.toCharArray();
        for (int i = 0; i < ca.length; i++)
            toUnicode(buf, ca[i]);

        return buf.toString();
    }

    public static String toUnicode(char c) {
        return toUnicode(new StringBuilder(6), c);
    }

    private static String toUnicode(StringBuilder buf, char c) {
        buf.append("\\u");
        int ci = c;
        for (int i = 0; i < gsHI.length; i++)
            if (ci > gsHI[i]) {
                int d = ci / gsHI[i];
                buf.append(gsN2H[d]);
                ci %= gsHI[i];
            } else {
                buf.append('0');
            }

        buf.append(gsN2H[ci]);
        return buf.toString();
    }

    public static String remove(String str, char removeChar) {
        if (str == null)
            return null;
        StringBuilder buf = new StringBuilder(str.length());
        char c[] = str.toCharArray();
        int start = 0;
        for (int i = 0; i < c.length; i++)
            if (c[i] == removeChar) {
                buf.append(str.substring(start, i));
                start = i + 1;
            }

        buf.append(str.substring(start));
        return buf.toString();
    }

    public static String removeBetween(String str, String startMarker,
            String endMarker) {
        if (str == null)
            return null;
        int startIdx = str.indexOf(startMarker);
        if (startIdx < 0)
            return str;
        int endIdx = str.indexOf(endMarker, startIdx);
        if (endIdx < 0)
            return str;
        else
            return str.substring(startIdx, endIdx + endMarker.length());
    }

    /**
     * Makes sure that the POSTed data is conforms to certain rules. These rules
     * are:
     * <UL>
     * <LI>The data always ends with a newline (some browsers, such as NS4.x
     * series, does not send a newline at the end, which makes the diffs a bit
     * strange sometimes.
     * <LI>The CR/LF/CRLF mess is normalized to plain CRLF.
     * </UL>
     * 
     * The reason why we're using CRLF is that most browser already return CRLF
     * since that is the closest thing to a HTTP standard.
     */
    public static String normalizeCRLF(String str) {
        StringBuilder sb = new StringBuilder();
        int n = str.length();

        for (int i = 0; i < n; i++) {
            switch (str.charAt(i)) {
            case 0x0a: // LF, UNIX
                sb.append("\r\n");
                break;

            case 0x0d: // CR, either Mac or MSDOS
                sb.append("\r\n");
                // If it's MSDOS, skip the LF so that we don't add it again.
                if (i < n - 1 && str.charAt(i + 1) == 0x0a) {
                    i++;
                }
                break;

            default:
                sb.append(str.charAt(i));
                break;
            }
        }

        return sb.toString();
    }

    /*
     * public static String toHexString(byte[] bs) { if (bs == null) return
     * null; StringBuilder sb = new StringBuilder(); for (int i = 0; i <
     * bs.length; i++) { int b = (int) bs[i]; if (b < 0) { b += 256; } if (0 <=
     * b && b < 10) { sb.append("0"); } sb.append(Integer.toHexString(b)); }
     * return sb.toString(); }
     */

    public static final String SPACE = " ";

    public static final int tabSize = 2;

    public static final String APPEND_CHAR = "..";

    public static final int FETCH_CHR_LEN = 7;

    public static String limitByteLen(String s, int maxLen) {
        // tab����Ϊ�ո�
        // s = s.replaceAll("\t", gernerateSpace(tabSize));
        // int stringLength = s.length();
        int k = 0;
        int byteLen = getByteLen(s);
        if (byteLen <= maxLen) {
            return s;
        }
        StringBuilder buf = new StringBuilder(maxLen);
        for (int indx = 0; indx < maxLen; indx++) {
            char character = s.charAt(indx);
            if (character < 0 || character > 255) {
                k = k + 2;
                buf.append(character);
            } else {
                k = k + 1;
                buf.append(character);
            }
            if (k == FETCH_CHR_LEN) {
                buf.append(APPEND_CHAR).append(".");
                break;
            }
            if (k == FETCH_CHR_LEN + 1) {
                buf.append(APPEND_CHAR);
                break;
            }
        }
        return buf.toString();
    }

    protected static String gernerateSpace(int i) {
        StringBuilder buf = new StringBuilder(i);
        for (int x = 0; x < i; x++) {
            buf.append(SPACE);
        }
        return buf.toString();
    }

    public static int getByteLen(String s) {
        int k = 0;
        int stringLength = s.length();
        for (int indx = 0; indx < stringLength; indx++) {
            char character = s.charAt(indx);
            if (character < 0 || character > 255) {
                k += 1;
            }
        }
        k += stringLength;
        return k;
    }
    
    public static Map<String,Object> parseMap(String text){
        if(text == null || text.isEmpty())
            return new LinkedHashMap<String,Object>(0);
        return null; //new StrMapParser().parse(text);
    }
    
    public static String formatMoney(double d){
    	String str = subDouble(d);
    	return str;
    }
    
    static void check(Object value) {
        /*boolean b = MathEx.booleanValue(value, false);
        if (!b)
            throw Exceptions.code("util.err_invalid").param(value);*/
    }

    static void trace(Object o) {
        System.out.println(o);
    }

    static void check(Object value, Object data) {
        trace(data);
        check(value);
    }
    
    public static String generateUUID(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
   
	
	public static void mergeObject(Object source,Object dest){
		PropertyDescriptor[] pds = ReflectUtils.getBeanProperties(source.getClass());
		for(int i=0; i<pds.length; i++){
			Method rm = pds[i].getReadMethod();
			Method wm = pds[i].getWriteMethod();
			try {
				Object value = rm.invoke(source, null);
				if(value!=null && wm!=null){
					wm.invoke(dest, value);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static boolean generateImage(String imgStr,File f)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            OutputStream out = new FileOutputStream(f);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  
	
}