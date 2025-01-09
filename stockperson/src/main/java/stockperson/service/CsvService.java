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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import stockperson.StockPersonException;

public class CsvService {

  public static void load(File csvFile, Consumer<String> loader) {
    try {
      var fileReader = new FileReader(csvFile);
      var reader = new BufferedReader(fileReader);
      for (var line = reader.readLine(); line != null; line = reader.readLine()) {
        if (!line.isEmpty() && !line.startsWith("# ")) {
          loader.accept(line);
        }
      }
    } catch (IOException e) {
      throw new StockPersonException(e);
    }
  }
}
