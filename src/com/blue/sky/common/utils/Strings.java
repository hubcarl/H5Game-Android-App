package com.blue.sky.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings
{
	public final static String EMPTY_STRING = "";

	public static boolean isEmpty(String str)
	{
		return (str == null) || (str.length() == 0) || "null".equals(str);
	}

	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}

	public static boolean isNumeric(String str)
	{
		if (isEmpty(str))
		{
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++)
		{
			if (Character.isDigit(str.charAt(i)) == false)
			{
				return false;
			}
		}
		return true;
	}

	public static String trim(String str)
	{
		return str == null ? null : str.trim();
	}

	public static String trimToEmpty(String str)
	{
		return str == null ? "" : str.trim();
	}

	public static String trimToNull(String str)
	{
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	public static String toString(int number)
	{
		return String.valueOf(number);
	}

	public static boolean checkEmail(String email)
	{
		String regex = "^\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	public static boolean isContainSpecialChar(String inputText)
	{
		if (Strings.isEmpty(inputText))
		{
			return false;
		}
		String specialChar = "'|and|exec|drop|insert|select|delete|update|count|*|%|truncate|char|declare|;|or|-|+|,";

		String[] specialCharArray = specialChar.split("\\|");
		for (int i = 0; i < specialCharArray.length; i++)
		{
			if (inputText.toLowerCase().indexOf(specialCharArray[i]) >= 0)
			{
				return true;
			}
		}
		return false;
	}

	public static String filterSpecialChar(String inputText)
	{

		if (Strings.isEmpty(inputText))
		{
			return Strings.EMPTY_STRING;
		}
		String specialChar = "'|and |exec|drop|insert|select|delete|update|count|*|%|truncate|char|declare|;|or|-|+|,";

		String[] specialCharArray = specialChar.split("\\|");
		for (int i = 0; i < specialCharArray.length; i++)
		{
			inputText = inputText.replace(specialCharArray[i], EMPTY_STRING);
		}
		return inputText.trim();
	}

    private final static Pattern EMAILE = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断是不是一个合法的电子邮件地址
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (isEmpty(email))
            return false;

        return EMAILE.matcher(email).matches();
    }

    public static boolean isMobile(String mobile) {
		/*
		 * Pattern p =
		 * Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		 * Matcher m = p.matcher(mobile); return m.matches();
		 */
        // return mobile.matches("^\\d{11}$");
        return mobile.matches("^(13|14|15|18)\\d{9}$"); // 不做太严格的判断
    }

    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = inputString;
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            //
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

}
