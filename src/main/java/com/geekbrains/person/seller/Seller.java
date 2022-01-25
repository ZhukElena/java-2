package com.geekbrains.person.seller;

import com.geekbrains.person.Person;
import com.geekbrains.person.customer.Customer;
import com.geekbrains.product.Product;

import java.util.List;

public class Seller extends Person {
    private String firstName;
    private String lastName;
    private List<Product> products;

    public boolean sellProducts(Customer customer, Product expectedProduct) {
        for (Product product : products) {
            if (product.getName().equals(expectedProduct.getName())) {
                if (product.getQuantity() >= expectedProduct.getQuantity()) {
                    long requiredCash = (long) product.getPrice() * expectedProduct.getQuantity();
                    if (customer.getCash() >= requiredCash) {
                        product.setQuantity(product.getQuantity() - expectedProduct.getQuantity());

                        Product customerProduct = new Product();
                        customerProduct.setQuantity(expectedProduct.getQuantity());
                        customerProduct.setName(expectedProduct.getName());

                        customer.addPurchase(customerProduct);

                        setCash(getCash() + requiredCash);
                        customer.setCash(customer.getCash() - requiredCash);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
