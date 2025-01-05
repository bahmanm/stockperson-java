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
package stockperson.chapter1_0;

import static stockperson.chapter1_0.db.Db.Db;

import java.io.File;
import stockperson.chapter1_0.services.InvoicePrettyPrinter;
import stockperson.chapter1_0.services.InvoiceService;

public class Main {

  public static void main(String[] args) {
    InvoiceService.invoicesFromCsvFile(new File(args[0]));
    Db().getInvoices().stream()
        .sorted(
            (i1, i2) -> {
              return i1.getDate().before(i2.getDate()) ? 1 : -1;
            })
        .forEach(new InvoicePrettyPrinter());
  }
}
