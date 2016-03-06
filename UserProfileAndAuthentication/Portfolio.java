import java.util.*;
public class Portfolio
{
    private String ID;
    private double moneyBalance;
    private List<Stock> stocks = new ArrayList<Stock>();
    
    public boolean addStock(String name, double price,int qty)
    {
        Stock s = new Stock(name,price,qty);
        stocks.add(s);
        return true;
    }
    public boolean deleteStock(String name)
    {
        int len = stocks.size();
        boolean flag = false;
        for(Stock i : stocks)
        {
            String test = i.getName();
            if(test.equals(name))
            {
                stocks.remove(i);
                flag=true;
                break;
            }
        }
        return flag;
    }
  
    public boolean setID(String i)
    {
        ID= i;
        return true;
    }
    public boolean setMoneyBalance(double money)
    {
        moneyBalance = money;
        return true;
    }
    public String getID()
    {
        return ID;
    }
    public List<Stock> getStocks()
    {
        return stocks;
    }
    public double getMoneyBalance()
    {
        return moneyBalance;
    }

    
    
   
}
