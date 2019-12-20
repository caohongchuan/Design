package org.caohongchuan.EIGEN;

import Jama.Matrix;

import java.util.Arrays;

class Eig{
    public double[] eigenvalue;
    public double[][] eigenvector;

    // n the number of eigenvalue, m the size of eigenvectors
    public Eig(int n, int m){
        eigenvalue = new double[n];
        eigenvector = new double[n][m];
    }
}

public class Eigen {

    public static Eig PowerMethod(double[][] A, double[] X, int times){
        int size = A.length;

        Matrix AA = new Matrix(A);
        Matrix cur = new Matrix(X, size);
        Matrix next = new Matrix(X, size);
        double maxx = 0;
        for(int i=0; i<times; i++){
            next = AA.times(cur);
            maxx = next.normInf();
            next = next.timesEquals(1/maxx);
            if(next.minus(cur).norm2() < 0.001){
                break;
            }
            cur = next;
        }
        Eig eig = new Eig(1, size);
        eig.eigenvalue[0] = maxx;
        eig.eigenvector[0] = cur.getColumnPackedCopy();
        return eig;
    }

    public static Eig InversePowerMethod(double[][] A, double[] X, int times){

        int size = A.length;
        Eig eig = new Eig(1, size);
        Matrix cur = new Matrix(X, size);
        Matrix next = new Matrix(X, size);
        Matrix AA = new Matrix(A);
        double q = cur.transpose().times(AA).times(cur).get(0,0)/cur.transpose().times(cur).get(0,0);

        for(int i=0; i<size; i++){
            A[i][i] = A[i][i] - q;
        }


        try {
            AA = AA.inverse();
        }catch (Exception e){
            eig.eigenvalue[0] = q;
            return eig;
        }


        double maxx = 0;
        for(int i=0; i<times; i++){
            next = AA.times(cur);
            maxx = next.normInf();
            next = next.timesEquals(1/maxx);
            if(next.minus(cur).norm2() < 0.001){
                break;
            }
            cur = next;
        }

        eig.eigenvalue[0] = 1/maxx + q;
        eig.eigenvector[0] = cur.getColumnPackedCopy();
        return eig;
    }

    public static Eig SymmetricPowerMethod(double[][] A, double[] X, int times){
        int size = A.length;

        Matrix AA = new Matrix(A);
        Matrix cur = new Matrix(X, size);
        Matrix next = new Matrix(X, size);

        double x2 = cur.norm2();
        cur.timesEquals(1/x2);
        double maxx = 0;

        for(int i=0; i<times; i++){
            next = AA.times(cur);
            maxx = cur.transpose().times(next).get(0,0);
            x2 = next.norm2();
            if(x2 == 0){
                break;
            }

            next = next.times(1/x2);
            if(next.minus(cur).norm2() < 0.001){
                break;
            }
            cur = next;
        }
        Eig eig = new Eig(1, size);
        eig.eigenvalue[0] = maxx;
        eig.eigenvector[0] = cur.getColumnPackedCopy();
        return eig;
    }

    public static double[][] getH(double[] v){
        int size = v.length;
        double[][] res = new double[size][size];
        double vv = 0;
        for(int i=0; i<size; i++){
            vv += v[i]*v[i];
        }
        vv = 2 / vv;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                res[i][j] = v[i] * v[j];
                res[i][i] *= vv;
                if(i==j){
                    res[i][j] = 1 - res[i][j];
                }else{
                    res[i][j] = -res[i][j];
                }

            }
        }

        for(double[] xx : res){
            for(double xxx : xx){
                System.out.printf("%f ",xxx);
            }
            System.out.printf("\n");
        }

        return res;

    }


    public static void main(String[] args) {
        double[][] matrix = new double[3][3];
        matrix[0][0] = 4;
        matrix[0][1] = -1;
        matrix[0][2] = 1;
        matrix[1][0] = -1;
        matrix[1][1] = 3;
        matrix[1][2] = -2;
        matrix[2][0] = 1;
        matrix[2][1] = -2;
        matrix[2][2] = 3;
        double[] x0 = new double[3];
        Arrays.fill(x0, 1);
//        Eig eig = SymmetricPowerMethod(matrix, x0, 200);
//
//        System.out.printf("eigenvalue : %f \n", eig.eigenvalue[0]);
//        double[] vector = eig.eigenvector[0];
//        System.out.printf("eigenvector : ");
//        for(double xx : vector){
//            System.out.printf("%f ",xx);
//        }
        double[] v1 = new double[5];
        v1[0]=3.236; v1[1] = 1; v1[2] = 1; v1[3] = 1;v1[4] = 1;
        getH(v1);
        System.out.printf("-------------------\n");
        double[] v2 = new double[5];
        v2[0]=0; v2[1]=-1.772; v2[2]=0.309; v2[3]=0.809; v2[4]=1.309;
        getH(v2);
        System.out.printf("-------------------\n");
        double[] v3 = new double[5];
        v3[0]=0; v3[1]=0; v3[2]=-1.660; v3[3]=-0.589; v3[4]=0.047;
        getH(v3);
    }
}
