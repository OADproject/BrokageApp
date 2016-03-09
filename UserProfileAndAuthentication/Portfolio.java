

import java.util.*;
public class Portfolio
{
    private double moneyBalance;
    private List<Stock> stocks = new ArrayList<Stock>();
    private List<TransactionHistory> transactionHistory = new ArrayList<TransactionHistory>();
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
            String test = i.getStockName();
            if(test.equals(name))
            {
                stocks.remove(i);
                flag=true;
                break;
            }
        }
        return flag;
    }
    public boolean setMoneyBalance(double money)
    {
        moneyBalance = money;
        return true;
    }
    public List<Stock> getStocks()
    {
        return stocks;
    }
    public double getMoneyBalance()
    {
        return moneyBalance;
    }
    public boolean addTransaction(String stockName,double stockUnitPrice,int stockQuantity,boolean buyOrSell)
    {
        TransactionHistory t = new TransactionHistory(stockName,stockUnitPrice,stockQuantity,buyOrSell);
        transactionHistory.add(t);
        return true;
    }
    public List<TransactionHistory> getTransactionHistory()
    {
        return transactionHistory;
    }
    
    public boolean updatePortfolio(BuySell order){

        // TODO write code for updating
        return true;
    }
}
