public class Authentication
{
    private String username;
    private String password;
    
    public boolean setUsername(String usn)
    {
        username = usn;
        return true;
    }
    public boolean setPassword(String pwd)
    {
        password = pwd;
        return true;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
}