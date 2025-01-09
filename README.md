# stockperson-java
[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/UMKeFZ8ns9T9vi5aquTfVT/818sSuLif4y4JSeRLN7p74/tree/main.svg?style=shield)](https://dl.circleci.com/status-badge/redirect/circleci/UMKeFZ8ns9T9vi5aquTfVT/818sSuLif4y4JSeRLN7p74/tree/main)
[![codecov](https://codecov.io/gh/bahmanm/stockperson-java/graph/badge.svg?token=rRkcgIXdpK)](https://codecov.io/gh/bahmanm/stockperson-java)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fbahmanm%2Fstockperson-java.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fbahmanm%2Fstockperson-java?ref=badge_shield)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/bahmanm/stockperson-java)
![Matrix](https://img.shields.io/matrix/stockperson-java%3Amatrix.org?style=flat&logo=matrix&color=0e80c0)
[![license: GPL v3](https://img.shields.io/badge/license-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

[StockPerson](https://github.com/bahmanm/stockperson) implemented using Java.

_NOTE: At first I wanted this to be a SpringBoot application with a web interface. Turned out
my old ThinkPad just didn't have the resources for me to pull it off. So, I stuck to core Java._

# ✅ Chapter 1.0

* ✔️ Read a CSV file that contains sales invoice lines.
* ✔️ Pretty-print the invoices in a document style.

```
$ make chapter-1.0
```

# ✅ Chapter 2.0 

* ✔️ Read a CSV file that contains sales invoice lines.
* ✔️ Find the total sales.
* ✔️ Find the most expensive invoice and pretty print it in a document style.
* ✔️ Find the most expensive product and print its code.
* ✔️ Calculate the average price of each product, and print the the list of prices in descending order.
* ✔️ Find the total sales per customer and print a list in descending order in the form `customer, total`.
* ✔️ Find the customer with the largest total sales.
* ✔️ Find the 3 customers with the least total sales and print a list in descending format in the form `customer, total`.
* ✔️ Find the date with the largest total sales amount.

```
$ make chapter-2.0
```

# ✅ Chapter 3.0
* ✔️ Read a CSV file that contains product inventory. 
* ✔️ Read a CSV file that contains sales invoice lines.
* ✔️ Check if the product referenced on each line has enough inventory.
* ✔️ If no, do not import the invoice to which the line belongs.
* ✔️ If yes, import the invoice line and update the inventory of the product accordingly.
* ✔️ Print a list of invoices listing the problematic line(s) for each invoice.

```
$ make chapter-3.0
```


## License
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fbahmanm%2Fstockperson-java.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fbahmanm%2Fstockperson-java?ref=badge_large)
