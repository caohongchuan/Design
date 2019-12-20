package org.caohongchuan.Root;

public class Root {

    private static double x11(double a, double b, double c){
        return (-b + Math.sqrt(b*b-4*a*c))/(2*a);
    }
    private static double x21(double a, double b, double c){
        return (-b - Math.sqrt(b*b-4*a*c))/(2*a);
    }
    private static double x12(double a, double b, double c){
        return (-2*c)/(b + Math.sqrt(b*b-4*a*c));
    }
    private static double x22(double a, double b, double c){
        return (-2*c)/(b - Math.sqrt(b*b-4*a*c));
    }

    public static double[] getRoot(double a, double b, double c){
        double root1 =0;
        double root2 =0;
        if(b>=0){
            root1 = x12(a, b, c);
            root2 = x21(a, b, c);
        }else{
            root1 = x11(a, b, c);
            root2 = x22(a, b, c);
        }

        //return array
        double[] re = new double[2];
        re[0] = root1;
        re[1] = root2;
        return re;
    }

}
