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
package stockperson.chapter3_0;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stockperson.db.Db;

class MainTest {

  private final PrintStream originalSystemOut = System.out;
  private ByteArrayOutputStream outputStream = null;
  private PrintStream systemOut = null;

  @AfterEach
  public void restoreSystemOut() {
    System.setOut(originalSystemOut);
    systemOut.close();
    systemOut = null;
    try {
      outputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      outputStream = null;
    }
  }

  @BeforeEach
  public void setupSystemOut() {
    outputStream = new ByteArrayOutputStream();
    systemOut = new PrintStream(outputStream);
    System.setOut(systemOut);
    Db.clear();
  }

  @Test
  void worksOk() throws IOException {
    // GIVEN
    var inventoryFilepath = getClass().getResource("/chapter-3.0-products.csv").getPath();
    var salesFilepath = getClass().getResource("/chapter-3.0-sales-invoices.csv").getPath();
    var purchaseFilepath = getClass().getResource("/chapter-3.0-purchase-invoices.csv").getPath();
    var expectedOutputFile = getClass().getResource("/expected-output-chapter3.0.txt").getFile();
    var expectedOutputFileReader = new FileReader(expectedOutputFile);
    var expectedOutputReader = new BufferedReader(expectedOutputFileReader);
    var expectedOutput = expectedOutputReader.lines().collect(joining("\n"));

    // WHEN
    Main.main(new String[] {inventoryFilepath, salesFilepath, purchaseFilepath});

    // THEN
    assertThat(outputStream.toString().trim()).isEqualTo(expectedOutput.trim());
  }
}
