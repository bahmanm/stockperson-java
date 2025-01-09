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

import stockperson.InsuffientInventory;
import stockperson.model.Product;

public class InventoryService {

  public static void qtyIn(Product product, Double qty) {
    Db().getProducts().stream()
        .filter(p -> p.equals(product))
        .findFirst()
        .ifPresent(
            p -> {
              p.setQty(qty + p.getQty());
            });
  }

  public static void qtyOut(Product product, Double qty) {
    Db().getProducts().stream()
        .filter(p -> p.equals(product))
        .findFirst()
        .ifPresent(
            p -> {
              if (p.getQty() < qty) {
                throw new InsuffientInventory(
                    "In stock: %.2f, requested %.2f".formatted(p.getQty(), qty));
              } else {
                p.setQty(p.getQty() - qty);
              }
            });
  }
}
