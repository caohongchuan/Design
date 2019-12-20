package org.caohongchuan.QR;

import Jama.Matrix;

import java.util.Arrays;

class QRresult{
    public double[][] Q;
    public double[][] R;
}


public class QR {

    public static double[][] householder(double[] x, double[] y){
        if(x.length != y.length){
            throw new RuntimeException("x != y");
        }

        int size = x.length;
        Matrix xx = new Matrix(x, size);
        Matrix yy = new Matrix(y, size);

        double rho = 0;
        if(x[0] > 0){
            rho = - xx.norm2();
        }else{
            rho =  xx.norm2();
        }

        yy = yy.times(rho);

        Matrix v = xx.minus(yy);
        Matrix I = new Matrix(size, size);
        for(int i=0; i<size; i++){
            I.set(i, i, 1);
        }
        double vtv = v.norm2();
        Matrix res = I.minus(v.times(v.transpose()).times(2.0/(vtv*vtv)));

        return res.getArray();
    }

    public static QRresult qr(double[][] A){
        int size = A.length;
        int size_c = A[0].length;

        Matrix Q = new Matrix(size, size);
        for(int i=0; i<size; i++){
            Q.set(i, i, 1);
        }
        Matrix R = new Matrix(A);

        for(int i=0; i<size_c; i++){
            double[] a = new double[size];
            Arrays.fill(a, 0);
            for(int j=i; j<size; j++){
                a[j] = R.get(j, i);
            }
            double[] y = new double[size];
            Arrays.fill(y, 0);
            y[i] = 1;

            double[][] h = householder(a, y);
            Matrix hh = new Matrix(h);
            R = hh.times(R);
            Q = hh.times(Q);

            //test
            System.out.printf("-----------------------%d\n", i);
            for(int it=0; it<size; it++){
                for(int jt=0; jt<size; jt++){
                    System.out.printf("%f ", h[it][jt]);
                }
                System.out.printf("\n");
            }
        }

        QRresult res = new QRresult();
        res.Q = Q.getArray();
        res.R = R.getArray();


        return res;
    }


    public static void main(String[] args) {
//        double[] x = {-12,3,3};
//        double[] y = {1,0,0};
//        double[][] res = householder(x,y);
//        for(double[] xx :res){
//            for(double tt : xx){
//                System.out.printf("%f ",tt);
//            }
//            System.out.printf("\n");
//        }

        double[][] matrix = new double[5][3];
        matrix[0][0] = 1; matrix[0][1] = -1; matrix[0][2] = 1.0;
        matrix[1][0] = 1; matrix[1][1] = -0.5; matrix[1][2] = 0.25;
        matrix[2][0] = 1; matrix[2][1] = 0.0; matrix[2][2] = 0.0;
        matrix[3][0] = 1; matrix[3][1] = 0.5; matrix[3][2] = 0.25;
        matrix[4][0] = 1; matrix[4][1] = 1; matrix[4][2] = 1;

        qr(matrix);

    }
}
