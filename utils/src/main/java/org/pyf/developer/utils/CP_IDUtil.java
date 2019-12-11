package org.pyf.developer.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/19 12:27.
 */


public class CP_IDUtil {

    public CP_IDUtil() {
        // TODO Auto-generated constructor stub
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    /**
     * @param args
     */
    /**
     * 生成随即密码
     * @param pwd_len 生成的密码的总长度
     * @return  密码的字符串
     */
    public static String genRandomPassword(int pwd_len){
        //35是因为数组是从0开始的，26个字母+10个数字
        final int  maxNum = 36;
        int i;  //生成的随机数
        int count = 0; //生成的密码的长度
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < pwd_len){
            //生成随机数，取绝对值，防止生成负数，

            i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(Math.abs(r.nextInt(2))==1? StringUtils.upperCase(str[i]+""):str[i]);
                count ++;
            }
        }

        return pwd.toString();
    }
    public static void main(String[] args) {
        for(int i=0;i<100;i++) {
            System.out.println(genRandomPassword(10));
        }
    }
}
