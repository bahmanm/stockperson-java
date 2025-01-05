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
import static stockperson.chapter1_0.models.InvoiceLine.Builder.anInvoiceLine;
import static stockperson.chapter1_0.models.Product.ProductBuilder.aProduct;

import org.junit.jupiter.api.Test;

class InvoiceLineTest {

  @Test
  void test_Builder() {
    // GIVEN
    var product = aProduct().code("foo").build();
    var line = anInvoiceLine().lineNo(10).product(product).price(100d).qty(2d).amt(200d).build();

    // EXPECT
    assertThat(line.getLineNo()).isEqualTo(10);
    assertThat(line.getPrice()).isEqualTo(100d);
    assertThat(line.getProduct()).isEqualTo(product);
    assertThat(line.getQty()).isEqualTo(2d);
    assertThat(line.getPrice()).isEqualTo(100d);
    assertThat(line.getAmt()).isEqualTo(200d);
  }

  @Test
  void test_equals() {
    // GIVEN
    var product = aProduct().code("foo").build();
    var line1 = anInvoiceLine().lineNo(10).product(product).price(100d).qty(2d).amt(200d).build();
    var line2 = anInvoiceLine().lineNo(20).product(product).price(100d).qty(2d).amt(200d).build();

    // EXPECT
    assertThat(line1.equals(line2)).isFalse();
  }

  @Test
  void test_toString() {
    // GIVEN
    var product = aProduct().code("foo").build();
    var line = anInvoiceLine().lineNo(10).product(product).price(100d).qty(2d).amt(200d).build();

    // EXPECT
    assertThat(line.toString()).isEqualTo("InvoiceLine(10, Product(foo), 2.00, 100.00, 200.00)");
  }
}
