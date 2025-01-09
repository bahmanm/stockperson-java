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
package stockperson.service;

import static org.assertj.core.api.Assertions.assertThat;
import static stockperson.db.Db.Db;
import static stockperson.model.Invoice.Builder.anInvoice;
import static stockperson.model.InvoiceLine.Builder.anInvoiceLine;
import static stockperson.model.Product.Builder.aProduct;
import static stockperson.service.InvoiceService.*;

import java.util.Date;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;
import stockperson.model.InvoiceLine;

class InvoiceServiceTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

  @Test
  void test_process_single_purchase_invoice() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var invoice =
        anInvoice()
            .purchaseInvoice()
            .docNo("docNo")
            .lines(Set.of(anInvoiceLine().lineNo(1).product(p1).qty(15d).build()))
            .build();

    // WHEN
    var result = process(invoice);

    // THEN
    assertThat(result).isTrue();
    assertThat(invoice.getLines().stream().allMatch(InvoiceLine::getIsValid)).isTrue();
    assertThat(p1.getQty()).isEqualTo(25d);
  }

  @Test
  void test_process_single_sales_invoice_ok() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var invoice =
        anInvoice()
            .salesInvoice()
            .docNo("docNo")
            .lines(Set.of(anInvoiceLine().lineNo(1).product(p1).qty(9d).build()))
            .build();

    // WHEN
    var result = process(invoice);

    // THEN
    assertThat(result).isTrue();
    assertThat(invoice.getLines().stream().allMatch(InvoiceLine::getIsValid)).isTrue();
    assertThat(p1.getQty()).isEqualTo(1d);
  }

  @Test
  void test_process_single_sales_invoice_invalid() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var invoice =
        anInvoice()
            .salesInvoice()
            .docNo("docNo")
            .lines(Set.of(anInvoiceLine().lineNo(1).product(p1).qty(11d).build()))
            .build();

    // WHEN
    var result = process(invoice);

    // THEN
    assertThat(result).isFalse();
    assertThat(invoice.getLines().stream().allMatch(InvoiceLine::getIsValid)).isFalse();
    assertThat(p1.getQty()).isEqualTo(10d);
  }

  @Test
  void test_process_invoices_invalid() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var date1 = new Date();
    var date2 = new Date();
    date2.setTime(date1.getTime() + 1_000);

    var invoice1 =
        anInvoice()
            .date(date1)
            .docNo("i1")
            .salesInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(11d).build()))
            .build();
    var invoice2 =
        anInvoice()
            .date(date2)
            .docNo("i2")
            .purchaseInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(10d).build()))
            .build();

    // WHEN
    var result = process(Set.of(invoice1, invoice2));

    // THEN
    assertThat(result).hasValue(Set.of(invoice1));
    assertThat(invoice1.getLines().stream().allMatch(InvoiceLine::getIsValid)).isFalse();
    assertThat(p1.getQty()).isEqualTo(20d);
  }

  @Test
  void test_process_invoices_ok() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var date1 = new Date();
    var date2 = new Date();
    date2.setTime(date1.getTime() + 1_000);

    var invoice1 =
        anInvoice()
            .date(date1)
            .docNo("i1")
            .salesInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(9d).build()))
            .build();
    var invoice2 =
        anInvoice()
            .date(date2)
            .docNo("i2")
            .purchaseInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(10d).build()))
            .build();

    // WHEN
    var result = process(Set.of(invoice1, invoice2));

    // THEN
    assertThat(result).isEmpty();
    assertThat(p1.getQty()).isEqualTo(11d);
  }

  @Test
  void test_process_invoices_sales_before_purchase() {
    // GIVEN
    var p1 = aProduct().code("p1").qty(10d).build();
    Db().save(p1);
    var date1 = new Date();
    var date2 = new Date();
    date2.setTime(date1.getTime() + 1_000);

    var invoice1 =
        anInvoice()
            .date(date1)
            .docNo("i2")
            .purchaseInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(10d).build()))
            .build();

    var invoice2 =
        anInvoice()
            .date(date2)
            .docNo("i1")
            .salesInvoice()
            .lines(Set.of(anInvoiceLine().product(p1).qty(15d).build()))
            .build();

    // WHEN
    var result = process(Set.of(invoice1, invoice2));

    // THEN
    assertThat(result).isEmpty();
    assertThat(p1.getQty()).isEqualTo(5d);
  }
}
