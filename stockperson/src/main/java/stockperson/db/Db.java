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
package stockperson.db;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import stockperson.model.Invoice;
import stockperson.model.Product;

public class Db {

  private static Db instance = new Db();

  private Set<Product> products = new HashSet<>();
  private Set<Invoice> invoices = new HashSet<>();

  private Db() {}

  public static Db Db() {
    return instance;
  }

  public void save(Invoice invoice) {
    invoices.add(invoice);
  }

  public void save(Product product) {
    products.add(product);
  }

  public Set<Invoice> getInvoices() {
    return invoices;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public Optional<Invoice> getInvoice(String docNo) {
    return invoices.stream().filter((i) -> docNo.equals(i.getDocNo())).findFirst();
  }

  public Optional<Product> getProduct(String code) {
    return products.stream().filter((p) -> code.equals(p.getCode())).findFirst();
  }

  public static void clear() {
    instance.products = new HashSet<>();
    instance.invoices = new HashSet<>();
  }
}
