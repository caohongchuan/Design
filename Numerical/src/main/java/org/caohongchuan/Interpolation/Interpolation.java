package org.caohongchuan.Interpolation;

import Jama.Matrix;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class Interpolation {

    public static double[][] CubicSpline(double[] x, double[] y){
        int size = x.length;
        double[][] A = new double[size][size];
        double[] H = new double[size];
        double[] b = new double[size];

        for(int i=0; i<size-1; i++){
            H[i] = x[i+1] - x[i];
        }

        for(int i=1; i<size-1; i++){
            A[i][i-1] = H[i-1];
            A[i][i] = 2* (H[i-1] + H[i]);
            A[i][i+1] = H[i];
        }
        A[0][0] = 1;
        A[size-1][size-1] = 1;

        for(int i=1; i<size-1; i++){
            b[i] = (3/H[i])*(y[i+1]-y[i]) - (3/H[i-1]*(y[i]-y[i-1]));
        }
        b[0] = 0;
        b[size-1] = 0;

        Matrix AA = new Matrix(A);
        Matrix bb = new Matrix(b, size);
        Matrix res = AA.inverse().times(bb);

        double[][] re = new double[4][size];
        re[2] = res.getColumnPackedCopy();
        re[0] = y;

        for(int i=0; i<size-1; i++){
            re[3][i] = (re[2][i+1] - re[2][i])/(3* H[i]);
            re[1][i] = (re[0][i+1]-re[0][i])/H[i] - H[i]*(2*re[2][i]+re[2][i+1])/3;
        }

        for(double[] tt : re){
            for(double xx : tt){
                System.out.printf("%f ",xx);
            }
            System.out.println();
        }

        return re;
    }

    public static double[][] CubicSpline1(double[] x, double[] y, double fa, double fb){
        int size = x.length;
        double[][] A = new double[size][size];
        double[] H = new double[size];
        double[] b = new double[size];

        for(int i=0; i<size-1; i++){
            H[i] = x[i+1] - x[i];
        }

        for(int i=1; i<size-1; i++){
            A[i][i-1] = H[i-1];
            A[i][i] = 2* (H[i-1] + H[i]);
            A[i][i+1] = H[i];
        }
        A[0][0] = 2*H[0];
        A[0][1] = H[0];
        A[size-1][size-2] = H[size-2];
        A[size-1][size-1] = 2*H[size-2];


        for(int i=1; i<size-1; i++){
            b[i] = (3/H[i])*(y[i+1]-y[i]) - (3/H[i-1]*(y[i]-y[i-1]));
        }

        b[0] = 3*(y[1]-y[0])/H[0] - 3*fa;
        b[size-1] = 3*fb - 3*(y[size-1]-y[size-2])/H[size-2];

        Matrix AA = new Matrix(A);
        Matrix bb = new Matrix(b, size);
        Matrix res = AA.inverse().times(bb);

        double[][] re = new double[4][size];
        re[2] = res.getColumnPackedCopy();
        re[0] = y;

        for(int i=0; i<size-1; i++){
            re[3][i] = (re[2][i+1] - re[2][i])/(3* H[i]);
            re[1][i] = (re[0][i+1]-re[0][i])/H[i] - H[i]*(2*re[2][i]+re[2][i+1])/3;
        }

        for(double[] tt : re){
            for(double xx : tt){
                System.out.printf("%f ",xx);
            }
            System.out.println();
        }

        return re;
    }

    public static double[][] CubicSpline2(double[] x, double[] y){
        int size = x.length;
        double[][] A = new double[size][size];
        double[] H = new double[size];
        double[] b = new double[size];

        for(int i=0; i<size-1; i++){
            H[i] = x[i+1] - x[i];
        }

        for(int i=1; i<size-1; i++){
            A[i][i-1] = H[i-1];
            A[i][i] = 2* (H[i-1] + H[i]);
            A[i][i+1] = H[i];
        }
        A[0][0] = -H[1];
        A[0][1] = (H[0] + H[1]);
        A[0][2] = -H[0];
        A[size-1][size-1] = -H[size-3];
        A[size-1][size-2] = H[size-2] + H[size-3];
        A[size-1][size-3] = -H[size-2];

        for(int i=1; i<size-1; i++){
            b[i] = (3/H[i])*(y[i+1]-y[i]) - (3/H[i-1]*(y[i]-y[i-1]));
        }
        b[0] = 0;
        b[size-1] = 0;

        Matrix AA = new Matrix(A);
        Matrix bb = new Matrix(b, size);
        Matrix res = AA.inverse().times(bb);

        double[][] re = new double[4][size];
        re[2] = res.getColumnPackedCopy();
        re[0] = y;

        for(int i=0; i<size-1; i++){
            re[3][i] = (re[2][i+1] - re[2][i])/(3* H[i]);
            re[1][i] = (re[0][i+1]-re[0][i])/H[i] - H[i]*(2*re[2][i]+re[2][i+1])/3;
        }

        for(double[] tt : re){
            for(double xx : tt){
                System.out.printf("%f ",xx);
            }
            System.out.println();
        }

        return re;
    }

    private static double CubicGetValue(double[][] ww, double[] x, double index){
        int size = x.length;
        int ind = 0;
        while(ind < x.length && x[ind] < index){
            ind++;
        }
        if(ind == 0){
            ind = 1;
        }

        if(ind > size-1){
            index = size-1;
        }

        ind--;

        double change = index - x[ind];
        double res = ww[0][ind] + ww[1][ind]*change + ww[2][ind]*change*change + ww[3][ind]*change*change*change;

        return res;
    }


    public static void PlotTest(double[][] ww, double[] x){
        int size = (int)(3.5/0.01);
        double[] xx = new double[size];
        double[] yy = new double[size];

        xx[0] = -0.5;
        yy[0] = CubicGetValue(ww, x, xx[0]);
        for(int i=1; i<size; i++){
            xx[i] = xx[i-1] + 0.01;
            yy[i] = CubicGetValue(ww, x, xx[i]);
        }
        for(int i=0; i<size; i++){
            System.out.printf("%f ", yy[i]);
        }
        System.out.printf("\n");


        Table table1 = Table.create("xr").addColumns(
                DoubleColumn.create("xx", xx),
                DoubleColumn.create("yy", yy)
        );
        Plot.show(LinePlot.create("FIT", table1, "xx", "yy"));
    }





    public static void main(String[] args) {
        double[] x = new double[11];
        double[] y = new double[11];
        double step = 3.14/10;
        x[0] = 0;
        y[0] = Math.sin(x[0]);
        for(int i=1; i<11; i++){
            x[i] = x[i-1] + step;
            y[i] = Math.sin(x[i]);
        }

//        double[][] ww = CubicSpline1(x, y, 1, -1);
        double[][] ww = CubicSpline2(x, y);
        PlotTest(ww, x);
    }
}
