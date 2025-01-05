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

public class Product {

  private String code;

  private Product() {}

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "Product(%s)".formatted(code);
  }

  @Override
  public boolean equals(Object otherObj) {
    if (otherObj instanceof Product other) {
      return code.equals(other.code);
    } else {
      return false;
    }
  }

  public static class ProductBuilder {

    private Product product = new Product();

    private ProductBuilder() {}

    public static ProductBuilder aProduct() {
      return new ProductBuilder();
    }

    public ProductBuilder code(String code) {
      product.code = code;
      return this;
    }

    public Product build() {
      return product;
    }
  }
}
