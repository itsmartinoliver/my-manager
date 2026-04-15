package com.mymanager.util;

import java.util.Random;

public class RandomString {

    public static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static void main(String[] args) {
        RandomString myRandomString = new RandomString();
        String myString = myRandomString.randomString(32);
        System.out.println(myString);
    }

    public String randomString(int length) {
        Random rand = new Random();
        String s = "";
        for (int i = 0; i < length; i++) {
            s = s.concat(Character.toString(
                // Get a random allowed character
                CHARACTER_SET.charAt(rand.nextInt(CHARACTER_SET.length()))
            ));
        }
        return s;
    }
}
