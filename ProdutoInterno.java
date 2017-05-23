/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutoDeMatrizes;

import java.util.concurrent.Callable;

/**
 *
 * @author Leandro
 */
public class ProdutoInterno implements Callable {

    private final double[] v1, v2;

    public ProdutoInterno(double v1[], double v2[]) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
    }

    public double calcular() {
        double resultado = 0;
        for (int i = 0; i < v1.length; i++) {
            resultado += v1[i] * v2[i];
        }
        return resultado;
    }

    @Override
    public Object call() {
        //System.out.println(Thread.currentThread().getName() + " iniciada!");
        return calcular();
    }
}
