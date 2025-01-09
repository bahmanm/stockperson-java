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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;
import stockperson.model.Invoice;
import stockperson.model.InvoiceLine;

class InvoiceServiceTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

  @Test
  void test_invoiceFromCsv_existingData() throws Exception {
    // GIVEN
    var csv =
        "QIjWQ7,BOyUMON,2023-11-22,403388202.50,9.25,1,aF1KeUz,580,78933.39,45781371.59605947560";
    Db().save(aProduct().code("aF1KeUz").build());
    Db()
        .save(
            anInvoice()
                .docNo("QIjWQ7")
                .customer("BOyUMON")
                .date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-22"))
                .discount(9.25d)
                .total(403388202.50d)
                .build());

    // WHEN
    invoiceFromCsv(csv, true);

    // THEN
    assertThat(Db().getInvoice("QIjWQ7")).isPresent();
    var invoice = Db().getInvoice("QIjWQ7").get();
    assertThat(invoice.getLines())
        .containsExactlyInAnyOrder(
            anInvoiceLine()
                .lineNo(1)
                .product(Db().getProduct("aF1KeUz").get())
                .qty(580d)
                .price(78933.39d)
                .amt(45781371.59605947560d)
                .build());
  }

  @Test
  void test_invoiceFromCsv_nonExistingData() throws Exception {
    // GIVEN
    var csv =
        "QIjWQ7,BOyUMON,2023-11-22,403388202.50,9.25,1,aF1KeUz,580,78933.39,45781371.59605947560";
    Db()
        .save(
            anInvoice()
                .docNo("QIjWQ7")
                .customer("BOyUMON")
                .date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-22"))
                .discount(9.25d)
                .total(403388202.50d)
                .build());

    // WHEN
    invoiceFromCsv(csv, true);

    // THEN
    assertThat(Db().getProduct("aF1KeUz")).hasValue(aProduct().code("aF1KeUz").build());
    assertThat(Db().getInvoice("QIjWQ7"))
        .hasValue(
            anInvoice()
                .docNo("QIjWQ7")
                .customer("BOyUMON")
                .date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-22"))
                .discount(9.25d)
                .total(403388202.50d)
                .lines(
                    Set.of(
                        anInvoiceLine()
                            .lineNo(1)
                            .product(Db().getProduct("aF1KeUz").get())
                            .qty(580d)
                            .price(78933.39d)
                            .amt(45781371.59605947560d)
                            .build()))
                .build());
  }

  @Test
  void test_invoiceFromCsv_addsLine() throws Exception {
    // GIVEN
    var csv =
        "QIjWQ7,BOyUMON,2023-11-22,403388202.50,9.25,2,KWrllAa,321,4125.14,1324172.502135007716";
    Db().save(aProduct().code("aF1KeUz").build());
    Db().save(aProduct().code("KWrllAa").build());
    Db()
        .save(
            anInvoice()
                .docNo("QIjWQ7")
                .customer("BOyUMON")
                .date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-22"))
                .discount(9.25d)
                .total(403388202.50d)
                .lines(
                    new HashSet<>(
                        Set.of(
                            anInvoiceLine()
                                .lineNo(1)
                                .product(Db().getProduct("aF1KeUz").get())
                                .qty(580d)
                                .price(78933.39d)
                                .amt(45781371.59605947560d)
                                .build())))
                .build());

    // WHEN
    invoiceFromCsv(csv, true);

    // THEN
    assertThat(Db().getInvoice("QIjWQ7")).isPresent();
    var invoice = Db().getInvoice("QIjWQ7").get();
    assertThat(invoice.getLines())
        .contains(
            anInvoiceLine()
                .lineNo(2)
                .product(Db().getProduct("KWrllAa").get())
                .qty(321d)
                .price(4125.14d)
                .amt(1324172.502135007716d)
                .build());
  }

  @Test
  void test_invoicesFromCsv() throws IOException {
    // GIVEN
    var path = getClass().getResource("/stockperson-data-chapter1.0--set-01.csv").getFile();

    // WHEN
    invoicesFromCsvFile(new File(path), true);

    // THEN
    assertThat(Db().getInvoices().stream().map(Invoice::getDocNo))
        .containsExactlyInAnyOrder("QIjWQ7", "VEyrc8", "i5tDrI", "SNYGhz", "hrLZGI");
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
