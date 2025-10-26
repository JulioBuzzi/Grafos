from pathlib import Path

def peso(u, v):
    return (u + v) % 9 + 1

def escreve_arquivo(path, V, edges, origem, destino, comentario=None):
    E = len(edges)
    with open(path, "w", encoding="utf-8") as f:
        if comentario:
            for l in comentario.splitlines():
                f.write("# " + l + "\n")
        
        f.write(f"{V} {E}\n")
        
        for (u, v) in edges:
            f.write(f"{u} {v} {peso(u,v)}\n")
            
        f.write(f"{origem} {destino}\n")
    print(f"Gerado {path} -> V={V}, E={E}, origem={origem}, destino={destino}")

def gera_euleriano(V, extra_pair_count):

    edges = []
    for i in range(V):
        edges.append((i, (i+1)%V))
    
    step = max(1, V // 10)
    added = 0
    i = 0
    while added < extra_pair_count:
        a = i % V
        b = (i + step + 1) % V 
        
        edges.append((a,b))
        edges.append((b,a))
        added += 1
        i += 1
    return edges

def gera_semi_euleriano(V, extra_pair_count, origem=0, destino=None):
    if destino is None:
        destino = V // 2
        
    edges = gera_euleriano(V, extra_pair_count)
    
    edges.append((origem, destino))
    
    return edges, origem, destino

def main():
    out_dir = Path(".")

    # Euleriano 10
    edges_e10 = gera_euleriano(10, extra_pair_count=5)
    escreve_arquivo(out_dir/"euleriano10.txt", 10, edges_e10, origem=0, destino=0,
                     comentario="euleriano10.txt - Grafo Euleriano (10 vértices)")
    
    # Euleriano 50
    edges_e50 = gera_euleriano(50, extra_pair_count=50)
    escreve_arquivo(out_dir/"euleriano50.txt", 50, edges_e50, origem=0, destino=0,
                     comentario="euleriano50.txt - Grafo Euleriano (50 vértices)")

    # Euleriano 100
    edges_e100 = gera_euleriano(100, extra_pair_count=150)
    escreve_arquivo(out_dir/"euleriano100.txt", 100, edges_e100, origem=0, destino=0,
                     comentario="euleriano100.txt - Grafo Euleriano (100 vértices)")

    # Euleriano 500
    edges_e500 = gera_euleriano(500, extra_pair_count=750)
    escreve_arquivo(out_dir/"euleriano500.txt", 500, edges_e500, origem=0, destino=0,
                     comentario="euleriano500.txt - Grafo Euleriano (500 vértices)")


    # Semi-Euleriano 10
    edges_se10, origem_se10, destino_se10 = gera_semi_euleriano(10, extra_pair_count=5, destino=5)
    escreve_arquivo(out_dir/"semiEuleriano10.txt", 10, edges_se10, origem=origem_se10, destino=destino_se10,
                     comentario="semiEuleriano10.txt - Grafo Semi-Euleriano (10 vértices)")

    # Semi-Euleriano 50
    edges_se50, origem_se50, destino_se50 = gera_semi_euleriano(50, extra_pair_count=75, destino=25)
    escreve_arquivo(out_dir/"semiEuleriano50.txt", 50, edges_se50, origem=origem_se50, destino=destino_se50,
                     comentario="semiEuleriano50.txt - Grafo Semi-Euleriano (50 vértices)")

    # Semi-Euleriano 100
    edges_se100, origem_se100, destino_se100 = gera_semi_euleriano(100, extra_pair_count=150, destino=50)
    escreve_arquivo(out_dir/"semiEuleriano100.txt", 100, edges_se100, origem=origem_se100, destino=destino_se100,
                     comentario="semiEuleriano100.txt - Grafo Semi-Euleriano (100 vértices)")

    # Semi-Euleriano 500
    edges_se500, origem_se500, destino_se500 = gera_semi_euleriano(500, extra_pair_count=750, destino=250)
    escreve_arquivo(out_dir/"semiEuleriano500.txt", 500, edges_se500, origem=origem_se500, destino=destino_se500,
                     comentario="semiEuleriano500.txt - Grafo Semi-Euleriano (500 vértices)")

    print("\nConcluído. Todos os 8 arquivos foram gerados com sucesso.")

if __name__ == "__main__":
    main()
