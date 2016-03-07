import java.util.*;
import java.text.*;
public class TransactionHistory
{
    String timeStamp;
    int userID;
    String stockName;
    double stockUnitPrice;
    int stockQuantity;
    boolean buyOrSell; //buy= true, sell = false;
    
    
    TransactionHistory(int user_id,String stock_name,double stock_unit_price,int stock_quantity,boolean buy_sell)
    {
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        userID = user_id;
        stockName = stock_name;
        stockUnitPrice = stock_unit_price;
        stockQuantity = stock_quantity;
        buyOrSell = buy_sell;
        
    }
    public String getTimeStamp()
    {
        return timeStamp;
    }
    public int getUserID()
    {
        return userID;
    }
    public String getStockName()
    {
        return stockName;
    }
    public double getStockUnitPrice()
    {
        return stockUnitPrice;
    }
    public int getStockQuantity()
    {
        return stockQuantity;
    }
    public boolean setUserID(int us)
    {
        userID = us;
        return true;
    }
    public boolean setStockName(String sn)
    {
        stockName = sn;
        return true;
    }
    public boolean setStockUnitPrice(double sp)
    {
        stockUnitPrice = sp;
        return true;
    }
    public boolean setStockQuantity(int qty)
    {
        stockQuantity = qty;
        return true;
    }
    public boolean setBuyOrSell(boolean bs)
    {
        buyOrSell = bs;
        return true;
    }
    public boolean isBuy()
    {
        return buyOrSell;
    }
    public boolean isSell()
    {
        return !buyOrSell;
    }
  
    
}
