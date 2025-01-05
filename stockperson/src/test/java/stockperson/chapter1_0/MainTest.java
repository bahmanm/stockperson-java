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
package stockperson.chapter1_0;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  }

  @Test
  void worksOk() throws IOException {
    // GIVEN
    var inputFilepath =
        getClass().getResource("/stockperson-data-chapter1.0--set-01.csv").getPath();
    var expectedOutputFile = getClass().getResource("/expected-output.txt").getFile();
    var expectedOutputFileReader = new FileReader(expectedOutputFile);
    var expectedOutputReader = new BufferedReader(expectedOutputFileReader);
    var expectedOutput = expectedOutputReader.lines().collect(Collectors.joining("\n"));

    // WHEN
    Main.main(new String[] {inputFilepath});

    // THEN
    assertThat(outputStream.toString().trim()).isEqualTo(expectedOutput.trim());
  }
}
