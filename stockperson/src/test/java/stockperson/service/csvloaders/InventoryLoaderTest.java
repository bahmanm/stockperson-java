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

import static org.assertj.core.api.Assertions.assertThat;
import static stockperson.db.Db.Db;
import static stockperson.model.Product.Builder.aProduct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;

class InventoryLoaderTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

  @Test
  void accept_new_product() {
    // GIVEN
    var csv = "p1,100.00";

    // WHEN
    new InventoryLoader().accept(csv);

    // THEN
    assertThat(Db().getProduct("p1")).isPresent();
    assertThat(Db().getProduct("p1").get().getQty()).isEqualTo(100d);
  }

  @Test
  void accept_existing_product() {
    // GIVEN
    var csv = "p1,100.00";
    Db().save(aProduct().code("p1").qty(1d).build());

    // WHEN
    new InventoryLoader().accept(csv);

    // THEN
    assertThat(Db().getProduct("p1").get().getQty()).isEqualTo(100d);
  }
}
