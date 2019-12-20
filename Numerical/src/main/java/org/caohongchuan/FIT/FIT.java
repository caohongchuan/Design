package org.caohongchuan.FIT;

import Jama.Matrix;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class FIT {

    public static double[] FITSolve(double[] X, double[] Y, String method){
        if(X.length != Y.length){
            throw new RuntimeException("Error equal between X and Y");
        }
        if(X.length == 0){
            throw new RuntimeException("Empty");
        }

        double[] res = new double[X.length];

        if("LS".equals(method)){
            res = LeastSquares(X, Y, 2);
        }


        return res;
    }

    public static double[] LeastSquares(double[] X, double[] Y, int times){
        int size = X.length;
        double[][] XX = new double[size][times+1];

        //get XX
        for(int i=0; i<size; i++){
            for(int j=0; j<times+1; j++){
                if(j == 0){
                    XX[i][j] = X[i];
                }else if(j < times){
                    XX[i][j] = XX[i][j-1]*X[i];
                }else{
                    XX[i][j] = 1;
                }
            }
        }


        Matrix XXM = new Matrix(XX);
        Matrix YYM = new Matrix(Y, size);
        Matrix WWM = ((XXM.transpose().times(XXM)).inverse()).times(XXM.transpose()).times(YYM);

        return WWM.getColumnPackedCopy();
    }

    public static void plotTest(double[] w, double start, double end){

        int size = (int)((end - start)/0.01);
        double[] res = new double[size];
        double[] index = new double[size];

        for(int i=0; i<size; i++){

            double pre = start + 0.01*i;
            double num = pre;
            double ans = 0;
            for(int j=0; j<w.length-1; j++){
                ans = ans + pre*w[j];
                pre *= num;
            }
            ans += w[w.length-1];
            res[i] = ans;
            index[i] = num;
        }

        Table table1 = Table.create("xr").addColumns(
                DoubleColumn.create("X", index),
                DoubleColumn.create("Y", res)
        );
        Plot.show(ScatterPlot.create("FIT", table1, "X", "Y"));
    }

    public static void main(String[] args) {
        double[] x = {-2, -1, 0, 1, 2};
        double[] y = {0, 1, 2, 1, 0};

        double[] ww = LeastSquares(x, y, 2);
        for(double xx : ww){
            System.out.printf("%f ",xx);
        }
        plotTest(ww, -3, 3);

    }
}
