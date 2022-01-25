package com.geekbrains.person.customer;

import com.geekbrains.market.Market;
import com.geekbrains.person.Person;
import com.geekbrains.person.seller.Seller;
import com.geekbrains.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends Person {

    private final List<Product> expectedPurchaseList;
    private List<Product> purchaseList;
    private String sellerFirstName;
    private String sellerLastName;


    public Customer(List<Product> expectedPurchaseList, int cash) {
        this.purchaseList = new ArrayList<>();
        this.expectedPurchaseList = expectedPurchaseList;
        this.setCash(cash);
    }

    public void addPurchase(Product product) {
        if (purchaseList == null) {
            purchaseList = new ArrayList<>();
        }
        purchaseList.add(product);
    }

    public void findProductOnMarket(Market market) {
        for (Product product : getExpectedPurchaseList()) {

            boolean isBoughtFromOurDude = false;

            //ищем своего кента на рынке
            for (Seller seller : market.getSellers()) {
                if (Objects.equals(sellerLastName, seller.getLastName())
                        && Objects.equals(sellerFirstName, seller.getFirstName())) {
                    boolean isBought = seller.sellProducts(this, product);
                    if (isBought) {
                        isBoughtFromOurDude = true;
                        break;
                    }
                }
            }

            //если не купили у своего кента
            if (!isBoughtFromOurDude) {
                for (Seller seller : market.getSellers()) {
                    boolean isBought = seller.sellProducts(this, product);
                    if (isBought) {
                        break;
                    }
                }
            }
        }
    }

    public void info() {
        StringBuilder result = new StringBuilder("Я купил ");
        if (purchaseList.size() == 0) {
            result.append("ничего");
        } else {
            for (Product product : purchaseList) {
                result.append(product.getName());
                result.append(" в колличестве ");
                result.append(product.getQuantity());
                result.append(" ");
            }
        }
        result.append(". У меня осталось: ");
        result.append(getCash());
        result.append(" рублей");
        System.out.println(result);
    }

    public List<Product> getExpectedPurchaseList() {
        return expectedPurchaseList;
    }

    public List<Product> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Product> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public void setSellerFirstName(String sellerFirstName) {
        this.sellerFirstName = sellerFirstName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public void setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
    }
}
