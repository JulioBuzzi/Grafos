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
            this.origem = new int[vertice+1];
            this.destino = new int[arestas+1];
        }

        public int getVertice() {
            return this.vertice;
        }

        public void preencherVetor(Scanner scanner) {
            int verticeAtual = -1;
            int posDestino = 1; 
            int origemTemp;
            int destinoTemp;

            while (posDestino<=arestas) {
                origemTemp = scanner.nextInt();
                destinoTemp = scanner.nextInt();

                if (origemTemp != verticeAtual) {
                    origem[origemTemp] = posDestino;
                    verticeAtual = origemTemp;
                }

                destino[posDestino] = destinoTemp;
                posDestino++;
            }

            // System.out.println("---------------ORIGEM------------------");
            //     for (int i = 1; i <= vertice; i++) {
            //         System.out.println("Vértice " + i + " -> índice " + origem[i]);
            //     }
            //     System.out.println("----------------DESTINO-----------------");
            //     for (int i = 1; i <=arestas; i++) { // mostra apenas as 20 primeiras
            //         System.out.println(destino[i]);
            //     }
        }   


        public int calculaGrauDeEntrada(int vertice) {
            int grau = 0;
            for(int i=0;i<destino.length;i++) {
                if(destino[i] == vertice) grau++;
            }
            return grau;
        }

        public int calculaGrauDeSaida(int vertice) {
            int grau = origem[vertice+1] - origem[vertice];
            return grau;
        }

        public int[] calculaListaSucessores(int v) {
        int grauSaida = calculaGrauDeSaida(v);
        int[] listaSucessores = new int[grauSaida];
        int pos = origem[v];

        for (int i = 0; i < grauSaida; i++) {
            listaSucessores[i] = destino[pos + i];
        }
        return listaSucessores;
    }


        public int[] calculaListaPredecessores(int v) {
        int grauEntrada = calculaGrauDeEntrada(v);
        int[] listaPredecessores = new int[grauEntrada];
        int k = 0;

        for (int i = 1; i <= this.vertice; i++) {
            int pos = origem[i];
            int limite = (i == this.vertice) ? destino.length : origem[i+1]; 

            while (pos < limite) {
                if (destino[pos] == v) {
                    listaPredecessores[k++] = i;
                }
                pos++;
            }
        }
        return listaPredecessores;
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
            if(option == 1) nomeArquivo = "graph-test-100.txt";
            else if(option == 2) nomeArquivo = "graph-test-50000.txt";
        } while (option != 1 && option !=2);
        try (Scanner scanner = new Scanner(new File(nomeArquivo));){
            int vertices = scanner.nextInt();
            int arestas = scanner.nextInt();

            Grafo grafo = new Grafo(vertices, arestas);
            grafo.preencherVetor(scanner);
            scanner.close();
            int vertice;
            do { 
                System.out.print("Qual o vértice que deseja saber as informações: ");
                vertice = sc.nextInt();
                if(vertice > grafo.getVertice()) System.out.println(RED + "Número inválido! O grafo possui " + grafo.getVertice() + " vértices." + RESET);
            } while (vertice > grafo.getVertice());

            do {
                calculaAsMetricas(sc, grafo, vertice);
                do { 
                    vertice = menuContinuar(sc);
                    if(vertice > grafo.getVertice()) System.out.println(RED + "Número inválido! O grafo possui " + grafo.getVertice() + " vértices." + RESET);
                } while (vertice > grafo.getVertice());

            } while (vertice != 0);

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
    System.out.println(CYAN +"========================================");
    System.out.println( "       Continuar consultando vértices   ");
    System.out.println("========================================"+ RESET);
    System.out.println("Gostaria de saber informações de mais algum vértice?");
    System.out.println("Digite " + YELLOW + "0"+ RESET + " para sair ou informe o " + YELLOW + "número do vértice " + RESET + "para continuar." );
    System.out.print(BOLD + "Sua opção: " + RESET);

    int option = sc.nextInt();
    System.out.println();
    return option;
}
    
    static void calculaAsMetricas(Scanner sc, Grafo grafo, int vertice) {
        
            System.out.println("");
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
