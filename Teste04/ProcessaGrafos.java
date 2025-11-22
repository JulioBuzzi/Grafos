import java.io.*;
import java.util.*;

public class ProcessaGrafos {

    static final int INF = 1_000_000;

    static int n, m, s, t;
    static int[][] cap;
    static int[][] flow;
    static int[] parent;

    static void carregarGrafo(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String[] first = br.readLine().split(" ");
        n = Integer.parseInt(first[0]);
        m = Integer.parseInt(first[1]);

        String[] begin = br.readLine().split(" ");
        s = Integer.parseInt(begin[0]);
        t = Integer.parseInt(begin[1]);

        cap = new int[n][n];
        flow = new int[n][n];
        parent = new int[n];

        for (int i = 0; i < m; i++) {
            String[] line = br.readLine().split(" ");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            cap[u][v] = 1; 
        }

        br.close();
    }

    static boolean bfs() {
        Arrays.fill(parent, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        parent[s] = -2;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v = 0; v < n; v++) {
                if (parent[v] == -1 && cap[u][v] - flow[u][v] > 0) {
                    parent[v] = u;
                    if (v == t) return true;
                    q.add(v);
                }
            }
        }
        return false;
    }

    static int edmondsKarp() {
        int maxFlow = 0;

        while (bfs()) {
            int f = INF;

            int v = t;
            while (v != s) {
                int u = parent[v];
                f = Math.min(f, cap[u][v] - flow[u][v]);
                v = u;
            }

            v = t;
            while (v != s) {
                int u = parent[v];
                flow[u][v] += f;
                flow[v][u] -= f;
                v = u;
            }

            maxFlow += f;
        }

        return maxFlow;
    }

    // Extração dos caminhos
    static List<List<Integer>> extrairCaminhos() {
        List<List<Integer>> caminhos = new ArrayList<>();

        while (true) {
            int current = s;
            List<Integer> path = new ArrayList<>();
            path.add(current);

            boolean advanced = false;

            while (current != t) {
                int next = -1;

                for (int v = 0; v < n; v++) {
                    if (flow[current][v] == 1) {
                        next = v;
                        flow[current][v] = 0;
                        break;
                    }
                }

                if (next == -1) break;

                path.add(next);
                current = next;
                advanced = true;
            }

            if (!advanced || current != t)
                break;

            caminhos.add(path);
        }

        return caminhos;
    }

    // Executa tudo para um grafo
    static Resultado processarArquivo(String filename) throws Exception {
        carregarGrafo(filename);

        long inicio = System.nanoTime();
        int maxFlow = edmondsKarp();
        long fim = System.nanoTime();

        long tempoMs = (fim - inicio) / 1_000_000;

        List<List<Integer>> caminhos = extrairCaminhos();

        long memoria = (Runtime.getRuntime().totalMemory()
                        - Runtime.getRuntime().freeMemory()) / 1024;

        return new Resultado(filename, n, m, maxFlow, caminhos, tempoMs, memoria);
    }

    // Classe auxiliar para armazenar resultado
    static class Resultado {
        String nome;
        int n, m, maxFlow;
        long tempoMs, memoriaKB;
        List<List<Integer>> caminhos;

        Resultado(String nome, int n, int m, int maxFlow,
                  List<List<Integer>> caminhos,
                  long tempo, long memoria) {
            this.nome = nome;
            this.n = n;
            this.m = m;
            this.maxFlow = maxFlow;
            this.caminhos = caminhos;
            this.tempoMs = tempo;
            this.memoriaKB = memoria;
        }
    }

    // ============================
    // MAIN — Lê os 8 arquivos
    // ============================

    public static void main(String[] args) throws Exception {

        String[] arquivos = {
            "grafo_euleriano_10.txt",
            "grafo_euleriano_50.txt",
            "grafo_euleriano_100.txt",
            "grafo_euleriano_500.txt",
            "grafo_semi_euleriano_10.txt",
            "grafo_semi_euleriano_50.txt",
            "grafo_semi_euleriano_100.txt",
            "grafo_semi_euleriano_500.txt"
        };

        List<Resultado> resultados = new ArrayList<>();

        for (String arq : arquivos) {
            System.out.println("\n==============================================");
            System.out.println("Processando: " + arq);
            System.out.println("==============================================");

            Resultado r = processarArquivo(arq);
            resultados.add(r);

            System.out.println("Vértices: " + r.n + ", Arestas: " + r.m);
            System.out.println("Caminhos disjuntos encontrados: " + r.maxFlow);
            System.out.println("Tempo: " + r.tempoMs + " ms");
            System.out.println("Memória usada: " + r.memoriaKB + " KB");
            System.out.println();

            for (int i = 0; i < r.caminhos.size(); i++) {
                System.out.print("Caminho " + (i+1) + ": ");
                for (int v : r.caminhos.get(i)) System.out.print(v + " ");
                System.out.println();
            }
        }

        // Tabela final para relatório
        System.out.println("\n\n================== TABELA RESUMO ==================");
        System.out.printf("%-30s %-8s %-8s %-8s %-10s %-10s\n",
                "Arquivo", "N", "M", "Flow", "Tempo(ms)", "Mem(KB)");

        for (Resultado r : resultados) {
            System.out.printf("%-30s %-8d %-8d %-8d %-10d %-10d\n",
                    r.nome, r.n, r.m, r.maxFlow, r.tempoMs, r.memoriaKB);
        }
    }
}
