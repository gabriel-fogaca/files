package application;


import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.println("Enter file path: ");
        String strFile = sc.nextLine();

        File path = new File(strFile);
        String strFolder = path.getParent();

        boolean success = new File(strFolder + "\\out").mkdir();

        String targetPath = strFolder + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(strFile))) {
            String itemCsv = br.readLine();
            while (itemCsv != null) {

                String[] fields = itemCsv.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                list.add(new Product(name, price, quantity));

                itemCsv = br.readLine();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetPath))) {
                for (Product lists : list) {
                    bw.write(lists.getName() + ", " + String.format("%.2f", lists.total()));
                    bw.newLine();
                }
                System.out.println(targetPath + " CREATED!");
            }
            catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        sc.close();
    }
}
