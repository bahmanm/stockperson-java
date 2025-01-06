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

import static java.util.stream.Collectors.averagingDouble;
import static stockperson.db.Db.Db;

import java.util.*;
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
              return i1.getTotal() < i2.getTotal() ? 1 : -1;
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
    return productPrices.entrySet().stream()
        .sorted(
            (kv1, kv2) -> {
              return kv1.getValue() < kv2.getValue() ? 1 : -1;
            })
        .toList()
        .getFirst()
        .getKey();
  }

  public static Map<Product, Double> getAvgProductPrices() {
    var prices = new HashMap<Product, List<Double>>();
    for (var invoice : Db().getInvoices()) {
      for (var line : invoice.getLines()) {
        if (!prices.containsKey(line.getProduct())) {
          prices.put(line.getProduct(), new ArrayList<>());
        }
        prices.get(line.getProduct()).add(line.getPrice());
      }
    }
    var result = new HashMap<Product, Double>();
    for (var product : prices.keySet()) {
      result.put(product, prices.get(product).stream().collect(averagingDouble((price) -> price)));
    }
    return result;
  }

  public static Map<String, Double> getSalesByCustomer() {
    var result = new HashMap<String, Double>();
    for (var invoice : Db().getInvoices()) {
      if (!result.containsKey(invoice.getCustomer())) {
        result.put(invoice.getCustomer(), invoice.getTotal());
      } else {
        result.put(invoice.getCustomer(), invoice.getTotal() + result.get(invoice.getCustomer()));
      }
    }
    return result;
  }

  public static Map<Date, Double> getSalesByDate() {
    var result = new HashMap<Date, Double>();
    for (var invoice : Db().getInvoices()) {
      if (!result.containsKey(invoice.getDate())) {
        result.put(invoice.getDate(), invoice.getTotal());
      } else {
        result.put(invoice.getDate(), invoice.getTotal() + result.get(invoice.getDate()));
      }
    }
    return result;
  }
}
