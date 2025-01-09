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
import static org.junit.jupiter.api.Assertions.*;
import static stockperson.db.Db.Db;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;
import stockperson.model.Invoice;
import stockperson.service.csvloaders.InvoiceLoader;

class CsvServiceTest {

  @BeforeEach
  void clearState() {
    Db.clear();
  }

  @Test
  void test_invoicesFromCsv() throws IOException {
    // GIVEN
    var path = getClass().getResource("/stockperson-data-chapter1.0--set-01.csv").getFile();

    // WHEN
    CsvService.load(new File(path), new InvoiceLoader(true));

    // THEN
    assertThat(Db().getInvoices().stream().map(Invoice::getDocNo))
        .containsExactlyInAnyOrder("QIjWQ7", "VEyrc8", "i5tDrI", "SNYGhz", "hrLZGI");
  }
}
