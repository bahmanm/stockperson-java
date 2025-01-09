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

import static org.assertj.core.api.Assertions.assertThat;
import static stockperson.chapter3_0.InventoryService.qtyIn;
import static stockperson.chapter3_0.InventoryService.qtyOut;
import static stockperson.db.Db.Db;
import static stockperson.model.Product.Builder.aProduct;

import org.junit.jupiter.api.Test;
import stockperson.db.Db;

class InventoryServiceTest {

  @Test
  void test_qtyIn() {
    // GIVEN
    var p1 = aProduct().qty(10d).code("p1").build();
    Db().save(p1);

    // WHEN
    qtyIn(p1, 10d);

    // THEN
    assertThat(p1.getQty()).isEqualTo(20d);
  }

  @Test
  void test_qtyOut_ok() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);

    // WHEN
    qtyOut(p1, 9d);

    // THEN
    assertThat(p1.getQty()).isEqualTo(1d);
  }
}
