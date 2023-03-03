import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class BasketTest extends TestCase {
    String[] products = {"Хлеб", "Яблоки", "Молоко"};
    Basket basket = new Basket(new int[products.length], new int[products.length], new int[products.length], new String[products.length], new int[products.length]);
    int[] prices = {25, 80, 35};
    int[] sum = {0, 0, 0};
    int[] amount = {1, 3, 10};
    int sumAll = 0;

    @Test
    public void testPrintCart() {

        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            sum[i] = amount[i] * prices[i];
            if (sum[i] > 0) {
                System.out.println(products[i] + " - " + amount[i] + " шт.; (" + prices[i] + " руб/шт); " + sum[i] + " рублей в сумме");
            }
        }


        for (int i = 0; i < products.length; i++) {
            sumAll = sumAll + sum[i];
        }
        System.out.println("ИТОГО: " + sumAll + " рублей");
        int result = 615;

        Assertions.assertEquals(sumAll,result);
    }

     int [] productNum = {2, 3, 1};
@Test
    public void testAddCart() {
    for (int i = 0; i < products.length; i++) {

        amount[i] += productNum[i];

    }
    int [] expected = {3,6,11};

    Assertions.assertArrayEquals(expected, amount);
    }

@Test
    public void testTestAddCart() {
        for (int i = 0; i < products.length; i++) {

            amount[i] += productNum[i];

        }
        Assertions.assertNotNull( amount);
    }
}