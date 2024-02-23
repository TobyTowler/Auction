import java.util.ArrayList;

public class Agent{
    private ArrayList<Product> products;
    private int currentSize, money;
    private static final int MAX_SIZE = 100;
    private static Product.ProductType currentProduct = Product.ProductType.BOOK;

    //method to add Product objects to "products" arraylist


    public Agent(int money) {
        this.money = money;
        this.currentSize = 0;
        this.products = new ArrayList<>();
    }

    public Agent(ArrayList<Product> products, int currentSize, int money) {
        this.products = products;
        this.currentSize = currentSize;
        this.money = money;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public int getMoney() {
        return money;
    }

    public static Product.ProductType getCurrentProduct() {
        return currentProduct;
    }

    public void addProducts(ArrayList<Product> list){
        for(Product p : list){
            //check if Agent is at maximum Product count
            if(this.currentSize != MAX_SIZE) {
                this.products.add(p);
                this.currentSize++;
            }
        }
    }

    //function to sell Product, returns true if sold successfully
    public boolean sellProduct(Product p, int price){
        //check if Agent has the product
        if(this.products.contains(p)){
            this.products.remove(p);
            this.currentSize--;
            this.money += price;
            return true;
        }
        //false if not
        return false;
    }

    //function to buy Product, returns true if bought successfully
    public boolean buyProduct(Product p, int price){
        //if Agent has enough money and is not at max size
        if(this.money >= price && this.currentSize<MAX_SIZE){
            this.products.add(p);
            this.currentSize++;
            this.money -=price;
            return true;
        }
        //false if not
        return false;
    }

    //setter for current product
    public void setCurrentProduct(Product.ProductType p){
        currentProduct = p;
    }

    //function to check if Agent has product of type currentProduct, returning it if true
    public Product offerProduct(){
        for(Product p : this.products){
            if(p.getType()==currentProduct){
                return p;
            }
        }
        return null;
    }

    //function to calculate amount of each ProductType Agent has, returning int array in form
    //[BOOK, COIN, STAMP]
    public int[] countProducts(){
        int[] result = {0 ,0, 0}; //{BOOK, COIN, STAMP}
        //loop products list add 1 to relevant array position
        for(Product p : this.products){
            if(p.getType()== Product.ProductType.BOOK){
                result[0]++;
            }
            else if(p.getType()== Product.ProductType.COIN){
                result[1]++;
            }
            else if(p.getType()== Product.ProductType.STAMP){
                result[2]++;
            }
        }
        return result;
    }

    //fucntion returning Agents bid as int
    public int makeBid(Product p){
        int[] owned = this.countProducts();
        int numberAleadyOwned;

        if(currentProduct == Product.ProductType.BOOK){
            numberAleadyOwned = owned[0];
        }
        else if(currentProduct == Product.ProductType.COIN){
            numberAleadyOwned = owned[1];
        }
        else numberAleadyOwned = owned[2];



        //calculate bid based on numberAlreadyOwned
        if(numberAleadyOwned == 0){
            return this.money/4;
        }
        else if(numberAleadyOwned == 1){
            return this.money/5;
        }
        else if(numberAleadyOwned == 2){
            return this.money/6;
        }
        else return this.money/7;
    }

}
