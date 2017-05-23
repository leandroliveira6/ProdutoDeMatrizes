/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlternativoProdutoDeMatrizes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Leandro
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        ArrayList<double[][]> matrizes = new ArrayList<>();
        Scanner arquivoLeitura;
        FileWriter arquivoEscrita;
        ExecutorService es;
        double resultado[][];
        int n, m, q;
        long tempo;

        // Inicialização
        //caminho = new File(new File("").getAbsolutePath() + "\\src\\ProdutoDeMatrizes\\Matrizes.txt");
        n = Integer.parseInt(args[1]); //numero de linhas/colunas
        m = Integer.parseInt(args[2]); //numero máximo de threads
        q = Integer.parseInt(args[3]); //numero de matrizes
        arquivoLeitura = new Scanner(new FileReader(new File(args[0])));
        for (int k = 0; k < q; k++) {
            double matriz[][] = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!arquivoLeitura.hasNext()) {
                        System.out.println("ERRO! Arquivo incompleto!");
                        System.exit(1);
                    }
                    matriz[i][j] = arquivoLeitura.nextDouble();
                }
            }
            matrizes.add(matriz);
        }
        arquivoLeitura.close();
        resultado = matrizes.get(0);
        matrizes.remove(0);
        es = Executors.newFixedThreadPool(m);
        tempo = System.nanoTime();
        // Fim Inicialização

        for (double[][] matriz : matrizes) {
            CountDownLatch trava = new CountDownLatch(n * n);
            ProdutoInterno p[] = new ProdutoInterno[n * n];

            transpor(matriz);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    p[n * i + j] = new ProdutoInterno(trava, resultado[i], matriz[j]);
                    es.execute(p[n * i + j]);
                }
            }
            trava.await();
            transpor(matriz); //matriz original

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    resultado[i][j] = p[n * i + j].getResultado();
                }
            }
        }
        tempo = System.nanoTime() - tempo;
        System.out.println(tempo + "ns");
        es.shutdown();

        arquivoEscrita = new FileWriter(new File("resultado.txt"));
        for (int i = 0; i < n; i++) {
            String s = Arrays.toString(resultado[i]) + "\r\n";
            arquivoEscrita.write(s);
            System.out.print(s);
        }
        arquivoEscrita.close();
    }

    public static void transpor(double m[][]) {
        for (int i = 0; i < m.length; i++) {
            for (int j = i + 1; j < m.length; j++) {
                double tmp;
                tmp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = tmp;
            }
        }
    }
}
