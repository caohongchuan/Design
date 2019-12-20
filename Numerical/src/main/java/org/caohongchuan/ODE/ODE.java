package org.caohongchuan.ODE;


import tech.tablesaw.api.CategoricalColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;

import javax.sound.sampled.Line;

class Equation{
    public double eq(double t, double y){
        return (1 + y*y);
    }
    public double f(double x, double y, double z, double u){
        return (u*(1-y*y)*z - y);
    }
    public double g(double x, double y, double z){
        return z;
    }
}


public class ODE {

    public static double[] Euler(double a, double b, double init, double step, Equation eq){
        int size = (int)Math.ceil((b-a)/step);
        size +=1;
        double[] res = new double[size];
        res[0] = init;
        int index=0;
        for(double i=a+step; i<b;){
            res[index+1] = res[index] + step*eq.eq(i, res[index]);
            i = i+ step;
            index++;
        }

        return res;
    }

    public static double[] EulerEnhance(double a, double b, double init, double step, Equation eq){
        int size = (int)Math.ceil((b-a)/step);
        size +=1;
        double[] res = new double[size];
        res[0] = init;
        int index=0;
        for(double i=a+step; i<b; ){
            double mid = res[index] + step*eq.eq(i, res[index]);
            res[index+1] = res[index] + step*(eq.eq(i, res[index]) + eq.eq(i, mid))/2;
            i = i + step;
            index++;
        }
        return res;
    }


    public static double[] Runge_Kutta(double a, double b, double init, double step, Equation eq){
        int size = (int)Math.ceil((b-a)/step);
        size +=1;
        double[] res = new double[size];
        res[0] = init;
        int index=1;
        for(double i=a+step; i<=b;){

            double k1 = step*eq.eq(i, res[index-1]);
            double k2 = step*eq.eq(i+step/2, res[index-1]+ k1/2);
            double k3 = step*eq.eq(i+step/2, res[index-1]+ k2/2);
            double k4 = step*eq.eq(i+step, res[index-1]+ k3);

            res[index] = res[index-1] + (k1 + 2*k2 + 2*k3 + k4)/6;

            i = i+ step;
            index++;
        }

        return res;
    }

    public static double[] Runge_Kutta1(double a, double b, double init, double step, Equation eq, double u){
        int size = (int)Math.ceil((b-a)/step);
        size++;

        double[] resy = new double[size];
        double[] resz = new double[size];
        resy[0] = 1;
        resz[0] = 0;

        int index=1;
        for(double i=a+step; i<=b;){

            double k1 = step*eq.f(i, resy[index-1], resz[index-1], u);
            double l1 = step*eq.g(i, resy[index-1], resz[index-1]);
            double k2 = step*eq.f(i+step/2, resy[index-1]+k1/2, resz[index-1]+l1/2, u);
            double l2 = step*eq.g(i+step/2, resy[index-1]+k1/2, resz[index-1]+l1/2);
            double k3 = step*eq.f(i+step/2, resy[index-1]+k2/2, resz[index-1]+l2/2, u);
            double l3 = step*eq.g(i+step/2, resy[index-1]+k2/2, resz[index-1]+l2/2);
            double k4 = step*eq.f(i+step, resy[index-1]+k3, resz[index-1]+l3, u);
            double l4 = step*eq.g(i+step, resy[index-1]+k3, resz[index-1]+l3);

            resz[index] = resz[index-1] + (k1 + 2*k2 + 2*k3 + k4)/6;
            resy[index] = resy[index-1] + (l1 + 2*l2 + 2*l3 + l4)/6;

            i = i+ step;
            index++;
        }
        return resy;
    }


    public static void test1(){
        Equation eq = new Equation();
        double[] xx = EulerEnhance(0,1, 0, 0.1, eq);
        for(int i=0; i<xx.length; i++){
            System.out.printf("%f ",xx[i]);
        }
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        test1();
//        Equation eq = new Equation();
////        double[] xx = Runge_Kutta(0,1, 0, 0.1, eq);
//        double[] xx1 = Runge_Kutta1(0,10, 0, 0.1, eq, 0.01);
//        double[] xx2 = Runge_Kutta1(0,10, 0, 0.1, eq, 0.1);
//        double[] xx3 = Runge_Kutta1(0,10, 0, 0.1, eq, 1);
//        double[] index = new double[101];
//        double start = 0;
//        for(int i=0;i<101;i++){
//            index[i] = start;
//            start += 0.1;
//        }
//
//        System.out.printf("\n---------------------------\n");
//        for(int i=0; i<101; i++){
//            System.out.printf("%f, ", index[i]);
//        }
//        System.out.printf("\n---------------------------\n");
//        for(int i=0; i<101; i++){
//            System.out.printf("%f, ", xx1[i]);
//        }
//        System.out.printf("\n---------------------------\n");
//        for(int i=0; i<101; i++){
//            System.out.printf("%f, ", xx2[i]);
//        }
//        System.out.printf("\n---------------------------\n");
//        for(int i=0; i<101; i++){
//            System.out.printf("%f, ", xx3[i]);
//        }
//
//        Table table1 = Table.create("xr").addColumns(
//                DoubleColumn.create("X", index),
//                DoubleColumn.create("0.01", xx1),
//                DoubleColumn.create("0.1", xx2),
//                DoubleColumn.create("1", xx3)
//        );
//
//        Plot.show(LinePlot.create("FIT", table1,  "X", "0.01"));
//        Plot.show(LinePlot.create("FIT", table1,  "X", "0.1"));
//        Plot.show(LinePlot.create("FIT", table1,  "X", "1"));

    }


}
