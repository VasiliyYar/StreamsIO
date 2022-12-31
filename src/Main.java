import javax.sound.sampled.Line;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки:");
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        Basket basket = new Basket(new int[products.length], new int[products.length], new int[products.length], new String[products.length], new int[products.length]);
        int[] prices = {25, 80, 35};
        for (int i = 0; i < products.length; i++) {

            System.out.println("№ " + (i + 1) + "." + " " + products[i] + " - " + prices[i] + " руб/шт");
        }


        while (true) {
            Arrays.fill(basket.productNum, 0);

            System.out.println("Выберите номер товар и его количество или введите end");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }

            String[] parts = input.split(" ");

            if (parts.length != 2) {
                System.out.println("Необходимо внести два значения: номер товара и его количество, через пробел!");
                System.out.println();
                continue;
            }
            try {
                int productNumber = Integer.parseInt(parts[0]) - 1;
                int productCount = Integer.parseInt(parts[1]);

                if (productNumber >= products.length || productNumber < 0) {
                    System.out.println("Номер выбранного товара не может быть отрицательным, нулевым или больше " + (products.length));
                    System.out.println();
                    continue;
                }


                if (productCount <= 0) {
                    System.out.println("Количество выбранного товара не может быть отрицательным или равно 0");
                    System.out.println();
                    continue;
                }
                basket.getProductNum()[productNumber] = productCount;

            } catch (NumberFormatException e) {
                System.out.println("Введены некоректные данные !!!");
                System.out.println();
                continue;
            }

            basket.addCart(basket.getAmount(), basket.getProductNum());

        }

        basket.printCart(basket.getAmount(), basket.getProductNum(), basket.getSum(), products, prices);


        basket.saveTxt(new File("basket.txt"), products, prices);

        Basket.LoadFromTxtFile(new File("basket.txt"));


    }
}
