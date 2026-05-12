import requests
from bs4 import BeautifulSoup
import pandas as pd

URL = "https://en.wikipedia.org/wiki/Pune_Institute_of_Computer_Technology"

# 1. Fetch & parse
soup = BeautifulSoup(requests.get(URL).text, "html.parser")

# 2. Loop all paragraphs and build rows
rows = []
for p in soup.find_all("p"):
    text = p.text.strip()
    if not text:
        continue
    rows.append({
        "Customer Name": "PICT Visitor",
        "Rating":        "5 stars",
        "Comment":       text,
        "Comment Tags":  "College Info",
        "Source":        URL
    })

# 3. Save
df = pd.DataFrame(rows)
df.to_csv("pict_reviews.csv", index=False)
print(df.head(10))
print("\nSaved", len(df), "items")
