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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Invoice {

  private Boolean isSales = true;
  private String docNo;
  private String businessPartner;
  private Date date;
  private Double discount;
  private Double total;
  private Set<InvoiceLine> lines = new HashSet<>();
  private Boolean isProcessed = false;

  private Invoice() {}

  public String getDocNo() {
    return docNo;
  }

  public String getBusinessPartner() {
    return businessPartner;
  }

  public Date getDate() {
    return date;
  }

  public Double getDiscount() {
    return discount;
  }

  public Double getTotal() {
    return total;
  }

  public Set<InvoiceLine> getLines() {
    return lines;
  }

  public void addLine(InvoiceLine line) {
    lines.add(line);
  }

  public Boolean getProcessed() {
    return isProcessed;
  }

  public void setIsProcessed(Boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

  public Boolean getIsSales() {
    return isSales;
  }

  @Override
  public String toString() {
    return "Invoice(%s, %s, %s, %.2f, %.2f, [%s])"
        .formatted(
            docNo,
            businessPartner,
            new SimpleDateFormat("yyyy-MM-dd").format(date),
            discount,
            total,
            lines.stream().map(InvoiceLine::toString).collect(Collectors.joining(",")));
  }

  @Override
  public boolean equals(Object otherObj) {
    if (otherObj instanceof Invoice other) {
      return docNo.equals(other.docNo);
    } else {
      return false;
    }
  }

  public static class Builder {
    Invoice invoice = new Invoice();

    private Builder() {}

    public static Builder anInvoice() {
      return new Builder();
    }

    public Builder docNo(String docNo) {
      invoice.docNo = docNo;
      return this;
    }

    public Builder customer(String customer) {
      invoice.businessPartner = customer;
      return this;
    }

    public Builder date(Date date) {
      invoice.date = date;
      return this;
    }

    public Builder discount(Double discount) {
      invoice.discount = discount;
      return this;
    }

    public Builder total(Double total) {
      invoice.total = total;
      return this;
    }

    public Builder lines(Set<InvoiceLine> lines) {
      invoice.lines = lines;
      return this;
    }

    public Builder salesInvoice() {
      invoice.isSales = true;
      return this;
    }

    public Builder purchaseInvoice() {
      invoice.isSales = false;
      return this;
    }

    public Invoice build() {
      return invoice;
    }
  }
}
