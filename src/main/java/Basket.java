package main.java;

import java.io.*;

public class Basket implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private int sumProducts;
    private String[] product;
    private int[] price;
    private int[] numProducts = new int[3];


    public Basket(String[] product, int[] price) {
        this.product = product;
        this.price = price;
    }

    public Basket() {
    }

    public Basket(String[] product, int[] price, int[] numProducts, int sumProducts) {
        this.product = product;
        this.price = price;
        this.numProducts = numProducts;
        this.sumProducts = sumProducts;
    }

    public int getSumProducts() {
        return sumProducts;
    }

    public int[] getPrice() {
        return price;
    }

    public int[] getNumProducts() {
        return numProducts;
    }

    public String[] getProduct() {
        return product;
    }

    public void setSumProducts(int sumProducts) {
        this.sumProducts = sumProducts;
    }

    public void setPrice(int[] price) {
        this.price = price;
    }

    public void setNumProducts(int[] numProducts) {
        this.numProducts = numProducts;
    }

    public void setProduct(String[] product) {
        this.product = product;
    }


    public void addToCart(int productNumber, int productCount) {
        int currentPrice = price[productNumber];
        int sumYourProduct = currentPrice * productCount;
        sumProducts += sumYourProduct;
        numProducts[productNumber] += productCount;
    }


    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < numProducts.length; i++) {
            if (numProducts[i] != 0) {
                System.out.println(product[i] + " " + numProducts[i] + " шт." + price[i] + " руб/шт " + numProducts[i] * price[i] + " руб в сумме");
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }


    public void saveBin(File file) {
        try (FileOutputStream fos = new FileOutputStream("basket.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            Basket basket = new Basket(getProduct(), getPrice(), getNumProducts(), getSumProducts());
            oos.writeObject(basket);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = new Basket();
        try (FileInputStream fis = new FileInputStream("basket.bin");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }
}
