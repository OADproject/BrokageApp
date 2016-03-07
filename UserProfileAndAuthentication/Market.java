import java.util.*;
public class Market
{
   private List<Stock> globalStocks = new ArrayList<Stock>();
   private List<BuySell> buyRequest = new ArrayList<BuySell>();
   private List<BuySell> sellRequest = new ArrayList<BuySell>();
   private boolean marketState;
    
    Market()
    {
        marketState = false;
    }
   
    public boolean startMarket()
    {
        marketState = true;
        return marketState;
    }
    public boolean stopMarket()
    {
        marketState = false;
        return marketState;
    }
    public boolean addStock(String name, double price,int qty)
    {
        Stock s = new Stock(name,price,qty);
        globalStocks.add(s);
        return true;
    }
    public boolean deleteStock(String name)
    {
        int len = globalStocks.size();
        boolean flag = false;
        for(Stock i : globalStocks)
        {
            String test = i.getName();
            if(test.equals(name))
            {
                globalStocks.remove(i);
                flag=true;
                break;
            }
        }
        return flag;
    }
    public List<Stock> getMarket()
    {
        return globalStocks;
    }
    public boolean getMarketState()
    {
        return marketState;
    }
    public boolean addBuyRequest(BuySell b)
    {
        buyRequest.add(b);
        return true;
    }
    public boolean addSellRequest(BuySell s)
    {
        sellRequest.add(s);
        return true;
    }
    
    
    
}
