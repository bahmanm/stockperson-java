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
package stockperson.db;

import static org.assertj.core.api.Assertions.assertThat;
import static stockperson.db.Db.Db;
import static stockperson.model.Invoice.Builder.anInvoice;
import static stockperson.model.Product.Builder.aProduct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DbTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

  @Test
  void saveProduct() {
    // GIVEN
    var p1 = aProduct().code("p1").build();
    var p2 = aProduct().code("p2").build();

    // WHEN
    Db().save(p1);
    Db().save(p1);
    Db().save(p2);

    // EXPECT
    assertThat(Db().getProducts()).containsExactlyInAnyOrder(p1, p2);
  }

  @Test
  void saveInvoice() {
    // GIVEN
    var i1 = anInvoice().docNo("docNo1").build();
    var i2 = anInvoice().docNo("docNo2").build();

    // WHEN
    Db().save(i1);
    Db().save(i1);
    Db().save(i2);

    // EXPECT
    assertThat(Db().getInvoices()).containsExactlyInAnyOrder(i1, i2);
  }

  @Test
  void getInvoice() {
    // GIVEN
    var i1 = anInvoice().docNo("docNo1").build();

    // WHEN
    Db().save(i1);

    // EXPECT
    assertThat(Db().getInvoice("docNo1")).hasValue(i1);
    assertThat(Db().getInvoice("docNo2")).isEmpty();
  }

  @Test
  void getProduct() {
    // GIVEN
    var p1 = aProduct().code("p1").build();

    // WHEN
    Db().save(p1);

    // EXPECT
    assertThat(Db().getProduct("p1")).hasValue(p1);
    assertThat(Db().getProduct("p2")).isEmpty();
  }
}
