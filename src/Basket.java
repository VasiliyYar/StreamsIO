import java.io.*;
import java.util.Arrays;

public class Basket {

    protected int[] amount;
    protected int[] productNum;
    protected int[] sum;

    protected String[] products;
    protected int[] prices;

    protected int sumAll;


    public Basket(int[] amount, int[] productNum, int[] sum, String[] products, int[] prices) {
        this.amount = amount;
        this.productNum = productNum;
        this.sum = sum;
        this.products = products;
        this.prices = prices;
    }

    public void addCart(int[] amount, int[] productNum) {
        for (int i = 0; i < productNum.length; i++) {
            amount[i] += productNum[i];
        }
    }

    public void printCart(int[] amount, int[] productNum, int[] sum, String[] products, int[] prices) {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < productNum.length; i++) {
            sum[i] = amount[i] * prices[i];
            if (sum[i] > 0) {
                System.out.println(products[i] + " - " + amount[i] + " шт.; (" + prices[i] + " руб/шт); " + sum[i] + " рублей в сумме");
            }
        }


        for (int i = 0; i < productNum.length; i++) {
            sumAll = sumAll + sum[i];
        }
        System.out.println("ИТОГО: " + sumAll + " рублей");

    }


    public void saveTxt(File textFile, String[] products, int[] prices) throws IOException {

        try (FileWriter out = new FileWriter("basket.txt", true);) {
            out.append('\n');
            for (int i = 0; i < productNum.length; i++) {
                if (sum[i] > 0) {
                    out.write(products[i] + " - " + amount[i] + " шт.; (" + prices[i] + " руб/шт); " + sum[i] + " рублей в сумме");
                    out.append('\n');
                }
            }


            out.write("ИТОГО: " + getSumAll() + " рублей");
            out.append('\n');

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static Basket LoadFromTxtFile(File textFile) {
        try (FileReader in = new FileReader("basket.txt");) {

            int currentByte = 0;
            System.out.println();
            System.out.println("Начали читать файл");
            while ((currentByte = in.read()) != -1) {// -1 говорит о завершении процесса
                System.out.print(Character.toString(currentByte));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public int[] getProductNum() {
        return productNum;
    }

    public void setProductNum(int[] productNum) {
        this.productNum = productNum;
    }

    public int[] getSum() {
        return sum;
    }

    public void setSum(int[] sum) {
        this.sum = sum;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public int getSumAll() {
        return sumAll;
    }

    public void setSumAll(int sumAll) {

        this.sumAll = sumAll;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "amount=" + Arrays.toString(amount) +
                ", productNum=" + Arrays.toString(productNum) +
                ", sum=" + Arrays.toString(sum) +
                ", products=" + Arrays.toString(products) +
                ", prices=" + Arrays.toString(prices) +
                '}';
    }
}
