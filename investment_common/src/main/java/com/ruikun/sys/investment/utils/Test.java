package com.ruikun.sys.investment.utils;

public class Test {
    public static void main(String[] args) {
        String aa = "123";
        String bb = "123";
        String cc = new String("123");
        String dd = new String("123");

        System.out.println(aa==bb);
        System.out.println(aa==cc);
        System.out.println(cc==dd);

        int a = 1;
        int b = 1;
        System.out.println(a==b);
    }
}
