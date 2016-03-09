import java.util.*;
import java.io.*;
import java.text.*;
public class Logger
{
    private static long lastCallUser = System.currentTimeMillis()/1000;
    private static long lastCallMarket = System.currentTimeMillis()/1000;
    
    
    public void updateLogs(List<User> userList, Market market) throws IOException
    {
        updateUserLogs(userList);
        updateMarketLogs(market);
    }
    
    public void updateUserLogs(List<User> userList) throws IOException
    {
        //FileWriter fw = new FileWriter(fileName,true);
        long currentTime = System.currentTimeMillis()/1000;
        if(lastCallUser - currentTime < 10)
        {
            return;
        }
        lastCallUser = System.currentTimeMillis()/1000;
       
        if(checkFiles(userList) == false)
        {
            initFiles(userList);
        }
        for(User i: userList)
        {
            FileWriter fw = new FileWriter("USR"+i.getID()+".csv",true);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            String data = sdf.format(d)+","+i.getPortfolio().getMoneyBalance()+",";
            fw.write(data);
            int x=0;
            for(Stock s: i.getPortfolio().getStocks())
            {
                String data2;
                if(x==0)
                {
                    data2 = s.getStockName()+","+s.getStockQty()+"\n";
                }
                else
                {
                    data2 = " , ,"+s.getStockName()+","+s.getStockQty()+"\n";
                }
                fw.write(data2);
                x++;
            }
            
        }
    }
    public void updateMarketLogs(Market market) throws IOException
    {
        long currentTime = System.currentTimeMillis()/1000;
        if(lastCallMarket - currentTime < 5)
        {
            return;
        }
        lastCallMarket = System.currentTimeMillis()/1000;
        
        if(checkFiles(market) == false)
        {
            initFiles(market);
        }
        List<Stock> globalStocks = market.getMarketStocks();
        List<BuySell> buyRequest = market.getBuyRequest();
        List<BuySell> sellRequest = market.getSellRequest();
        // String header = "Time,Stock Name, Market Vale, Buy Requests, Sell Requests\n";
        FileWriter fw = new FileWriter("MarketDatabase.csv",true);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        
        for(Stock s: globalStocks)
        {
            String data = sdf.format(d)+","+s.getStockName()+","+s.getUnitPrice()+","+buyRequest.size()+","+sellRequest.size()+"\n";
            fw.write(data);
        }
        
    }
    public boolean checkFiles(List<User> userList)
    {
        for(User i: userList)
        {
            File f = new File("USR"+i.getID()+".csv");
            if((f.exists() && !f.isDirectory()) == false)
            {
                return false;
            }
        }
        return true;
    }
    public void initFiles(List<User> userList) throws IOException
    {
        for(User i: userList)
        {
            
            FileWriter fw = new FileWriter("USR"+i.getID()+".csv",true);
            String header = "UserName:,"+i.getAuth().getUsername()+"\n"+"Name:,"+i.getName()+"\n"+"SSN:,"+i.getSSN()+"\n"+"Address:,"+i.getAddress()+"\n"+"Phone:,"+i.getPhoneNumber()+"\n\n";
            fw.write(header);
            String header2 = "Time,Balance,Stock Name,Stock Units\n";
            fw.write(header2);
        }
    }
    public boolean checkFiles(Market m)
    {
        File f = new File ("MarketDatabase.csv");
        if((f.exists() && !f.isDirectory())==false)
        {
            return false;
        }
        return true;
        
    }
    public void initFiles(Market m) throws IOException
    {
        FileWriter fw = new FileWriter("MarketDatabase.csv",true);
        String header = "Time,Stock Name, Market Value, Buy Requests, Sell Requests\n";
        fw.write(header);
    }
        
        
        
}

