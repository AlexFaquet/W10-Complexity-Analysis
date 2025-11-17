import os
import pandas as pd
import matplotlib.pyplot as plt


def main():
    # Locate project root and results directory
    base_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    results_dir = os.path.join(base_dir, "results")
    os.makedirs(results_dir, exist_ok=True)

    csv_path = os.path.join(results_dir, "results.csv")
    if not os.path.exists(csv_path):
        raise FileNotFoundError(
            f"Could not find {csv_path}. "
            "Run the Java ExperimentRunner first to generate results.csv."
        )

    # Load data
    df = pd.read_csv(csv_path)
    df["n"] = pd.to_numeric(df["n"])
    df["avgMs"] = pd.to_numeric(df["avgMs"])
    df = df.sort_values(["algorithm", "inputType", "n"])

    algorithms = df["algorithm"].unique()
    input_types = df["inputType"].unique()

    # One figure per algorithm (selection / merge / quick)
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

        filename = os.path.join(results_dir, f"{algo}_performance.png")
        plt.savefig(filename, dpi=300, bbox_inches="tight")
        print(f"Saved {filename}")

    # Combined comparison: all algorithms on random input
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

    comp_filename = os.path.join(results_dir, "comparison_random.png")
    plt.savefig(comp_filename, dpi=300, bbox_inches="tight")
    print(f"Saved {comp_filename}")

    plt.close("all")


if __name__ == "__main__":
    main()
