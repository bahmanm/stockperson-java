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
package stockperson.service;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;
import stockperson.model.Invoice;

public class InvoicePrettyPrinter implements Consumer<Invoice> {

  @Override
  public void accept(Invoice invoice) {
    System.out.println(
        "+------------------------------------------------------------------------------+");
    System.out.println(
        "| INVOICE#: %-35s DATE: %-24s |"
            .formatted(
                invoice.getDocNo(), new SimpleDateFormat("yyyy-MM-dd").format(invoice.getDate())));
    System.out.println(
        "| CUSTOMER: %-35s DISCOUNT%%: %-19.2f | "
            .formatted(invoice.getBusinessPartner(), invoice.getDiscount()));
    System.out.println(
        "+------------------------------------------------------------------------------+");
    System.out.println(
        "| #   | PRODUCT                     | QTY      | PRICE      | AMOUNT           |");
    System.out.println(
        "+------------------------------------------------------------------------------+");
    var lines =
        invoice.getLines().stream()
            .sorted(
                (l1, l2) -> {
                  return (l1.getLineNo() <= l2.getLineNo()) ? -1 : 1;
                })
            .toList();
    for (var line : lines) {
      System.out.println(
          "| %4d  %-27s  %10.2f  %11.2f  %16.2f |"
              .formatted(
                  line.getLineNo(),
                  line.getProduct().getCode(),
                  line.getQty(),
                  line.getPrice(),
                  line.getAmt()));
    }
    System.out.println(
        "+------------------------------------------------------------------------------+");
    System.out.println(
        "|                                                     TOTAL: %17.2f |"
            .formatted(invoice.getTotal()));
    System.out.println(
        "+------------------------------------------------------------------------------+");
    System.out.println();
  }
}
