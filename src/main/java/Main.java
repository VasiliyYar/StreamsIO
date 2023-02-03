import com.opencsv.CSVWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.sound.sampled.Line;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        //Создаем построитель документа
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Создается дерево DOM документа из файла. Используем уже имеющийся объект для парсинга xml
        Document doc = builder.parse("shop.xml");
        //Получаем корневой элемент
        Node root = doc.getDocumentElement();


        //Запрашиваем все элементы корневого элемента xml документа
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                System.out.println("Текущий узел: " + node.getNodeName());
                Element element = (Element) node;
                if (node.getNodeName().equals("load")) { //Если условие сработало
                    if (element.getAttribute("enabled").equals("false")) {
                        File file = new File(element.getAttribute("fileName"));
                        if (element.getAttribute("format").equals("json")) {
                            FileWriter in = new FileWriter("basket.json");
                        } else if (element.getAttribute("format").equals("txt")) {
                            FileWriter in = new FileWriter("basket.txt");
                        }

                    } else if (element.getAttribute("enabled").equals("true")) {
                        File file = new File(element.getAttribute("fileName"), String.valueOf(true));
                        if (element.getAttribute("format").equals("json")) {
                            FileWriter in = new FileWriter("basket.json", true);
                        } else if (element.getAttribute("format").equals("txt")) {
                            FileWriter in = new FileWriter("basket.txt", true);
                        }
                    }

                }
                if (node.getNodeName().equals("save")) { //Если условие сработало
                    if (element.getAttribute("enabled").equals("true")) {
                        File file = new File(element.getAttribute("fileName"), String.valueOf(true));
                        if (element.getAttribute("format").equals("json")) {
                            FileWriter out = new FileWriter("basket.json", true);
                        } else if (element.getAttribute("format").equals("txt")) {
                            FileWriter out = new FileWriter("basket.txt", true);
                        }
                    } else if (element.getAttribute("enabled").equals("false")) {
                        File file = new File(element.getAttribute("fileName"));
                        if (element.getAttribute("format").equals("json")) {
                            FileWriter out = new FileWriter("basket.json");
                        } else if (element.getAttribute("format").equals("txt")) {
                            FileWriter out = new FileWriter("basket.txt");
                        }

                    }

                }
                if (node.getNodeName().equals("log")) { //Если условие сработало
                    if (element.getAttribute("enabled").equals("true")) {
                        try (CSVWriter writer = new CSVWriter(new FileWriter("log.csv", true))) {

                        }

                    } else if (element.getAttribute("enabled").equals("false")) {
                        continue;
                    }
                }
            }
        }
        //Сохранение внесенных изменение в xml файл
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        FileOutputStream fos = new FileOutputStream("shop.xml");
        StreamResult result = new StreamResult(fos);
        tr.transform(source, result);

        System.out.println("Список возможных товаров для покупки:");
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        Basket basket = new Basket(new int[products.length], new int[products.length], new int[products.length], new String[products.length], new int[products.length]);
        Clientlog clientlog = new Clientlog(new int[products.length], new int[products.length], new String[products.length]);
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

            //basket.addCart(basket.getAmount(), basket.getProductNum());
            clientlog.addCart(basket.getAmount(), basket.getProductNum());


        }


        basket.printCart(basket.getAmount(), basket.getProductNum(), basket.getSum(), products, prices);

        basket.saveTxt(new File("basket.json"), products, prices);

        Basket.LoadFromTxtFile(new File("basket.json"));

        clientlog.exportASCSV(new File("log.csv"), basket.getAmount());
    }
}
