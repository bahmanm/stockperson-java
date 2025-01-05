/*
 * Copyright 2025 Bahman Movaqar
 *
 * This file is part of StockPerson-Java.
 *
 * StockPerson-Java is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * StockPerson-Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with StockPerson-Java. If not, see <https://www.gnu.org/licenses/>.
 */
package stockperson.chapter2_0;

import static stockperson.db.Db.Db;

import java.util.HashMap;
import stockperson.model.Invoice;
import stockperson.model.Product;

public class Chapter2Service {

  public static Double getTotalSales() {
    return Db().getInvoices().stream().mapToDouble(Invoice::getTotal).sum();
  }

  public static Invoice getMostExpensiveInvoice() {
    return Db().getInvoices().stream()
        .sorted(
            (i1, i2) -> {
              return i1.getTotal() < i2.getTotal() ? -1 : 1;
            })
        .toList()
        .getFirst();
  }

  public static Product getMostExpensiveProduct() {
    var productPrices = new HashMap<Product, Double>();
    for (var invoice : Db().getInvoices()) {
      for (var line : invoice.getLines()) {
        if (productPrices.containsKey(line.getProduct())) {
          if (productPrices.get(line.getProduct()) < line.getPrice()) {
            productPrices.put(line.getProduct(), line.getPrice());
          }
        } else {
          productPrices.put(line.getProduct(), line.getPrice());
        }
      }
    }
    Product result = null;
    Double maxPrice = 0d;
    for (var product : productPrices.keySet()) {
      if (productPrices.get(product) > maxPrice) {
        result = product;
      }
    }
    return result;
  }
}
