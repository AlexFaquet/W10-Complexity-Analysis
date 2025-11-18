import os
import pandas as pd
import matplotlib.pyplot as plt


def main():
    # Locate project root and results directory
    base_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    results_dir = os.path.join(base_dir, "results")

    csv_path = os.path.join(results_dir, "results.csv")
    if not os.path.exists(csv_path):
        raise FileNotFoundError(
            f"Could not find {csv_path}. "
            "Run the Java ExperimentRunner first to generate results.csv."
        )

    # Load raw trial data
    df = pd.read_csv(csv_path)

    # Ensure numeric types
    df["n"] = pd.to_numeric(df["n"])
    df["ms"] = pd.to_numeric(df["ms"])

    # Group by algorithm, inputType, n and compute mean + std
    grouped = (
        df.groupby(["algorithm", "inputType", "n"], as_index=False)
          .agg(mean_ms=("ms", "mean"), std_ms=("ms", "std"))
          .sort_values(["algorithm", "inputType", "n"])
    )

    algorithms = grouped["algorithm"].unique()
    input_types = grouped["inputType"].unique()

    # One figure per algorithm
    for algo in algorithms:
        subset = grouped[grouped["algorithm"] == algo]

        plt.figure()
        for input_type in input_types:
            s = subset[subset["inputType"] == input_type]
            if s.empty:
                continue

            # Error bars: mean ± std
            plt.errorbar(
                s["n"],
                s["mean_ms"],
                yerr=s["std_ms"],
                marker="o",
                capsize=4,
                label=input_type,
            )

        plt.title(f"{algo.capitalize()} sort performance")
        plt.xlabel("Input size n")
        plt.ylabel("Time (ms)")
        plt.legend(title="Input type")
        plt.grid(True)

        filename = os.path.join(results_dir, f"{algo}_performance.png")
        plt.savefig(filename, dpi=300, bbox_inches="tight")
        print(f"Saved {filename}")

    # Combined comparison: all algorithms on random input
    random_only = grouped[grouped["inputType"] == "random"]

    plt.figure()
    for algo in algorithms:
        s = random_only[random_only["algorithm"] == algo]
        if s.empty:
            continue

        plt.errorbar(
            s["n"],
            s["mean_ms"],
            yerr=s["std_ms"],
            marker="o",
            capsize=4,
            label=algo,
        )

    plt.title("Comparison on random input")
    plt.xlabel("Input size n")
    plt.ylabel("Time (ms)")
    plt.legend(title="Algorithm")
    plt.grid(True)

    comp_filename = os.path.join(results_dir, "comparison_random.png")
    plt.savefig(comp_filename, dpi=300, bbox_inches="tight")
    print(f"Saved {comp_filename}")

    plt.close("all")


if __name__ == "__main__":
    main()
