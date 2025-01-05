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
import static stockperson.chapter1_0.models.Product.ProductBuilder.aProduct;

import org.junit.jupiter.api.Test;

class ProductTest {

  @Test
  void test_Builder() {
    // GIVEN
    var product = aProduct().code("foo").build();

    // EXPECT
    assertThat(product.getCode()).isEqualTo("foo");
  }

  @Test
  void test_equals() {
    var p1 = aProduct().code("foo").build();
    var p2 = aProduct().code("bar").build();

    // EXPECT
    assertThat(p1.equals(p2)).isFalse();
  }

  @Test
  void test_toString() {
    // GIVEN
    var p1 = aProduct().code("foo").build();

    // EXPECT
    assertThat(p1.toString()).isEqualTo("Product(foo)");
  }
}
