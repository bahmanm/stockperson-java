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

import java.io.File;

public class Main {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("USAGE: INVENTORY_FILE SALES_INVOICES_FILE PURCHASE_INVOICES_FILE");
      System.exit(0);
    }
    var inventoryFile = new File(args[0]);
    var salesInvoicesFile = new File(args[1]);
    var puchaseInvoicesFile = new File(args[2]);
  }
}
