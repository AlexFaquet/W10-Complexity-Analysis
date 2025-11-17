import pandas as pd
import matplotlib.pyplot as plt

# 1. Load CSV
df = pd.read_csv("results.csv")

# Ensure numeric types
df["n"] = pd.to_numeric(df["n"])
df["avgMs"] = pd.to_numeric(df["avgMs"])

# 2. Sort for nicer plots
df = df.sort_values(["algorithm", "inputType", "n"])

# 3. Show a quick summary table in the terminal (optional)
summary = df.groupby(["algorithm", "inputType"])["avgMs"].describe()
print("=== Summary statistics by algorithm and input type ===")
print(summary)
print()

# 4. Plot: one figure per algorithm, with 3 curves (random/sorted/reversed)
algorithms = df["algorithm"].unique()
input_types = df["inputType"].unique()

for algo in algorithms:
    subset = df[df["algorithm"] == algo]

    plt.figure()
    for input_type in input_types:
        s = subset[subset["inputType"] == input_type]
        if s.empty:
            continue
        s = s.sort_values("n")
        plt.plot(s["n"], s["avgMs"], marker="o", label=input_type)

    plt.title(f"{algo.capitalize()} sort performance")
    plt.xlabel("Input size n")
    plt.ylabel("Average time (ms)")
    plt.legend(title="Input type")
    plt.grid(True)

    # Save each figure as PNG for the report
    filename = f"{algo}_performance.png"
    plt.savefig(filename, dpi=300, bbox_inches="tight")
    print(f"Saved {filename}")

# 5. Optionally, one combined plot comparing all algorithms on random input
random_only = df[df["inputType"] == "random"]

plt.figure()
for algo in algorithms:
    s = random_only[random_only["algorithm"] == algo]
    if s.empty:
        continue
    s = s.sort_values("n")
    plt.plot(s["n"], s["avgMs"], marker="o", label=algo)

plt.title("Comparison on random input")
plt.xlabel("Input size n")
plt.ylabel("Average time (ms)")
plt.legend(title="Algorithm")
plt.grid(True)
plt.savefig("comparison_random.png", dpi=300, bbox_inches="tight")
print("Saved comparison_random.png")

plt.close("all")
print("\nAnalysis complete.")
