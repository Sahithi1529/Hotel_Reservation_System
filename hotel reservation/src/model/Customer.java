package model;
import java.util.regex.Pattern;

public class Customer
{
    private String firstName;
    private String lastName;
    private final String email;
    private static final String regexForEmail =  "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";
    private static final Pattern expression = Pattern.compile(regexForEmail);

    public Customer(String fName, String lName, String mail)
    {
        if (!checkMail(mail))
        {
            throw new IllegalArgumentException("Please enter the correct email. We got the wrong format: " + mail);
        }
        this.firstName = fName;
        this.lastName = lName;
        this.email = mail;
    }

    private static boolean checkMail(String mail)
    {
        return expression.matcher(mail).matches();
    }

    public String getMailId()
    {
        return email;
    }

    public String getFirst()
    {
        return firstName;
    }

    public String getLast()
    {
        return lastName;
    }

    public void setFirst(String fName) {
        this.firstName = fName;
    }

    public void setLast(String lName) {
        this.lastName = lName;
    }

    @Override
    public String toString()
    {
        return "Customer Name : "+firstName+" "+lastName+"\n"+"Customer Email : "+email+"-------------------";
    }
}
