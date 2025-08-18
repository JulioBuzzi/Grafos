import java.util.Scanner;

public class Grafo {
    private int vertice;
    private int arestas;
    private int[] origem;
    private int[] destino;
    
        public Grafo(int vertice, int arestas) {
            this.vertice = vertice;
            this.arestas = arestas;
            this.origem = new int[vertice+1];
            this.destino = new int[arestas+1];
        }

        public void preencherVetor(Scanner scanner) {
            int verticeAtual = -1;
            int posDestino = 1; 
            int origemTemp = -1;
            int destinoTemp = -1;

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
