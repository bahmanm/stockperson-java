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
package stockperson.chapter1_0.services;

import static stockperson.chapter1_0.db.Db.Db;
import static stockperson.chapter1_0.models.Invoice.Builder.anInvoice;
import static stockperson.chapter1_0.models.InvoiceLine.Builder.anInvoiceLine;
import static stockperson.chapter1_0.models.Product.ProductBuilder.aProduct;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import stockperson.chapter1_0.exceptions.StockPersonException;

public class InvoiceService {

  private static enum CsvFields {
    DOC_NO,
    CUSTOMER,
    DATE,
    TOTAL,
    DISCOUNT,
    LINE_NO,
    PRODUCT,
    QTY,
    PRICE,
    LINE_AMT;
  }

  public static void invoiceFromCsv(String csv) {
    var fields = csv.split(",");
    var product =
        Db()
            .getProduct(fields[CsvFields.PRODUCT.ordinal()])
            .orElseGet(
                () -> {
                  var p = aProduct().code(fields[CsvFields.PRODUCT.ordinal()]).build();
                  Db().save(p);
                  return p;
                });
    var invoice =
        Db()
            .getInvoice(fields[CsvFields.DOC_NO.ordinal()])
            .orElseGet(
                () -> {
                  try {
                    var i =
                        anInvoice()
                            .docNo(fields[CsvFields.DOC_NO.ordinal()])
                            .customer(fields[CsvFields.CUSTOMER.ordinal()])
                            .date(
                                new SimpleDateFormat("yyyy-MM-dd")
                                    .parse(fields[CsvFields.DATE.ordinal()]))
                            .discount(Double.valueOf(fields[CsvFields.DISCOUNT.ordinal()]))
                            .total(Double.valueOf(fields[CsvFields.TOTAL.ordinal()]))
                            .build();
                    Db().save(i);
                    return i;
                  } catch (ParseException e) {
                    throw new StockPersonException(e);
                  }
                });
    var invoiceLine =
        anInvoiceLine()
            .lineNo(Integer.valueOf(fields[CsvFields.LINE_NO.ordinal()]))
            .product(product)
            .qty(Double.valueOf(fields[CsvFields.QTY.ordinal()]))
            .price(Double.valueOf(fields[CsvFields.PRICE.ordinal()]))
            .amt(Double.valueOf(fields[CsvFields.LINE_AMT.ordinal()]))
            .build();
    invoice.addLine(invoiceLine);
  }

  public static void invoicesFromCsvFile(File csvFile) {
    try {
      var fileReader = new FileReader(csvFile);
      var reader = new BufferedReader(fileReader);
      for (var line = reader.readLine(); line != null; line = reader.readLine()) {
        if (!line.isEmpty() && !line.startsWith("# ")) {
          invoiceFromCsv(line);
        }
      }
    } catch (IOException e) {
      throw new StockPersonException(e);
    }
  }
}
