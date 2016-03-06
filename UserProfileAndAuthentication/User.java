public class User
{
    private String ID;
    private String name;
    private String SSN;
    private String address;
    private Portfolio p = new Portfolio();
    private Authentication a = new Authentication();
    
    public String getID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public String getSSN()
    {
        return SSN;
    }
    public String getAddress()
    {
        return address;
    }
    public Portfolio getPortfolio()
    {
        return p;
    }
    public Authentication getAuth()
    {
        return a;
    }
    public boolean setID(String i)
    {
        ID = i;
        return true;
    }
    public boolean setName(String n)
    {
        name = n;
        return true;
    }
    public boolean setSSN(String s)
    {
        SSN = s;
        return true;
    }
    public boolean setAddress(String a)
    {
        address =a;
        return true;
    }
    public boolean setAuth(String usn,String pwd)
    {
        a.setUsername(usn);
        a.setPassword(usn);
        return true;
    }
    
    
   
}
