/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutoDeMatrizes;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Leandro
 */
public class ProdutoInterno implements Runnable {

    private double[] v1, v2;
    private double resultado;
    private final CountDownLatch trava;

    public ProdutoInterno(CountDownLatch trava, double v1[], double v2[]) {
        this.v1 = v1.clone();
        this.v2 = v2.clone();
        this.trava = trava;
    }

    @Override
    public void run() {
        //System.out.println("Inicio da thread " + Thread.currentThread().getName());
        calcular();
        trava.countDown();
    }

    public void calcular() { //totalmente genérico
        for (int i = 0; i < v1.length; i++) {
            resultado += v1[i] * v2[i];
        }
    }

    public double getResultado() {
        return resultado;
    }
}
