    import requests
    import pandas as pd

    URL = "https://store.steampowered.com/appreviews/730?json=1&filter=recent&language=english&num_per_page=100"

    # 1. Fetch JSON
    data = requests.get(URL).json()

    # 2. Loop and build rows
    rows = []
    for r in data["reviews"]:
        rows.append({
            "Customer (SteamID)": r["author"]["steamid"],
            "Rating":  "Recommended" if r["voted_up"] else "Not Recommended",
            "Comment": r["review"],
            "Tags":    "Verified Purchase" if r["steam_purchase"] else "Not Verified",
            "Helpful Votes": r["votes_up"]
        })

    # 3. Save
    df = pd.DataFrame(rows)
    df.to_csv("steam_reviews.csv", index=False)
    print(df)
    print("\nSaved", len(df), "reviews")
