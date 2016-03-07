public class BuySell
{
    String stockName;
    String userName;
    double unitPrice;
    int quantity;
    boolean buySell; //buy = true , sell = false
    BuySell(String sn,String un, double up,int qty,boolean bS)
    {
        stockName = sn;
        userName = un;
        unitPrice = up;
        quantity = qty;
        buySell = bS;
    }
    
    public String getStockName()
    {
        return stockName;
    }
    public String getUserName()
    {
        return userName;
    }
    public double getUnitPrice()
    {
        return unitPrice;
        
    }
    public int getQuantity()
    {
        return quantity;
    }
    public boolean isBuy()
    {
        return buySell;
    }
    public boolean isSell()
    {
        return !buySell;
    }
    
}
