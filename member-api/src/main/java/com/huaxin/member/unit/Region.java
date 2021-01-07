package com.huaxin.member.unit;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DecimalFormat;

/**
 *
 * @author Administrator
 */
public class Region {
    public static void main(String[] args){
        String s ="(50,)";
        //System.out.println( new Region().Get(s));
    }
    public boolean Check(double douD , String s){
        String[] ssplit =s.split(",");
        String left = ssplit[0].substring(1);
        String right = ssplit[1].substring(0,ssplit[1].length()-1);
        double leftd ;
        double rightd ;
        if (s.contains(")")) {         //(0,100)  (,100)  (10,)
            if (right.equals("")) {
                leftd = Double.parseDouble(left);
                if (ssplit[0].contains("(")) {
                    if (leftd<douD) {
                        return true;
                    }
                }else if(ssplit[0].contains("[")){
                    if (leftd<=douD) {
                        return true;
                    }
                }
            }else{
                rightd = Double.parseDouble(right);
                if (s.contains("(")) {
                    if (douD<rightd) {
                        if (left.equals("")) {
                            return true;
                        }else{
                            leftd = Double.parseDouble(left);
                            if (leftd<douD) {
                                return true;
                            }
                        }
                    }
                }else if (s.contains("[")){
                    if (douD<rightd) {
                        leftd = Double.parseDouble(left);
                        if (leftd<=douD) {
                            return true;
                        }
                    }
                }

            }
            return false;
        }else if(s.contains("]")){
            rightd = Double.parseDouble(right);
            if (douD<=rightd) {
                if (left.equals("")) {
                    return true;
                }else{
                    leftd = Double.parseDouble(left);
                    if (ssplit[0].contains("(")) {
                        if (leftd<douD) {
                            return true;
                        }
                    }else if(ssplit[0].contains("[")){
                        if (leftd<=douD) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    public String Get(String s){
        String[] ssplit =s.split(",");
        String left = ssplit[0].substring(1);
        String right = ssplit[1].substring(0,ssplit[1].length()-1);
        double leftd ;
        double rightd ;
        double result=0;
        if (s.contains(")")) {
            if (right.equals("")) {
                leftd = Double.parseDouble(left);
                if (s.contains("(")) {
                    result =leftd + Math.random()*(leftd);
                }else if(s.contains("[")){
                    result =leftd +  Math.random()*leftd;
                }
            }else{
                rightd = Double.parseDouble(right);
                if (s.contains("(")) {
                    if (left.equals("")) {
                        result = Math.random()*(rightd);
                    }else{
                        leftd = Double.parseDouble(left);
                        result = Math.random()*(rightd-leftd)+leftd;
                    }

                }else if(s.contains("[")){
                    leftd = Double.parseDouble(left);
                    result = Math.random()*(rightd-leftd)+leftd;
                }


            }
        }else if(s.contains("]")){
            rightd = Double.parseDouble(right);
            if (left.equals("")) {
                result = rightd - Math.random()*rightd;
            }else{
                leftd= Double.parseDouble(left);
                if (s.contains("(")) {
                    result = leftd +  Math.random()*(rightd-leftd);
                }else if(s.contains("[")){
                    result =leftd +  Math.random()*(rightd-leftd);
                }
            }
        }
        return ""+  new DecimalFormat("0.00").format(result);
    }
}
