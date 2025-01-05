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
package stockperson.chapter1_0.models;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static stockperson.chapter1_0.models.Invoice.Builder.anInvoice;
import static stockperson.chapter1_0.models.InvoiceLine.Builder.anInvoiceLine;
import static stockperson.chapter1_0.models.Product.ProductBuilder.aProduct;

import java.util.Date;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InvoiceTest {

  @Test
  void builder() {
    // GIVEN
    var date = new Date();
    var product = aProduct().code("product").build();
    var line = anInvoiceLine().lineNo(10).product(product).qty(10d).price(10d).amt(100d).build();
    var invoice =
        anInvoice()
            .docNo("docNo")
            .customer("customer")
            .date(date)
            .discount(10d)
            .total(100d)
            .lines(Set.of(line))
            .build();

    // EXPECT
    assertThat(invoice.getDocNo()).isEqualTo("docNo");
    assertThat(invoice.getCustomer()).isEqualTo("customer");
    assertThat(invoice.getDate()).isEqualTo(date);
    assertThat(invoice.getDiscount()).isEqualTo(10d);
    assertThat(invoice.getTotal()).isEqualTo(100d);
    assertThat(invoice.getLines()).isEqualTo(Set.of(line));
  }

  @Test
  void addLine() {
    // GIVEN
    var date = new Date();
    var product = aProduct().code("product").build();
    var line = anInvoiceLine().lineNo(10).product(product).qty(10d).price(10d).amt(100d).build();
    var invoice = anInvoice().build();

    // WHEN
    invoice.addLine(line);

    // THEN
    assertThat(invoice.getLines()).isEqualTo(Set.of(line));
  }

  @Test
  void testEquals() {
    // GIVEN
    var i1 = anInvoice().docNo("docNo1").build();
    var i2 = anInvoice().docNo("docNo2").build();
    var i3 = anInvoice().docNo("docNo1").build();

    // EXPECT
    assertThat(i1.equals(i2)).isFalse();
    assertThat(i1.equals(i3)).isTrue();
  }
}
