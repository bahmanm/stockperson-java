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

import static stockperson.db.Db.Db;

import java.io.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import stockperson.model.Invoice;
import stockperson.model.InvoiceLine;

public class InvoiceService {

  public static Optional<Set<Invoice>> process(Set<Invoice> invoices) {
    Set<Invoice> unprocessedInvoices = new HashSet<>();
    invoices.stream()
        .sorted((i1, i2) -> i1.getDate().before(i2.getDate()) ? -1 : 1)
        .forEachOrdered(
            invoice -> {
              if (!process(invoice)) {
                unprocessedInvoices.add(invoice);
              }
            });
    return unprocessedInvoices.isEmpty() ? Optional.empty() : Optional.of(unprocessedInvoices);
  }

  public static Boolean process(Invoice invoice) {
    if (!invoice.getIsSales()) {
      invoice.getLines().stream()
          .forEach((line) -> line.getProduct().setQty(line.getProduct().getQty() + line.getQty()));
      Db().save(invoice);
      return true;
    } else {
      invoice.getLines().stream()
          .filter(il -> il.getProduct().getQty() < il.getQty())
          .forEach(InvoiceLine::invalid);
      if (!invoice.getLines().stream().allMatch(InvoiceLine::getIsValid)) {
        invoice.setIsProcessed(false);
        Db().save(invoice);
        return false;
      } else {
        invoice
            .getLines()
            .forEach(
                (line) -> line.getProduct().setQty(line.getProduct().getQty() - line.getQty()));
        Db().save(invoice);
        return true;
      }
    }
  }
}
