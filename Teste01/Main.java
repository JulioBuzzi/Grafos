package Teste01;
import java.util.*;
import java.io.File;

public class Main {
    //private static final String nomeArquivo = "arquivoTeste100.txt";
    private static final String nomeArquivo = "arquivoTeste50000.txt";
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File(nomeArquivo));
            int vertices = scanner.nextInt();
            int arestas = scanner.nextInt();

            Grafo grafo = new Grafo(vertices, arestas);
            grafo.preencherVetor(scanner);
            scanner.close();
            Scanner sc = new Scanner(System.in);
            calculaAsMetricas(sc, grafo);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void calculaAsMetricas(Scanner sc, Grafo grafo) {
        System.out.println("Qual o vértice que deseja saber as informações: ");
            int vertice = sc.nextInt();

            //GRAU DE ENTRADA
            System.out.println("Grau de Entrada do vértice " + vertice +": " + grafo.calculaGrauDeEntrada(vertice));
            //GRAU DE SAÍDA
            System.out.println("Grau de Saída do vértice " + vertice + ": "+ grafo.calculaGrauDeSaida(vertice));
            //SUCESSORES
            System.out.print("Sucessores do vértice " + vertice + ": {");
            int[] listaSucessores = grafo.calculaListaSucessores(vertice);
            for(int i=0;i<listaSucessores.length-1;i++) {
                System.out.print( " " + listaSucessores[i] + ",");
            }
            System.out.print( " " + listaSucessores[grafo.calculaGrauDeSaida(vertice)-1] + " }");
            System.out.println("");
            //PREDECESSORES
            System.out.print("Predecessores do vértice " + vertice + ": {");
            int[] listaPredecessores = grafo.calculaListaPredecessores(vertice);
            for(int i=0;i<listaPredecessores.length-1;i++) {
                System.out.print( " " + listaPredecessores[i] + ",");
            }
            System.out.print( " " + listaPredecessores[grafo.calculaGrauDeEntrada(vertice)-1] + " }");
            System.out.println("");
    }

}
