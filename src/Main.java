import java.awt.print.Book;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        /*-------------------
        Product class testing
        -------------------*/

        System.out.println("\n------------------- \n PRODUCT TESTING \n------------------- \n");

        //Creating Products
        Product product1 = new Product("Head First Java", Product.ProductType.BOOK);
        Product product2 = new Product("Toby", Product.ProductType.COIN);
        Product product3 = new Product("Brazil", Product.ProductType.STAMP);

        //Serialising Products
        System.out.println("Serialising Products");
        writeToFile(product1, "Product1.ser");
        writeToFile(product2, "Product2.ser");
        writeToFile(product3, "Product3.ser");

        //Setting products sale price
        product1.setSalePrice(100);
        product2.setSalePrice(50);
        product3.setSalePrice(200);

        //Printing products
        System.out.println("\nPrinting Products");
        System.out.println(product1);
        System.out.println(product2);
        System.out.println(product3);

        //Reading products from serialised files
        System.out.println("\nReading serialised files");
        System.out.println(readFromFile("Product1.ser"));
        System.out.println(readFromFile("Product2.ser"));
        System.out.println(readFromFile("Product3.ser"));


        /*-------------------
        Agent class Testing
        -------------------*/

        System.out.println("\n------------------- \n AGENT TESTING \n------------------- \n");

        //creating Agent
        Agent myAgent = new Agent(1000);

        //buying products
        myAgent.buyProduct(new Product("book1", Product.ProductType.BOOK), 50);
        myAgent.buyProduct(new Product("book2", Product.ProductType.BOOK), 50);
        myAgent.buyProduct(new Product("coin1", Product.ProductType.COIN), 50);
        myAgent.buyProduct(new Product("Stamp1", Product.ProductType.STAMP), 50);
        myAgent.buyProduct(new Product("Stamp2", Product.ProductType.STAMP), 50);
        myAgent.buyProduct(new Product("Stamp3", Product.ProductType.STAMP), 50);

        //Printing BOOK products
        System.out.println("Printing Book Products:");
        for(Product p : myAgent.getProducts()){
            if(p.getType() == Product.ProductType.BOOK){
                System.out.println(p);
            }
        }
        //note could also use countProducts here but would need to know the value of BOOK
        //System.out.println(myAgent.countProducts()[0]);

        //testing setCurrentProduct
        myAgent.setCurrentProduct(Product.ProductType.STAMP);
        //testing offerProduct
        System.out.println("\nPrinting result of offerProcut method: ");
        System.out.println(myAgent.offerProduct());

        //testing sellProduct using offerProduct to garuntee it is the same Product as above
        System.out.println("\nPrinting sellProduct result: ");
        System.out.println(myAgent.sellProduct(myAgent.offerProduct(), 100));
        //printing remaining STAMP Products
        System.out.println("\nPrinting remaining STAMP Products: ");
        for(Product p : myAgent.getProducts()){
            if(p.getType() == Product.ProductType.STAMP){
                System.out.println(p);
            }
        }


        /*-------------------
        Auction class Testing
        -------------------*/

        System.out.println("\n------------------- \n AUCTION TESTING \n------------------- \n");

        //create Auction using constructor
        Auction auction = new Auction(4, 6);

        //run Auction simulation
        auction.multipleAuctionSimulation();

    }

    //method to serialise product objects
    public static void writeToFile(Product p1, String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(p1);
            out.close();
            fos.close();
            System.out.println("Saved file");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    //method to read serialised files and return a new Product instances
    public static Product readFromFile(String name){
        try {

            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(fis);
            Product result =  (Product)(in.readObject());
            in.close();
            fis.close();
            return result;
        }catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

}