package org.caohongchuan.SLE;

import Jama.Matrix;
import org.checkerframework.checker.units.qual.Luminance;

import java.util.Arrays;

public class LSE {

    public static double[] solveEquation(double[][] A, double[] b, double errors, String method){

        if(A.length != A[0].length || A.length != b.length){
            throw new RuntimeException("Matrix error");
        }

        double[] res = new double[A.length];
        Arrays.fill(res, 0);

        if("LU".equals(method)){
            res = LUSolve(A, b);
        }else if("LLT".equals(method)){

        }else if("LDL".equals(method)){

        }else if("Joc".equals(method)){
            res = JacobilSolve(A, b, errors);
        }else if("GS".equals(method)){

        }else if("SOR".equals(method)){

        }else{
            throw new RuntimeException("No method");
        }

        return res;
    }

    //Calcluate L and U matrix
    private static double[][] LUdecompose(double[][] A){
        int size = A.length;

        double[][] matrix = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrix[i][j] = 0;
            }
        }

        for(int i=0; i<size; i++){
            for(int j=i; j<size; j++){

                //calculate U
                for(int k=0; k<i; k++){
                    matrix[i][j] -= matrix[i][k]*matrix[k][j];
                }
                matrix[i][j] = A[i][j] + matrix[i][j];

                //calculate L
                if(i != j){
                    for(int k=0; k<i; k++){
                        matrix[j][i] -= matrix[j][k]*matrix[k][i];
                    }
                    matrix[j][i] = (A[j][i] + matrix[j][i])/matrix[i][i];
                }

            }
        }
        return matrix;
    }

    private static double[] LUSolve(double[][] A, double[] b){
        int size = A.length;

        double[][] matrix = LUdecompose(A);

        //calculate y
        for(int i=0; i<size; i++){
            double sum=0;
            for(int k=0; k<i; k++){
                sum = sum + matrix[i][k]*b[k];
            }
            b[i] -= sum;
        }

        //calculate x
        for(int i=size-1; i>=0; i--){
            double sum=0;
            for(int k=i+1; k<size; k++){
                sum += matrix[i][k]*b[k];
            }
            b[i] -= sum;
            b[i] /= matrix[i][i];
        }

        return b;
    }

    public static double[] JacobilSolve(double[][] A, double[] b, double errors){

        int size = A.length;

        //calculate D-1(L + U)
        double[][] DLU = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(A[i][i] == 0){
                    throw new RuntimeException("zero A[i][i]");
                }

                if(i == j){
                   DLU[i][j] = 0;
                   continue;
                }
                DLU[i][j] = (-A[i][j])/A[i][i];
            }
        }

        //calculate D-1b
        double[] DB = new double[size];
        for(int i=0; i<size; i++){
            DB[i] = b[i] / A[i][i];
        }

        //init x0
        double[] xx0 = new double[size];
        Arrays.fill(xx0, 1);

        //In order to speed the matrix multiply
        Matrix Xk = new Matrix(DLU);
        Matrix Xb = new Matrix(DB, size);
        Matrix x0 = new Matrix(xx0, size);
        Matrix xcur;


        while(true){
            xcur = Xk.times(x0).plus(Xb);

            double err = xcur.minus(x0).norm2()/xcur.norm2();
            if(err < errors){
                break;
            }

            x0 = xcur;
        }

        return xcur.getColumnPackedCopy();
    }


    public static double[] GSSolve(double[][] A, double[] b, double errors){
        int size = A.length;

        double[][] DL = new double[size][size];
        double[][] U = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(i <= j){
                    DL[i][j] = A[i][j];
                    U[i][j] = 0;
                }else{
                    U[i][j] = -A[i][j];
                    DL[i][j] =0;
                }
            }
        }

        //init x0
        double[] xx0 = new double[size];
        Arrays.fill(xx0, 1);

        Matrix DLL = (new Matrix(DL)).inverse();
        Matrix Xk = DLL.times(new Matrix(U));
        Matrix Xb = DLL.times(new Matrix(b, size));
        Matrix x0 = new Matrix(xx0, size);
        Matrix xcur;

        while(true){
            xcur = Xk.times(x0).plus(Xb);

            double err = xcur.minus(x0).norm2()/xcur.norm2();
            if(err < errors){
                break;
            }

            x0 = xcur;
        }

        return xcur.getColumnPackedCopy();
    }




    public static void main(String[] arg){
        double[] bb = new double[3];
        bb[0] = 7;
        bb[1] = -21;
        bb[2] = 15;
        double[][] test = new double[3][3];
        test[0][0] = 4;
        test[0][1] = -1;
        test[0][2] = 1;
        test[1][0] = 4;
        test[1][1] = -8;
        test[1][2] = 1;
        test[2][0] = -2;
        test[2][1] = 1;
        test[2][2] = 5;

        bb = GSSolve(test, bb, 0.00001);


        for(double xx: bb){
            System.out.printf("%f ", xx);
        }
    }
}
