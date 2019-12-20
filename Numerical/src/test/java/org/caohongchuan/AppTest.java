package org.caohongchuan;

import static org.junit.Assert.assertTrue;

import org.caohongchuan.Root.Root;
import org.junit.Test;

import tech.tablesaw.api.*;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;

import tech.tablesaw.plotly.api.ScatterPlot;



import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void tableSawTest6() {
        String[] students = {"小明", "李雷", "小二"};
        double[] scores = {90.1, 84.3, 99.7};
        Table table = Table.create("学生分数统计表").addColumns(
                StringColumn.create("姓名", students),
                DoubleColumn.create("分数", scores));
        System.out.println(table.print());
    }

    @Test
    public void root2Test(){
        double a=1, b=2, c=1;
        double[] res = Root.getRoot(a, b, c);
        for(double xx : res){
            System.out.printf("%f\n", xx);
        }

    }

    public static void f1(double[] arr,int index){
        arr[index] = 1.0/2 * arr[index-1];
    }

    public static void f2(double[] arr, int index){
        arr[index] = 3.0/2 * arr[index-1] - 1.0/2 * arr[index-2];
    }

    public static void f3(double[] arr, int index){
        arr[index] = 5.0/2 * arr[index-1] - 1.0*arr[index-2];
    }

    @Test
    public void errorTest(){
        double[] xx = new double[10];
        double[] rr = new double[10];
        double[] pp = new double[10];
        double[] qq = new double[10];
        Arrays.fill(xx, 0.0);
        Arrays.fill(rr, 0.0);
        Arrays.fill(pp, 0.0);
        Arrays.fill(qq, 0.0);

        for(int i=1; i<10; i++){
            xx[0] = 1;
            f1(xx, i);
        }
        for(int i=1; i<10; i++){
            rr[0] = 0.994;
            f1(rr ,i);
        }
        for(int i=2; i<10; i++){
            pp[0] = 1;
            pp[1] = 0.497;
            f2(pp, i);
        }
        for(int i=2; i<10; i++){
            qq[0] = 1;
            qq[1] = 0.497;
            f3(qq, i);
        }

        for(int i=0; i<10; i++){
            System.out.printf("%14f, %14f, %14f, %14f\n", xx[i], rr[i], pp[i], qq[i]);
        }


        //error anaysis
        double[] xr = new double[10];
        double[] xp = new double[10];
        double[] xq = new double[10];
        Arrays.fill(xr, 0.0);
        Arrays.fill(xp, 0.0);
        Arrays.fill(xq, 0.0);

        for(int i=0; i<10; i++){
            xr[i] = xx[i] - rr[i];
            xp[i] = xx[i] - pp[i];
            xq[i] = xx[i] - qq[i];
            System.out.printf("%14f, %14f, %14f\n", xr[i], xp[i], xq[i]);
        }

        int[] index = new int[10];
        for(int i=0; i<10; i++){
            index[i] = i;
        }

        Table table1 = Table.create("xr").addColumns(
                IntColumn.create("index", index),
                DoubleColumn.create("error_xr", xr)
        );
        Table table2 = Table.create("xr").addColumns(
                IntColumn.create("index", index),
                DoubleColumn.create("error_xp", xp)
        );
        Table table3 = Table.create("xr").addColumns(
                IntColumn.create("index", index),
                DoubleColumn.create("error_xq", xq)
        );
        System.out.println(table1);
        Plot.show(ScatterPlot.create("error x - r", table1, "index", "error_xr"));
        Plot.show(ScatterPlot.create("error x - p", table2, "index", "error_xp"));
        Plot.show(ScatterPlot.create("error x - q", table3, "index", "error_xq"));
    }
}
