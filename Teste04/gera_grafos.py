import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages

# =============================
# Criar DataFrame diretamente
# =============================
dados = {
    "arquivo": [
        "grafo_euleriano_10.txt",
        "grafo_euleriano_50.txt",
        "grafo_euleriano_100.txt",
        "grafo_euleriano_500.txt",
        "grafo_semi_euleriano_10.txt",
        "grafo_semi_euleriano_50.txt",
        "grafo_semi_euleriano_100.txt",
        "grafo_semi_euleriano_500.txt",
    ],
    "N": [10, 50, 100, 500, 10, 50, 100, 500],
    "M": [20, 150, 400, 2500, 29, 199, 499, 2999],
    "Flow": [2, 3, 4, 5, 3, 4, 5, 6],
    "Tempo_ms": [0, 0, 0, 3, 0, 0, 0, 1],
    "Mem_KB": [3441, 4595, 4595, 7552, 7552, 7552, 8044, 10657]
}

df = pd.DataFrame(dados)

# Identificar tipo
df["tipo"] = df["arquivo"].apply(
    lambda x: "Euleriano" if "euleriano" in x and "semi" not in x else "Semi-Euleriano"
)

# =============================
# Criar PDF de saída
# =============================
pdf = PdfPages("graficos_resultados.pdf")

def salvar_grafico(fig, nome):
    fig.savefig(nome, dpi=300, bbox_inches="tight")
    pdf.savefig(fig)
    plt.close(fig)


# ============================================================
# GRÁFICO 1: Tempo x N
# ============================================================
fig1 = plt.figure()
for tipo, grupo in df.groupby("tipo"):
    plt.plot(grupo["N"], grupo["Tempo_ms"], marker="o", label=tipo)

plt.xlabel("Número de vértices (N)")
plt.ylabel("Tempo (ms)")
plt.title("Tempo de execução x Número de vértices")
plt.legend()
salvar_grafico(fig1, "tempo_x_n.png")


# ============================================================
# GRÁFICO 2: Flow x N
# ============================================================
fig2 = plt.figure()
for tipo, grupo in df.groupby("tipo"):
    plt.plot(grupo["N"], grupo["Flow"], marker="o", label=tipo)

plt.xlabel("N")
plt.ylabel("Fluxo Máximo (caminhos disjuntos)")
plt.title("Caminhos disjuntos x Número de vértices")
plt.legend()
salvar_grafico(fig2, "flow_x_n.png")


# ============================================================
# GRÁFICO 3: M x N
# ============================================================
fig3 = plt.figure()
for tipo, grupo in df.groupby("tipo"):
    plt.plot(grupo["N"], grupo["M"], marker="o", label=tipo)

plt.xlabel("N")
plt.ylabel("Número de arestas (M)")
plt.title("Crescimento de arestas x Número de vértices")
plt.legend()
salvar_grafico(fig3, "m_x_n.png")


# ============================================================
# GRÁFICO 4: Tempo por aresta x N
# ============================================================
df["tempo_por_aresta"] = df["Tempo_ms"] / df["M"]

fig4 = plt.figure()
for tipo, grupo in df.groupby("tipo"):
    plt.plot(grupo["N"], grupo["tempo_por_aresta"], marker="o", label=tipo)

plt.xlabel("N")
plt.ylabel("Tempo por aresta (ms / aresta)")
plt.title("Tempo normalizado por aresta")
plt.legend()
salvar_grafico(fig4, "tempo_por_aresta.png")


# ============================================================
# GRÁFICO 5: Memória x N
# ============================================================
fig5 = plt.figure()
for tipo, grupo in df.groupby("tipo"):
    plt.plot(grupo["N"], grupo["Mem_KB"], marker="o", label=tipo)

plt.xlabel("N")
plt.ylabel("Memória (KB)")
plt.title("Uso de memória x Número de vértices")
plt.legend()
salvar_grafico(fig5, "memoria_x_n.png")


# Finalizar PDF
pdf.close()

print("Gráficos gerados com sucesso!")
print("Arquivos criados:")
print("- graficos_resultados.pdf")
print("- tempo_x_n.png")
print("- flow_x_n.png")
print("- m_x_n.png")
print("- tempo_por_aresta.png")
print("- memoria_x_n.png")
