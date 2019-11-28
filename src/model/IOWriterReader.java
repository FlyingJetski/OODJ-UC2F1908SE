package model;

import model.objects.*;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class IOWriterReader {
    public static int productId;
    public static int catalogueId;
    public static int categoryId;
    public static int supplierId;
    public static int userId;

    public static final String directoryName = System.getProperty("user.dir");
    public static final String dataDirectory = directoryName + "\\src\\data\\";
    public static final String resourcesDirectory = directoryName + "\\src\\resources\\";
    public static final File idFile = new File(dataDirectory+ "ID.txt");
    public static final File userFile = new File(dataDirectory+ "User.txt");
    public static final File productFile = new File(dataDirectory+ "Product.txt");
    public static final File catalogueFile = new File(dataDirectory+ "Catalogue.txt");
    public static final File categoryFile = new File(dataDirectory+ "Category.txt");
    public static final File supplierFile = new File(dataDirectory+ "Supplier.txt");
    public static final File loginLog = new File(dataDirectory+ "log\\Login.log");
    public static final File productLog = new File(dataDirectory+ "log\\Product.log");
    public static final File catalogueLog = new File(dataDirectory+ "log\\Catalogue.log");
    public static final File supplierLog = new File(dataDirectory+ "log\\Supplier.log");

    public static void onStartup() throws IOException, ParseException {
        // Read ID
        BufferedReader reader = new BufferedReader(new FileReader(idFile));
        productId = Integer.parseInt(reader.readLine());
        catalogueId = Integer.parseInt(reader.readLine());
        categoryId = Integer.parseInt(reader.readLine());
        supplierId = Integer.parseInt(reader.readLine());
        userId = Integer.parseInt(reader.readLine());

        String lineString;

        // Read data
        reader = new BufferedReader(new FileReader(productFile));
        while ((lineString = reader.readLine()) != null) {
            String[] productArray = lineString.split("\\|");
            Product.products.add(new Product(Integer.parseInt(productArray[0]), productArray[1], Integer.parseInt(productArray[2]),
                    Integer.parseInt(productArray[3]), Integer.parseInt(productArray[4]),
                    Double.parseDouble(productArray[5]), Double.parseDouble(productArray[6])));
        }

        reader = new BufferedReader(new FileReader(catalogueFile));
        while ((lineString = reader.readLine()) != null) {
            String[] catalogueArray = lineString.split("\\|");

            ArrayList<Integer> productId = new ArrayList<>();
            for (String product: catalogueArray[2].split("<>")) {
                productId.add(Integer.parseInt(product));
            }

            ArrayList<Double> productDiscount = new ArrayList<>();
            for (String discount: catalogueArray[3].split("<>")) {
                productDiscount.add(Double.parseDouble(discount));
            }

            Catalogue.catalogues.add(new Catalogue(Integer.parseInt(catalogueArray[0]), catalogueArray[1],
                    productId, productDiscount, Date.valueOf(catalogueArray[4]),
                    Date.valueOf(catalogueArray[5]), catalogueArray[6]));
        }

        reader = new BufferedReader(new FileReader(categoryFile));
        while ((lineString = reader.readLine()) != null) {
            String[] categoryArray = lineString.split("\\|");
            Category.categories.add(new Category(Integer.parseInt(categoryArray[0]), categoryArray[1]));
        }

        reader = new BufferedReader(new FileReader(supplierFile));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Supplier.suppliers.add(new Supplier(Integer.parseInt(logArray[0]), logArray[1], logArray[2],
                    logArray[3], logArray[4], logArray[5], logArray[6], logArray[7],
                    logArray[8], Boolean.valueOf(logArray[9])));
        }

        reader = new BufferedReader(new FileReader(userFile));
        while ((lineString = reader.readLine()) != null) {
            String[] userArray = lineString.split("\\|");
            if (userArray[3].equals("Product Manager")) {
                User.users.add(new Product_Manager(Integer.parseInt(userArray[0]), userArray[1], userArray[2],
                        userArray[4], userArray[5], userArray[6], userArray[7], Boolean.valueOf(userArray[8])));
            } else {
                User.users.add(new Administrator(Integer.parseInt(userArray[0]), userArray[1], userArray[2],
                        userArray[4], userArray[5], userArray[6], userArray[7], Boolean.valueOf(userArray[8])));
            }
        }

        // Read logs
        reader = new BufferedReader(new FileReader(loginLog));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Log.loginLogs.add(new Log(logArray[0], logArray[1], logArray[2]));
        }

        reader = new BufferedReader(new FileReader(productLog));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Log.productLogs.add(new Log(logArray[0], logArray[1], logArray[2]));
        }

        reader = new BufferedReader(new FileReader(catalogueLog));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Log.catalogueLogs.add(new Log(logArray[0], logArray[1], logArray[2]));
        }

        reader = new BufferedReader(new FileReader(supplierLog));
        while ((lineString = reader.readLine()) != null) {
            String[] logArray = lineString.split("\\|");
            Log.supplierLogs.add(new Log(logArray[0], logArray[1], logArray[2]));
        }

        reader.close();
    }

    public static void onExit() throws IOException {
        // Write ID
        BufferedWriter writer = new BufferedWriter(new FileWriter(idFile));
        writer.write(String.valueOf(productId));
        writer.newLine();
        writer.write(String.valueOf(catalogueId));
        writer.newLine();
        writer.write(String.valueOf(categoryId));
        writer.newLine();
        writer.write(String.valueOf(supplierId));
        writer.newLine();
        writer.write(String.valueOf(userId));
        writer.newLine();
        writer.flush();

        // Write data
        writer = new BufferedWriter(new FileWriter(productFile));
        for (Product product: Product.products) {
            writer.write(product.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(catalogueFile));
        for (Catalogue catalogue: Catalogue.catalogues) {
            writer.write(catalogue.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(categoryFile));
        for (Category category: Category.categories) {
            writer.write(category.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(supplierFile));
        for (Supplier supplier: Supplier.suppliers) {
            writer.write(supplier.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(userFile));
        for (User user: User.users) {
            writer.write(user.toString());
            writer.newLine();
        }
        writer.flush();

        // Write logs
        writer = new BufferedWriter(new FileWriter(loginLog));
        for (Log log: Log.loginLogs) {
            writer.write(log.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(productLog));
        for (Log log: Log.productLogs) {
            writer.write(log.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(catalogueLog));
        for (Log log: Log.catalogueLogs) {
            writer.write(log.toString());
            writer.newLine();
        }
        writer.flush();

        writer = new BufferedWriter(new FileWriter(supplierLog));
        for (Log log: Log.supplierLogs) {
            writer.write(log.toString());
            writer.newLine();
        }
        writer.flush();

        writer.close();
    }

    public static int getProductId() {
        productId++;
        return productId;
    }

    public static int getCatalogueId() {
        catalogueId++;
        return catalogueId;
    }

    public static int getCategoryId() {
        categoryId++;
        return categoryId;
    }

    public static int getSupplierId() {
        supplierId++;
        return supplierId;
    }

    public static int getUserId() {
        userId++;
        return userId;
    }

    /*public static int generateId(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int currentIdCount = Integer.parseInt(reader.readLine());
        reader.close();

        return currentIdCount + 1;
    }

    public static void updateIdCount(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine(); // Omit the ID Count (first line)

        int newIdCount = generateId(file);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        writer.write(String.valueOf(newIdCount));
        writer.newLine();

        String lineString;
        while ((lineString = reader.readLine()) != null) {
            writer.write(lineString);
            writer.newLine();
        }

        writer.flush();
        writer.close();
        reader.close();
    }*/
}
