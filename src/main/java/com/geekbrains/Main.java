package com.geekbrains;

import com.geekbrains.market.Market;
import com.geekbrains.person.customer.Customer;
import com.geekbrains.person.seller.Seller;
import com.geekbrains.product.MarketConstants;
import com.geekbrains.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Market market = new Market();

        Seller firstSeller = createFirstSeller();
        Seller secondSeller = createSecondSeller();

        market.addSeller(firstSeller);
        market.addSeller(secondSeller);

        Customer customer = createFirstCustomer();
        customer.findProductOnMarket(market);
        customer.info();
    }

    private static Customer createFirstCustomer() {
        Product firstProduct = new Product();
        firstProduct.setName(MarketConstants.TOMATOES_PRODUCT_NAME);
        firstProduct.setQuantity(4);
        Product secondProduct = new Product();
        secondProduct.setName(MarketConstants.CUCUMBER_PRODUCT_NAME);
        secondProduct.setQuantity(2);
        Customer customer = new Customer(List.of(firstProduct, secondProduct), 50);
        customer.setSellerFirstName("Елена");
        customer.setSellerLastName("Жук");
        return customer;
    }

    private static Seller createFirstSeller() {
        Seller seller = new Seller();
        seller.setFirstName("Елена");
        seller.setLastName("Жук");
        seller.setCash(0);

        Product firstProduct = new Product();
        firstProduct.setName(MarketConstants.TOMATOES_PRODUCT_NAME);
        firstProduct.setPrice(10);
        firstProduct.setQuantity(2);

        Product secondProduct = new Product();
        secondProduct.setName(MarketConstants.CUCUMBER_PRODUCT_NAME);
        secondProduct.setPrice(8);
        secondProduct.setQuantity(1);

        List<Product> products = List.of(firstProduct, secondProduct);
        seller.setProducts(products);

        return seller;
    }

    private static Seller createSecondSeller() {
        Seller seller = new Seller();
        seller.setFirstName("Иван");
        seller.setLastName("Иванов");
        seller.setCash(100);

        Product firstProduct = new Product();
        firstProduct.setName(MarketConstants.TOMATOES_PRODUCT_NAME);
        firstProduct.setPrice(8);
        firstProduct.setQuantity(40);

        Product secondProduct = new Product();
        secondProduct.setName(MarketConstants.CUCUMBER_PRODUCT_NAME);
        secondProduct.setPrice(5);
        secondProduct.setQuantity(12);
        List<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        seller.setProducts(products);

        return seller;
    }
}

