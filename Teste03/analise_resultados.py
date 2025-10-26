import pandas as pd
import matplotlib.pyplot as plt

dados_euler = {
    "Arquivo": ["eulerianoTeste1.txt", "eulerianoTeste2.txt", "eulerianoTeste3.txt", "eulerianoTeste4.txt"],
    "Vertices": [10, 50, 100, 500],
    "Peso": [33, 22, 44, 63],
    "Arestas": [6, 6, 15, 19],
    "Tempo(ms)": [23, 3, 4, 6]
}

dados_semi = {
    "Arquivo": ["semiEuleriano1.txt", "semiEuleriano2.txt", "semiEuleriano3.txt", "semiEuleriano4.txt"],
    "Vertices": [10, 50, 100, 500],
    "Peso": [30, 22, 31, 41],
    "Arestas": [5, 6, 10, 10],
    "Tempo(ms)": [1, 0, 2, 6]
}

df_euler = pd.DataFrame(dados_euler)
df_semi = pd.DataFrame(dados_semi)

#GRAFICO 1
plt.figure(figsize=(8,5))
plt.plot(df_euler["Vertices"], df_euler["Tempo(ms)"], marker="o", label="Eulerianos")
plt.plot(df_semi["Vertices"], df_semi["Tempo(ms)"], marker="s", label="Semi-Eulerianos")
plt.title("Eficiência: Tempo de execução × Número de vértices")
plt.xlabel("Número de vértices")
plt.ylabel("Tempo (ms)")
plt.xticks([10, 50, 100, 500])
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig("grafico_eficiencia.png")
plt.show()

#GRAFICO 2
plt.figure(figsize=(8,5))
plt.bar(df_euler["Vertices"], df_euler["Peso"], width=15, label="Eulerianos")
plt.bar([v + 15 for v in df_semi["Vertices"]], df_semi["Peso"], width=15, label="Semi-Eulerianos")
plt.title("Eficácia: Peso do Caminho Mínimo × Tipo de Grafo")
plt.xlabel("Número de vértices")
plt.ylabel("Peso total do caminho mínimo")
plt.xticks([10, 50, 100, 500])
plt.legend()
plt.grid(True, axis='y')
plt.tight_layout()
plt.savefig("grafico_eficacia.png")
plt.show()
