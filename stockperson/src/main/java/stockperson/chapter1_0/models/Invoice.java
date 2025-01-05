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

import java.util.Date;
import java.util.Set;

public class Invoice {

  private String docNo;
  private String customer;
  private Date date;
  private Double discount;
  private Double total;
  private Set<InvoiceLine> lines;

  private Invoice() {}

  public String getDocNo() {
    return docNo;
  }

  public String getCustomer() {
    return customer;
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

  public static class InvoiceBuiler {
    Invoice invoice = new Invoice();

    private InvoiceBuiler() {}

    public static InvoiceBuiler anInvoice() {
      return new InvoiceBuiler();
    }

    public InvoiceBuiler docNo(String docNo) {
      invoice.docNo = docNo;
      return this;
    }

    public InvoiceBuiler customer(String customer) {
      invoice.customer = customer;
      return this;
    }

    public InvoiceBuiler date(Date date) {
      invoice.date = date;
      return this;
    }

    public InvoiceBuiler discount(Double discount) {
      invoice.discount = discount;
      return this;
    }

    public InvoiceBuiler total(Double total) {
      invoice.total = total;
      return this;
    }

    public InvoiceBuiler lines(Set<InvoiceLine> lines) {
      invoice.lines = lines;
      return this;
    }

    public Invoice build() {
      return invoice;
    }
  }
}
