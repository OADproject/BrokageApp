import java.util.*;
public class Market
{
    private List<Stock> market = new ArrayList<Stock>();
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
        market.add(s);
        return true;
    }
    public boolean deleteStock(String name)
    {
        int len = market.size();
        boolean flag = false;
        for(Stock i : market)
        {
            String test = i.getName();
            if(test.equals(name))
            {
                market.remove(i);
                flag=true;
                break;
            }
        }
        return flag;
    }
    public List<Stock> getMarket()
    {
        return market;
    }
    public boolean getMarketState()
    {
        return marketState;
    }
    
}
