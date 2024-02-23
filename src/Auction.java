import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Auction {
    private class Triple<E, F, G>{
        protected E first;  //Agent
        protected F second; //Product
        protected G third;  //Bid amount int/double??

        public Triple(E first, F second, G third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public E getFirst() {
            return first;
        }

        public F getSecond() {
            return second;
        }

        public G getThird() {
            return third;
        }

    }

    private int numAgents;
    private HashSet<Agent> agents;
    private HashSet<Triple<Agent, Product, Double>> bids;
    private Triple<Agent, Product, Double> highestBid;

    public Auction(int numAgents, int productsPerAgent) {
        //setting defaults
        this.numAgents = numAgents;
        this.agents = new HashSet<>();

        //Construct Agents
        for(int i =0; i<numAgents; i++) {

            //reset list each time
            ArrayList<Product> list = new ArrayList<>();
            for (int j = 0; j < productsPerAgent; j++) {
                list.add(new Product("Product " + i + "," + j, Product.randomProductType()));
            }

            //generate random amount of money for new agent
            Random rand = new Random();
            //I'm assuming that "between 500 and 1000" is exclusive,
            //random is inclusive of bottom value exclusive of top value
            // 501-999 is possible with these values
            int randomMoney = rand.nextInt(501, 1000);

            //adding new Agent to list
            //note - Could have used addProducts Agent method here but that would require less efficient code:
            //constructing an agent -> calling addProducts -> adding to the agents hashset
            this.agents.add(new Agent(list, productsPerAgent, randomMoney));
        }
    }

    //Function to run a single auction, returning the winning Triple
    public Triple<Agent, Product, Double> singleAuction(Agent seller){
        //reset highestBid and bids
        this.bids = new HashSet<>();
        this.highestBid = new Triple<>(new Agent(new ArrayList<>(), 1, 1), new Product("",
                Product.ProductType.BOOK), (Double)0.1);

        //set random productType
        seller.setCurrentProduct(Product.randomProductType());
        Product product = seller.offerProduct();

        //check if Seller has valid Product, if not return null, cancelling the Auction
        if(product == null){
            System.err.println("Seller has no item of this type, Auction cancelled");
            return null;
        }

        //go set of Agents and place bid for each
        for(Agent a : this.agents){
            //stop seller bidding on their own item
            if(a != seller) {
                this.bids.add(new Triple<>(a, product, (double) a.makeBid(product)));
            }
        }
        //check for highest big placed
        for(Triple<Agent, Product, Double> t : this.bids){
            //if bids are matching, highestBid goes to whichever Agent is first found in the set
            if(t.getThird()>this.highestBid.getThird()){
                this.highestBid = t;
            }
        }

        /*
        Adjusting Agents
        If the either transaction fails, the auction is voided and both Agents remain unchanged
        Since the seller is checked when offerProduct is called, no need to check again.
        So buyer is evaluated first, in order to prevent seller gaining money if buyer buyProduct
        fails - this happened in my testing before it was in this order and the Seller would gain
        money when buyProduct and therefore singleAuction function failed.
        */

        for(Agent a : this.agents){
            //Finding buyer in hashset
            if (this.highestBid.getFirst() == a) {
                //Adjusting buyer
                //Since buyProduct and sellProduct take int parameters, int cast is needed for both function calls
                if (a.buyProduct(product, (int) Math.round(this.highestBid.getThird()))) {

                    //Adjust seller if buyProduct was true
                    seller.sellProduct(product, (int)Math.round(this.highestBid.getThird()));
                    System.out.println("Single Auction Successful");

                    //return highest bid for output
                    return this.highestBid;
                } else { //if buyProduct fails
                    System.err.println("Buyer is at max items or could not afford" +
                                       " Product, Auction cancelled");
                    return null;
                }
            }
        }
        //default return
        return null;
    }

    //method to run multiple singleAuction functions
    public void multipleAuctionSimulation(){
        //every Agent must sell once
        for(Agent a : this.agents){
            //try catch in case singleAuction fails meaning winningTriple cannot be read
            try {
                //print seller
                System.out.println("Seller :" + a);

                //call of singleAuction and read winner data
                Triple<Agent, Product, Double> winningTriple = singleAuction(a);

                //print winner data
                System.out.println("Winning Agent: " + winningTriple.getFirst().toString() + " bought a " +
                        winningTriple.getSecond().getType() + " for " + winningTriple.getThird() + "\n");

                //update productType prices
                winningTriple.getSecond().getType().setLastSalePrice((int) Math.round(winningTriple.getThird()));


            }catch(Exception e) {
                //No need to print message here as failure is already handled in
                //singleAuction with appropriate error message
            }
        }
    }
}
