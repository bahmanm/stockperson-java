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

import static stockperson.chapter2_0.Chapter2Service.*;
import static stockperson.service.InvoiceService.invoicesFromCsvFile;

import java.io.File;
import java.text.SimpleDateFormat;
import stockperson.service.InvoicePrettyPrinter;

public class Main {

  public static void main(String[] args) {
    // 1
    invoicesFromCsvFile(new File(args[0]));
    // 2
    System.out.printf("\uD83D\uDC49 TOTAL SALES: %.2f%n", getTotalSales());
    // 3
    new InvoicePrettyPrinter().accept(getMostExpensiveInvoice());
    // 4
    System.out.printf(
        "\uD83D\uDC49 MOST EXPENSIVE PRODUCT: %s\n", getMostExpensiveProduct().getCode());
    // 5
    System.out.println("\uD83D\uDC49 AVERAGE PRODUCT PRICES:");
    getAvgProductPrices().entrySet().stream()
        .sorted(
            (kv1, kv2) -> {
              return kv1.getValue() < kv2.getValue() ? 1 : -1;
            })
        .forEach(
            (kv) -> {
              System.out.printf("%-20s %20.2f\n", kv.getKey().getCode(), kv.getValue());
            });
    // 6
    System.out.println("\uD83D\uDC49 SALES BY CUSTOMER:");
    getSalesByCustomer().entrySet().stream()
        .sorted(
            (kv1, kv2) -> {
              return kv1.getValue() < kv2.getValue() ? 1 : -1;
            })
        .forEach(
            (kv) -> {
              System.out.printf("%-20s %20.2f\n", kv.getKey(), kv.getValue());
            });
    // 7
    System.out.print("\uD83D\uDC49 CUSTOMER WITH LARGEST SALE: ");
    System.out.println(
        getSalesByCustomer().entrySet().stream()
            .sorted(
                (kv1, kv2) -> {
                  return kv1.getValue() < kv2.getValue() ? 1 : -1;
                })
            .toList()
            .getFirst()
            .getKey());
    // 8
    System.out.println("\uD83D\uDC49 3 CUSTOMERS WITH LEAST SALES: ");
    var customerSales =
        getSalesByCustomer().entrySet().stream()
            .sorted(
                (kv1, kv2) -> {
                  return kv1.getValue() < kv2.getValue() ? -1 : 1;
                })
            .toList();
    System.out.printf(
        "%-20s %20.2f\n", customerSales.get(2).getKey(), customerSales.get(2).getValue());
    System.out.printf(
        "%-20s %20.2f\n", customerSales.get(1).getKey(), customerSales.get(1).getValue());
    System.out.printf(
        "%-20s %20.2f\n", customerSales.get(0).getKey(), customerSales.get(0).getValue());
    // 9
    System.out.printf(
        "\uD83D\uDC49 DATE WITH LARGEST TOTAL SALES: %s\n",
        new SimpleDateFormat("yyyy-MM-dd")
            .format(
                getSalesByDate().entrySet().stream()
                    .sorted(
                        (kv1, kv2) -> {
                          return kv1.getValue() < kv2.getValue() ? 1 : -1;
                        })
                    .toList()
                    .getFirst()
                    .getKey()));
  }
}
