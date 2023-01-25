import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Clientlog {
    protected int[] amount;
    protected int[] productNum;
    protected int[] sum;

    protected String[] products;
    protected int[] prices;

    protected int sumAll;

    public Clientlog(int[] amount, int[] productNum, String[] products) {
        this.amount = amount;
        this.productNum = productNum;
        this.products = products;


    }

    public void addCart(int[] amount, int[] productNum) {
        for (int i = 0; i < productNum.length; i++) {
            amount[i] += productNum[i];

        }
    }


    public void exportASCSV(File txtFile, int[] amount) {
        try (CSVWriter writer = new CSVWriter(new FileWriter("log.csv", true))) {
            writer.writeNext(new String[]{"productNum,amount"});

            for (int i = 0; i < productNum.length; i++) {
                if (amount[i] > 0) {
                    writer.writeNext(new String[]{String.valueOf((i + 1)), String.valueOf(amount[i])});
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public void setProductNum(int[] productNum) {
        this.productNum = productNum;
    }

    public int[] getAmount() {
        return amount;
    }

    public int[] getProductNum() {
        return productNum;
    }
}
