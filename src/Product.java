import java.io.Serializable;
import java.util.Random;

public class Product implements Serializable {
    public enum ProductType {
        BOOK,
        COIN,
        STAMP;

        private int lastSalePrice = 0, maxSalePrice = 0;

        public int getLastSalePrice() {
            return lastSalePrice;
        }

        public void setLastSalePrice(int lastSalePrice) {
            this.lastSalePrice = lastSalePrice;
            if(lastSalePrice>this.maxSalePrice){
                this.maxSalePrice = lastSalePrice;
            }
        }

        public int getMaxSalePrice() {
            return maxSalePrice;
        }

        public void setMaxSalePrice(int maxSalePrice) {
            this.maxSalePrice = maxSalePrice;
        }
    };
    private ProductType type;
    private int salePrice;
    private String name;

    public Product(String name, ProductType type) {
        this.type = type;
        this.name = name;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public ProductType getType() {
        return type;
    }

    //toString fucntion for Product objects in the form, "name, type, salePrice"
    @Override
    public String toString(){
        return this.name + ", " +  this.type + ", " + this.salePrice;
    }

    //function to generate random ProductType value for Auction constructor
    static ProductType randomProductType(){
        Random rand = new Random();
        int type = rand.nextInt(3);
        if(type == 0) return ProductType.BOOK;
        else if(type == 1) return ProductType.COIN;
        else return ProductType.STAMP;
    }
}
