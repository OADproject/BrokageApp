import org.omg.PortableInterceptor.USER_EXCEPTION;

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
            currentStockValues.put(s.toString().toLowerCase(),  100.00);
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
        currentStockValues.put(name,price);
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
//                System.out.println("Global Stocks: "+ s + " " + newStockVal);
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
        for (Integer id: allUsersTable.keySet() ){
            User u = allUsersTable.get(id);
            if(u.getName().equals(b.getUserName())) {
                u.getPortfolio().updatePortfolio(b);
            } else if(u.getName().equals(s.getUserName())){
                u.getPortfolio().updatePortfolio(s);
            }
        }
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

    public User returnUser(Authentication authentication){
        List<User> users = getUserList();
        for (User u: users) {
            if(u.getAuth() == authentication){
                return u;
            }
        }
        double balance = 88888;
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(StockType.AMAZON.toString().toLowerCase(),100,100));
        stocks.add(new Stock(StockType.GOOGLE.toString().toLowerCase(),100,100));
        stocks.add(new Stock(StockType.FACEBOOK.toString().toLowerCase(),100,100));
        List<TransactionHistory> histories = new ArrayList<>();
        Portfolio p = new Portfolio(balance,stocks,histories);
        User nu = new User(" "," "," "," ",p,authentication);

        return nu;
    }




    public BuySell createBuySell(String request) {
        Map<String, String> m = parse(request);
        // stockName:amazon,userId:3,userName:surag,
        BuySell b = new BuySell(m.get("stockName"), Integer.parseInt(m.get("userId")), m.get("userName"),
                Double.parseDouble(m.get("unitPrice")), Integer.parseInt(m.get("quantity")),
                m.get("buySell").toLowerCase().equals("true") ? true : false);

        return b;

    }

    public String parseStringCurrentStockValues(){
        Market m =  Market.getMarket();
        StringBuilder sb = new StringBuilder();
        Map<String,Double> vals =  m.getCurrentStockValues();
        for (String s: vals.keySet()
                ) {
            sb.append(s);
            sb.append(" : ");
            sb.append(vals.get(s));
            sb.append(",");
        }
       return  sb.toString();
    }


    public String getUserStringView(User user){
        StringBuilder sb = new StringBuilder();
        sb.append(parseStringCurrentStockValues());
        List<Stock> s = user.getPortfolio().getStocks();
        for (Stock x:s) {
            sb.append(","+x.getStockName()+"qty:"+x.getStockQty());
        }
        sb.append(",balance:" + user.getPortfolio().getMoneyBalance());
        return sb.toString();
    }

    public List<BuySell> createBuySellRequsets(String input){
        String[] reqs = input.split(";");
        List<BuySell> buySells = new ArrayList<>();
        for (String s: reqs
             ) {
            buySells.add(createBuySell(s));
        }
    return  buySells;
    }

    public HashMap<String, String> parse(String input) {
        String[] s = input.split(",");
        HashMap<String, String> m = new HashMap<>();
        for (String x : s) {
            String[] z = x.split(":");
            m.put(z[0], z[1]);
        }
        return m;
    }
}
