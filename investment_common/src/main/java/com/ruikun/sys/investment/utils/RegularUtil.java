package com.ruikun.sys.investment.utils;

import java.util.regex.Pattern;

public class RegularUtil {

    /**
     * 正整数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        return pattern.matcher(str).matches();
    }

    //判断小数点后2位的数字的正则表达式
    public static boolean isTwoPoints(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        return pattern.matcher(str).matches();
    }

}
