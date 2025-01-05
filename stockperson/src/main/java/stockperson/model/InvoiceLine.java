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
package stockperson.model;

public class InvoiceLine {

  private Integer lineNo;
  private Product product;
  private Double qty;
  private Double price;
  private Double amt;

  private InvoiceLine() {}

  public Integer getLineNo() {
    return lineNo;
  }

  public Product getProduct() {
    return product;
  }

  public Double getQty() {
    return qty;
  }

  public Double getPrice() {
    return price;
  }

  public Double getAmt() {
    return amt;
  }

  @Override
  public String toString() {
    return "InvoiceLine(%d, %s, %.2f, %.2f, %.2f)".formatted(lineNo, product, qty, price, amt);
  }

  @Override
  public boolean equals(Object otherObj) {
    if (otherObj instanceof InvoiceLine other) {
      return lineNo.equals(other.lineNo)
          && product.equals(other.product)
          && qty.equals(other.qty)
          && price.equals(other.price);
    } else {
      return false;
    }
  }

  public static class Builder {
    private InvoiceLine line = new InvoiceLine();

    private Builder() {}

    public static Builder anInvoiceLine() {
      return new Builder();
    }

    public Builder lineNo(Integer lineNo) {
      line.lineNo = lineNo;
      return this;
    }

    public Builder product(Product product) {
      line.product = product;
      return this;
    }

    public Builder qty(Double qty) {
      line.qty = qty;
      return this;
    }

    public Builder price(Double price) {
      line.price = price;
      return this;
    }

    public Builder amt(Double amt) {
      line.amt = amt;
      return this;
    }

    public InvoiceLine build() {
      return line;
    }
  }
}
