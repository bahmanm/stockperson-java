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
package stockperson.chapter3_0;

import static stockperson.db.Db.Db;
import static stockperson.service.CsvService.load;
import static stockperson.service.InvoiceService.process;

import java.io.File;
import stockperson.service.InvalidInvoicePrettyPrinter;
import stockperson.service.csvloaders.InventoryLoader;
import stockperson.service.csvloaders.InvoiceLoader;

public class Main {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("USAGE: INVENTORY_FILE SALES_INVOICES_FILE PURCHASE_INVOICES_FILE");
      System.exit(0);
    }

    load(new File(args[0]), new InventoryLoader());
    load(new File(args[1]), new InvoiceLoader(true));
    load(new File(args[2]), new InvoiceLoader(false));

    var invalidInvoices = process(Db().getInvoices());
    invalidInvoices.ifPresent(
        invoices -> {
          invoices.stream()
              .sorted((i1, i2) -> i1.getDate().before(i2.getDate()) ? -1 : 1)
              .forEach(new InvalidInvoicePrettyPrinter());
        });
  }
}
