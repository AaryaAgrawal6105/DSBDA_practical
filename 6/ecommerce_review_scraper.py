import requests
from bs4 import BeautifulSoup
import pandas as pd

URL = "https://webscraper.io/test-sites/e-commerce/static/computers/laptops"

# 1. Fetch & parse
soup = BeautifulSoup(requests.get(URL).text, "html.parser")

# 2. Loop products and build rows
rows = []
for p in soup.find_all("div", class_="thumbnail"):
    rows.append({
        "Customer Name": p.find("a", class_="title").text.strip(),
        "Rating":        f"{len(p.find_all('span', class_='ws-icon'))} stars",
        "Comment":       p.find("p", class_="description").text.strip(),
        "Comment Tags":  p.find("h4", class_="price").text.strip(),
        "Review Count":  p.find("p", class_="review-count").text.strip()
    })
# 3. Save
df = pd.DataFrame(rows)
df.to_csv("ecommerce_reviews.csv", index=False)
print(df)
print("\nSaved", len(df), "products")
