import java.io.*;
import java.util.*;

class Aresta {
    int destino;
    int peso;
    public Aresta(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
}

class Estado implements Comparable<Estado> {
    int vertice, distancia, arestas;
    public Estado(int vertice, int distancia, int arestas) {
        this.vertice = vertice;
        this.distancia = distancia;
        this.arestas = arestas;
    }

    @Override
    public int compareTo(Estado outro) {
        if (this.distancia != outro.distancia)
            return Integer.compare(this.distancia, outro.distancia);
        return Integer.compare(this.arestas, outro.arestas);
    }
}

public class CaminhoMinimo {
    static List<List<Aresta>> grafo;
    static int V, E;

    public static void main(String[] args) throws Exception {
        String[] arquivosEuler = {
            "eulerianoTeste1.txt",
            "eulerianoTeste2.txt",
            "eulerianoTeste3.txt",
            "eulerianoTeste4.txt"
        };

        String[] arquivosSemi = {
            "semiEuleriano1.txt",
            "semiEuleriano2.txt",
            "semiEuleriano3.txt",
            "semiEuleriano4.txt"
        };

        System.out.println("===== RESULTADOS PARA GRAFOS EULERIANOS =====");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Arquivo", "Peso", "Arestas", "Tempo(ms)");
        for (String nome : arquivosEuler) {
            testarGrafo(nome);
        }

        System.out.println("\n===== RESULTADOS PARA GRAFOS SEMI-EULERIANOS =====");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Arquivo", "Peso", "Arestas", "Tempo(ms)");
        for (String nome : arquivosSemi) {
            testarGrafo(nome);
        }
    }

    static void testarGrafo(String nomeArquivo) {
        try {
            long inicio = System.currentTimeMillis();

            Scanner sc = new Scanner(new File(nomeArquivo));
            List<String> linhas = new ArrayList<>();

            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                linhas.add(linha);
            }
            
            String[] primeira = linhas.get(0).split("\\s+");
            V = Integer.parseInt(primeira[0]);
            E = Integer.parseInt(primeira[1]);

            grafo = new ArrayList<>();
            for (int i = 0; i < V; i++) grafo.add(new ArrayList<>());

            for (int i = 1; i <= E; i++) {
                String[] p = linhas.get(i).split("\\s+");
                int u = Integer.parseInt(p[0]);
                int v = Integer.parseInt(p[1]);
                int w = Integer.parseInt(p[2]);
                grafo.get(u).add(new Aresta(v, w));
            }

            String[] ult = linhas.get(linhas.size() - 1).split("\\s+");
            int origem = Integer.parseInt(ult[0]);
            int destino = Integer.parseInt(ult[1]);

            Resultado r = dijkstra(origem, destino);

            long fim = System.currentTimeMillis();
            long tempo = fim - inicio;

            if (r.existe) {
                System.out.printf("%-20s %-10d %-10d %-10d\n", nomeArquivo, r.peso, r.qtdArestas, tempo);
            } else {
                System.out.printf("%-20s %-10s %-10s %-10d\n", nomeArquivo, "N/A", "N/A", tempo);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Erro ao processar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    static class Resultado {
        boolean existe;
        int peso;
        int qtdArestas;
    }

    static Resultado dijkstra(int origem, int destino) {
        Resultado r = new Resultado();
        int[] dist = new int[V];
        int[] arestas = new int[V];
        int[] anterior = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(arestas, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);

        PriorityQueue<Estado> pq = new PriorityQueue<>();
        dist[origem] = 0;
        arestas[origem] = 0;
        pq.add(new Estado(origem, 0, 0));

        while (!pq.isEmpty()) {
            Estado atual = pq.poll();

            if (atual.vertice == destino)
                break;

            if (atual.distancia > dist[atual.vertice])
                continue;

            for (Aresta e : grafo.get(atual.vertice)) {
                int novoCusto = atual.distancia + e.peso;
                int novaQtdArestas = atual.arestas + 1;

                if (novoCusto < dist[e.destino] ||
                   (novoCusto == dist[e.destino] && novaQtdArestas < arestas[e.destino])) {

                    dist[e.destino] = novoCusto;
                    arestas[e.destino] = novaQtdArestas;
                    anterior[e.destino] = atual.vertice;
                    pq.add(new Estado(e.destino, novoCusto, novaQtdArestas));
                }
            }
        }

        if (dist[destino] == Integer.MAX_VALUE) {
            r.existe = false;
        } else {
            r.existe = true;
            r.peso = dist[destino];
            r.qtdArestas = arestas[destino];
        }

        return r;
    }
}
