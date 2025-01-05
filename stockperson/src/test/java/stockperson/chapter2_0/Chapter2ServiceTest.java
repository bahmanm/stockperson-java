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
package stockperson.chapter2_0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static stockperson.chapter2_0.Chapter2Service.getMostExpensiveInvoice;
import static stockperson.chapter2_0.Chapter2Service.getTotalSales;
import static stockperson.db.Db.Db;
import static stockperson.model.Invoice.Builder.anInvoice;

import org.junit.jupiter.api.Test;
import stockperson.db.Db;

class Chapter2ServiceTest {

  @Test
  void test_getTotalSales() {
    var i1 = anInvoice().total(100d).build();
    var i2 = anInvoice().total(200d).build();
    Db().save(i1);
    Db().save(i2);

    // EXPECT
    assertThat(getTotalSales()).isEqualTo(300d);
  }

  @Test
  void test_getMostExpensiveInvoice() {
    var i1 = anInvoice().total(100d).build();
    var i2 = anInvoice().total(200d).build();
    Db().save(i1);
    Db().save(i2);

    // EXPECT
    assertThat(getMostExpensiveInvoice().getTotal()).isEqualTo(200d);
  }
}
