package br.com.forja.bits.donation.utils;

public class Util {

    private static Util util;


    public static Util getInstance(){
        if(util == null)
            util = new Util();
        return util;
    }


    public int convertInCredits(double value) {
        return (int) Math.floor(value / 0.1);
    }

}
