package br.com.forja.bits.south.utils;

import java.util.Date;

public class Util {

    private static Util util;


    public static Util getInstance(){
        if(util == null)
            util = new Util();
        return util;
    }


    public Date makeDefaultDate(){
        Date date = new Date();
        return new Date(date.getTime() + 60 * 1000); // soma 1 minutos a data atual
    }

}
