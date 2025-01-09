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
import static stockperson.chapter2_0.Chapter2Service.*;
import static stockperson.db.Db.Db;
import static stockperson.model.Invoice.Builder.anInvoice;
import static stockperson.model.InvoiceLine.Builder.anInvoiceLine;
import static stockperson.model.Product.Builder.aProduct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;

class Chapter2ServiceTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

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

  @Test
  void test_getMostExpensiveProduct() {
    // GIVEN
    var p1 = aProduct().code("p1").build();
    var p2 = aProduct().code("p2").build();
    var i1 =
        anInvoice()
            .lines(Set.of(anInvoiceLine().lineNo(10).product(p1).price(100d).build()))
            .build();
    var i2 =
        anInvoice()
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(110d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(98d).build()))
            .build();
    Db().save(i1);
    Db().save(i2);

    // EXPECT
    assertThat(getMostExpensiveProduct()).isEqualTo(p1);
  }

  @Test
  void test_getAvgProductPrices() {
    var p1 = aProduct().code("p1").build();
    var p2 = aProduct().code("p2").build();
    var i1 =
        anInvoice()
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(100d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(40d).build()))
            .build();
    var i2 =
        anInvoice()
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(110d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(50d).build()))
            .build();
    Db().save(i1);
    Db().save(i2);

    // WHEN
    var actual = getAvgProductPrices();

    // THEN
    assertThat(actual)
        .hasEntrySatisfying(
            p1,
            (price) -> {
              if (price != 105d) {
                throw new RuntimeException();
              }
            });
    assertThat(actual)
        .hasEntrySatisfying(
            p2,
            (price) -> {
              if (price != 45d) {
                throw new RuntimeException();
              }
            });
  }

  @Test
  void test_getSalesByCustomer() {
    var p1 = aProduct().code("p1").build();
    var p2 = aProduct().code("p2").build();
    var i1 =
        anInvoice()
            .customer("customer")
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(100d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(40d).build()))
            .total(1800d)
            .build();
    var i2 =
        anInvoice()
            .customer("customer")
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(110d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(50d).build()))
            .total(2100d)
            .build();
    Db().save(i1);
    Db().save(i2);

    // WHEN
    var actual = getSalesByCustomer();

    // THEN
    assertThat(actual).containsEntry("customer", 3900d);
  }

  @Test
  void test_getSalesByDate() throws ParseException {
    // GIVEN
    var date = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-05");
    var p1 = aProduct().code("p1").build();
    var p2 = aProduct().code("p2").build();
    var i1 =
        anInvoice()
            .customer("customer")
            .date(date)
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(100d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(40d).build()))
            .total(1800d)
            .build();
    var i2 =
        anInvoice()
            .customer("customer")
            .date(date)
            .lines(
                Set.of(
                    anInvoiceLine().lineNo(10).product(p1).price(110d).build(),
                    anInvoiceLine().lineNo(20).product(p2).price(50d).build()))
            .total(2100d)
            .build();
    Db().save(i1);
    Db().save(i2);

    // EXPECT
    assertThat(getSalesByDate()).containsEntry(date, 3900d);
  }
}
