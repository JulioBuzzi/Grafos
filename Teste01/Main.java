import java.io.File;
import java.util.Scanner;

class Grafo {
    private final int vertice;
    private final int arestas;
    private final int[] origem; 
    private final int[] destino; 

    public Grafo(int vertice, int arestas) {
        this.vertice = vertice;
        this.arestas = arestas;
        this.origem = new int[vertice + 2]; 
        this.destino = new int[arestas + 1]; 
    }

    public int getVertice() {
        return this.vertice;
    }

    public void preencherVetor(Scanner scanner) {
        int posDestino = 1;         
        int posOrigem = 0; 

        while (posDestino <= arestas) {
            int origemTemp = scanner.nextInt();
            int destinoTemp = scanner.nextInt();

            for (int i = posOrigem + 1; i <= origemTemp; i++) {
                if (origem[i] == 0) origem[i] = posDestino;
            }
            destino[posDestino] = destinoTemp;
            posDestino++;
            posOrigem = origemTemp;
            }
            for (int i = posOrigem + 1; i <= vertice + 1; i++) {
                origem[i] = posDestino; 
            }
            if (origem[1] == 0) origem[1] = 1;
    }

    public int calculaGrauDeEntrada(int v) {
        int grau = 0;
        for (int i = 1; i <= arestas; i++) {
            if (destino[i] == v) grau++;
        }
        return grau;
    }

    public int calculaGrauDeSaida(int v) {
        if (v < 1 || v > vertice) return 0;
        return origem[v + 1] - origem[v];
    }

    public int[] calculaListaSucessores(int v) {
        int grau = calculaGrauDeSaida(v);
        int[] lista = new int[grau];
        int pos = origem[v];
        for (int i = 0; i < grau; i++) {
            lista[i] = destino[pos + i];
        }
        return lista;
    }

    public int[] calculaListaPredecessores(int v) {
        int grau = calculaGrauDeEntrada(v);
        int[] lista = new int[grau];
        int k = 0;
        for (int i = 1; i <= this.vertice; i++) {
            int ini = origem[i];
            int fim = origem[i + 1]; // usa o sentinela V+1
            for (int p = ini; p < fim; p++) {
                if (destino[p] == v) lista[k++] = i;
            }
        }
        return lista;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nomeArquivo = "";
        int option;
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        do {
            option = menuInicio(sc);
            if (option == 1) nomeArquivo = "graph-test-100.txt";
            else if (option == 2) nomeArquivo = "graph-test-50000.txt";
        } while (option != 1 && option != 2);

        try (Scanner scanner = new Scanner(new File(nomeArquivo))) {
            int vertices = scanner.nextInt();
            int arestas = scanner.nextInt();

            Grafo grafo = new Grafo(vertices, arestas);
            grafo.preencherVetor(scanner);

            int vertice;
            do {
                System.out.print("Qual o vértice que deseja saber as informações: ");
                vertice = sc.nextInt();
                if (vertice > grafo.getVertice() || vertice < 0)
                    System.out.println(RED + "Número inválido! O grafo possui " + grafo.getVertice() + " vértices." + RESET);
                else if(vertice == 0) System.out.println(RED + "Número inválido! O vértice 0 não existe, o grafo possui " + grafo.getVertice() + " vértices." + RESET);
            } while (vertice > grafo.getVertice() || vertice <= 0);

            while (vertice != 0) {
                calculaAsMetricas(grafo, vertice);

                do {
                    vertice = menuContinuar(sc);
                    if (vertice > grafo.getVertice() || vertice < 0)
                        System.out.println(RED + "Número inválido! O grafo possui " + grafo.getVertice() + " vértices." + RESET);
                } while (vertice > grafo.getVertice() || vertice < 0);
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao processar o arquivo: " + e.getMessage());
        }
        sc.close();
    }

    public static int menuInicio(Scanner sc) {
        int option;
        System.out.println("1) 100 vértices");
        System.out.println("2) 50000 vértices");
        System.out.print("Você deseja representar qual grafo: ");
        option = sc.nextInt();
        return option;
    }

    public static int menuContinuar(Scanner sc) {
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";
        final String YELLOW = "\u001B[33m";
        final String BOLD = "\u001B[1m";
        System.out.println();
        System.out.println(CYAN + "========================================");
        System.out.println("       Continuar consultando vértices   ");
        System.out.println("========================================" + RESET);
        System.out.println("Gostaria de saber informações de mais algum vértice?");
        System.out.println("Digite " + YELLOW + "0" + RESET + " para sair ou informe o " + YELLOW + "número do vértice " + RESET + "para continuar.");
        System.out.print(BOLD + "Sua opção: " + RESET);

        int option = sc.nextInt();
        System.out.println();
        return option;
    }

    static void calculaAsMetricas(Grafo grafo, int vertice) {
        System.out.println();
        // GRAU DE ENTRADA
        System.out.println("Grau de Entrada do vértice " + vertice + ": " + grafo.calculaGrauDeEntrada(vertice));
        // GRAU DE SAÍDA
        int grauSaida = grafo.calculaGrauDeSaida(vertice);
        System.out.println("Grau de Saída do vértice " + vertice + ": " + grauSaida);

        // SUCESSORES
        int[] listaSucessores = grafo.calculaListaSucessores(vertice);
        System.out.print("Sucessores do vértice " + vertice + ": {");
        for (int i = 0; i < listaSucessores.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(listaSucessores[i]);
        }
        System.out.println("}");

        // PREDECESSORES
        int[] listaPredecessores = grafo.calculaListaPredecessores(vertice);
        System.out.print("Predecessores do vértice " + vertice + ": {");
        for (int i = 0; i < listaPredecessores.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(listaPredecessores[i]);
        }
        System.out.println("}");
    }
}
