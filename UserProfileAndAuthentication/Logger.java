import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
public class Logger
{
    
    public void updateLogs(List<User> userList, Market market) throws IOException
    {
        updateUserLogs(userList);
        updateMarketLogs(market);
    }
    
    public void updateUserLogs(List<User> userList) throws IOException
    {
        //FileWriter fw = new FileWriter(fileName,true);
        if(checkFiles(userList) == false)
        {
            initFiles(userList);
        }
        for(User i: userList)
        {
        }
    }
    public void updateMarketLogs(Market market)
    {
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
        }
    }
}
