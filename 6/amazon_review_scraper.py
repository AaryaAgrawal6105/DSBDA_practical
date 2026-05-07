from selenium import webdriver
from bs4 import BeautifulSoup
import pandas as pd
import time

URL = "https://www.amazon.in/product-reviews/B0BSNWY1XZ/"

# 1. Open Amazon in a real Chrome browser
driver = webdriver.Chrome()
driver.get(URL)
time.sleep(5)   # wait for page to load

# 2. Get the page HTML and close the browser
soup = BeautifulSoup(driver.page_source, "html.parser")
driver.quit()

# 3. Find all review blocks
reviews = soup.find_all("div", {"data-hook": "review"})

names, ratings, titles, dates, comments = [], [], [], [], []

# 4. Extract fields from each review
for r in reviews:
    name   = r.find("span", class_="a-profile-name")
    rating = r.find("i", {"data-hook": "review-star-rating"})
    title  = r.find("a", {"data-hook": "review-title"})
    date   = r.find("span", {"data-hook": "review-date"})
    body   = r.find("span", {"data-hook": "review-body"})

    names.append(name.text.strip() if name else "N/A")
    ratings.append(rating.text.strip() if rating else "N/A")
    titles.append(title.text.strip() if title else "N/A")
    dates.append(date.text.strip() if date else "N/A")
    comments.append(body.text.strip() if body else "N/A")

# 5. Save to CSV
df = pd.DataFrame({
    "Customer Name": names,
    "Rating": ratings,
    "Review Title": titles,
    "Date": dates,
    "Comment": comments
})

df.to_csv("amazon_reviews.csv", index=False)
print(df)
print("\nSaved", len(df), "reviews to amazon_reviews.csv")
