import java.util.*;
import java.util.concurrent.ThreadFactory;

public class Market extends Thread {


    private static Market marketInstance = null;
    private List<Stock> globalStocks = new ArrayList<Stock>();
    private static final List<BuySell> buyRequest = Collections.synchronizedList(new ArrayList<BuySell>());
    private static final List<BuySell> sellRequest = Collections.synchronizedList(new ArrayList<BuySell>());
    private static final Hashtable<Integer, User> allUsersTable = new Hashtable<>();
    //private static final List<User> allUsers = Collections.synchronizedList(new ArrayList<User>());


    private boolean marketState;

    private Map<String, Integer> stockTrendMap = new HashMap<>();
    private Map<String, Double> currentStockValues = new HashMap<>();
    private static double VARIANCE_FACTOR = 5;


    private void init() {
        createStocks(StockType.values());
        createStockTrendMap();
        addDummyCurrentValues();
        start();
    }

    @Override
    public void run() {
        while (true) {
            matchOrders();
            updateStockValues();
//            evaluateCurrentMarketValue();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDummyCurrentValues() {
        for (StockType s : StockType.values()
                ) {
            currentStockValues.put(s.toString().toLowerCase(), (double) 100);
        }
    }

    private void createStockTrendMap() {
        for (Stock s : globalStocks) {
            // Trend for all shares when market starts will be zero
            // positive trend will increase the value of stock and negative vice versa.
            stockTrendMap.put(s.getStockName(), 0);
        }
    }

    private void createStocks(StockType[] stocks) {
        for (StockType t : stocks
                ) {
            String name = t.toString().toLowerCase();
            double stockUnitPrice = 100;
            int stockQty = 0;
            addStock(name, stockUnitPrice, stockQty);
        }
    }

    private Market() {
        marketState = false;
        init();
    }

    public static Market getMarket() {
        if (marketInstance == null) {
            marketInstance = new Market();
        }
        return marketInstance;
    }

    public boolean startMarket() {
        marketState = true;
        return marketState;
    }

    public boolean stopMarket() {
        marketState = false;
        return marketState;
    }

    synchronized public boolean addStock(String name, double price, int qty) {
        Stock s = new Stock(name, price, qty);
        globalStocks.add(s);
        return true;
    }

    public boolean deleteStock(String name) {
        int len = globalStocks.size();
        boolean flag = false;
        for (Stock i : globalStocks) {
            String test = i.getStockName();
            if (test.equals(name)) {
                globalStocks.remove(i);
                flag = true;
                break;
            }
        }
        return flag;
    }

    public List<Stock> getMarketStocks() {
        return globalStocks;
    }

    public boolean getMarketState() {
        return marketState;
    }
    public List<User> getUserList()
    {
        return (List<User>)allUsersTable.values();
    }

    synchronized public boolean addBuyRequest(BuySell b) {
        buyRequest.add(b);
        return true;
    }

    synchronized public boolean addSellRequest(BuySell s) {
        sellRequest.add(s);
        return true;
    }

    synchronized public List<BuySell> getBuyRequest() {
        return buyRequest;
    }

    synchronized public List<BuySell> getSellRequest() {
        return sellRequest;
    }

    /**
     * Update Variance Factor
     * Update Portfolios
     * Update Transaction History
     */

    public void updateStockValues() {
        List<BuySell> buy = getBuyRequest();
        List<BuySell> sell = getSellRequest();
        for (BuySell x : buy) {
            int stockCount = stockTrendMap.get(x.getStockName());
            stockTrendMap.put(x.getStockName(), x.getQuantity() + stockCount);
        }

        for (BuySell x : sell) {
            int stockCount = stockTrendMap.get(x.getStockName());
            stockTrendMap.put(x.getStockName(), x.getQuantity() - stockCount);
        }
        evaluateCurrentMarketValue();
    }

    private boolean evaluateCurrentMarketValue() {
        double currentMarketValue = 0;

        for (String s : stockTrendMap.keySet()) {
            int buySellDiff = stockTrendMap.get(s);
//            System.out.println(s);
            double newStockVal = calculateNewVal(currentStockValues.get(s), buySellDiff);
            currentStockValues.put(s, newStockVal);
            if (s.equals("amazon")){
                System.out.println("Global Stocks: "+ s + " " + newStockVal);
            }

        }
        return true;
    }

    private double calculateNewVal(Double currentVal, int buySellDiff) {
        double newVal = currentVal;

        if (buySellDiff > 0) {
            newVal = currentVal + VARIANCE_FACTOR * Math.log10(buySellDiff);
        } else if (buySellDiff < 0) {
            newVal = currentVal - VARIANCE_FACTOR * Math.log10(buySellDiff);
        }
        return newVal;
    }

    public Map<String, Double> getCurrentStockValues() {
        return this.currentStockValues;
    }


    private boolean matchOrders() {
        boolean atLeastOneMatch = false;
        Iterator<BuySell> i = buyRequest.iterator();
        Iterator<BuySell> j = sellRequest.iterator();
        while(i.hasNext()) {
            BuySell temp1 = i.next();
            while(j.hasNext()) {
                BuySell temp2 = j.next();
                if (temp1.getStockName().equals(temp2.getStockName())) {

                    if (temp1.getQuantity() == temp2.getQuantity()) {
                        System.out.println("found match" + temp1.getStockName());
                        i.remove();
                        j.remove();
//                        updatePortfolio(b, s);
                        
                        atLeastOneMatch = true;
                    }
                }
            }
        }
        return atLeastOneMatch;
    }

    public boolean addUser(User user) {
        if (allUsersTable.containsValue(user)) {
            System.out.println("This user already exists");
            // log the same thing
            return false;

        } else {
            allUsersTable.put(user.getID(), user);
        }
        return true;

    }

    private void updatePortfolio(BuySell b, BuySell s) {
        User ub = allUsersTable.get(b.getUserId());
        ub.getPortfolio().updatePortfolio(b);

        User sb = allUsersTable.get(s.getUserId());
        sb.getPortfolio().updatePortfolio(s);
    }

    public static void main(String[] args) {
        Market m = Market.getMarket();
        m.addBuyRequest(new BuySell(StockType.AMAZON.toString().toLowerCase(), 1, "sur", 100, 100, true));
        m.addSellRequest(new BuySell(StockType.AMAZON.toString().toLowerCase(), 1, "sur", 100, 100, false));
//        m.addSellRequest(new BuySell(StockType.AMAZON.toString(),1,"sun",100, 100, false));
//        m.matchOrders();
//        m.updateStockValues();
//        m.evaluateCurrentMarketValue();
    }
}
