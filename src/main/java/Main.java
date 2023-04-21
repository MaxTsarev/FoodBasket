import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("basket.json");
        File fileCsv = new File("log.csv");
        Gson gson = new Gson();

        String jsonString;
        String[] product = {"Хлеб", "Яблоки", "Молоко"};
        int[] price = {30, 150, 80};

        Basket basket = new Basket();

        if (file.exists()) {
            jsonString = Files.readString(Paths.get(String.valueOf(file)));
            basket = gson.fromJson(jsonString, Basket.class);
        } else {
            basket.setProduct(product);
            basket.setPrice(price);
        }


        System.out.println("Список возможных товаров для покупки:");

        int num = 1;
        for (int i = 0; i < product.length; i++) {
            System.out.println(num + ". " + product[i] + " " + price[i] + " руб/шт");
            num++;
        }


        while (true) {
            System.out.println("Введите номер товара и количество, или введите end:");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();


            if (input.equals("end")) {
                break;
            }


            String[] parts = input.split(" ");
            int productNumber;
            int productCount;


            try {
                productNumber = Integer.parseInt(parts[0]) - 1;
                productCount = Integer.parseInt(parts[1]);
            } catch (RuntimeException e) {
                System.out.println("Нужно ввести два числа через пробел! Вы ввели: " + input);
                continue;
            }

            if (parts.length != 2) {
                System.out.println("Вводимое значение должно состоять из двух чисел! Ваш ввод: " + input);
                continue;
            }
            if (productNumber < 0 | productNumber > 2) {
                System.out.println("Некорректный ввод номера продукта! Нужно ввести от 1 до 3. Вы ввели: " + (productNumber + 1));
                continue;
            }
            if (productCount <= 0) {
                System.out.println("Некорректный ввод количества продуктов! Ввод не должен быть нулём и отрицательным числом! Вы ввели: " + productCount);
                continue;
            }

            ClientLog.log(productNumber, productCount);
            basket.addToCart(productNumber, productCount);
        }

        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        jsonString = gson.toJson(basket);
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientLog.exportAsCSV(fileCsv);
        basket.printCart();
    }
}

