package org.caohongchuan.Integration;


class Equation{
    public double eq(double x){
        return Math.exp(-1*x*x/2);
    }
}
public class Integration {

    public static double CompositeTrapezoidal(double a, double b, int n, Equation eq){
        double h = (b-a)/n;
        double res = 0;
        res += eq.eq(a)/2;
        res += eq.eq(b)/2;

        for(int i=1; i<n; i++){
            res += eq.eq(a+i*h);
        }

        res *= h;

        return res;
    }

    public static double CompositeSimpson(double a, double b, int n, Equation eq){
        if(n%2!=0){
            throw new RuntimeException("n is odd");
        }
        double h = (b-a)/n;

        double res = 0;
        res += eq.eq(a)/3;
        res += eq.eq(b)/3;

        for(int i=1; i<=(n/2-1); i++){
            res += eq.eq(a+2*i*h)*2/3;
            res += eq.eq(a+(2*i-1)*h)*4/3;
        }
        res += eq.eq(a+n/2*h)*4/3;

        res *= h;

        return res;
    }


    public static void main(String[] args) {
        Equation eq = new Equation();
        double y1 = CompositeTrapezoidal(0, 1, 100, eq);
        double y2 = CompositeSimpson(0, 1, 100, eq);
        System.out.printf("y1 = %f, y2 = %f\n",y1/(Math.sqrt(2*3.14)), y2/(Math.sqrt(2*3.14)));

        double cur = (-2.0*Math.exp(-0.5)+2)/(Math.sqrt(2*3.14));
        System.out.printf("acc = %f \n", cur);

    }
}
