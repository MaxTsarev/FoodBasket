import java.io.*;

public class Basket implements Serializable {
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
        try (FileOutputStream fos = new FileOutputStream("basket.json");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            Basket basket = new Basket(getProduct(), getPrice(), getNumProducts(), getSumProducts());
            oos.writeObject(basket);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = new Basket();
        try (FileInputStream fis = new FileInputStream("basket.json");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (String s : product) {
                out.print(s + " ");
            }
            out.println();
            for (int x : price)
                out.print(x + " ");
            out.println();
            for (int e : numProducts)
                out.print(e + " ");
            out.println();
            out.print(sumProducts);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File file) throws IOException {
        String[] product = new String[3];
        int[] price = new int[3];
        int[] numProduct = new int[3];
        int sumProducts = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("basket.txt"))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] part = text.split(" ");
                if (product[0] == null) {
                    int i = 0;
                    for (String s : part) {
                        product[i] = s;
                        i++;
                    }
                } else if (price[0] == 0) {
                    int i = 0;
                    for (String s : part) {
                        int a = Integer.parseInt(s);
                        price[i] = a;
                        i++;
                    }
                } else if (numProduct[0] == 0) {
                    int i = 0;
                    for (String s : part) {
                        int a = Integer.parseInt(s);
                        numProduct[i] = a;
                        i++;
                    }
                } else {
                    String s = part[0];
                    sumProducts = Integer.parseInt(s);
                }
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return new Basket(product, price, numProduct, sumProducts);
    }
}
