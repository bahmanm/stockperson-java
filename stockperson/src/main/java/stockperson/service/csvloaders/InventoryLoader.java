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
package stockperson.service.csvloaders;

import static stockperson.db.Db.Db;
import static stockperson.model.Product.Builder.aProduct;

import java.util.function.Consumer;
import stockperson.db.Db;

public class InventoryLoader implements Consumer<String> {

  private static enum CsvFields {
    CODE,
    QTY
  }

  @Override
  public void accept(String csv) {
    var fields = csv.split(",");
    var p =
        aProduct()
            .code(fields[CsvFields.CODE.ordinal()])
            .qty(Double.parseDouble(fields[CsvFields.QTY.ordinal()]))
            .build();
    Db()
        .getProduct(p.getCode())
        .ifPresentOrElse(
            (existingProduct) -> existingProduct.setQty(p.getQty()), () -> Db().save(p));
  }
}
