
package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class RecieveInfo {
    
    String userName,password;
    boolean newUser,validUser, alreadyExist;
    BufferedWriter fout;
    FileReader fin;
    Scanner sc;
    RecieveInfo() 
    {
        
    }
    void process(String processedString)
    {
        System.out.println(processedString);
        if(processedString.charAt(0)=='1')
            newUser = true;
        else
            newUser = false;
        processedString = processedString.substring(1);
        int i=0;
        while(true)
        {
            try{
            if(processedString.charAt(i)==32)
            {
                userName = processedString.substring(0, i);
                password = processedString.substring(i+1);
                break;
            }
            i++;
                
            }
            catch(Exception e)
            {
                System.out.println("Bound of Array");
                break;
            }
        }
    }
    void recieveAndWrite() throws IOException
    {
        if(newUser)
        {
            write();
            validUser = false;
        }
        else
        {
            alreadyExist = false;
            validUser = checkValidity();
           // System.out.println(validUser);
        }
    }
    void write() throws IOException
    {
        FileReader fin = new FileReader("userinfo.txt");
        Scanner sc = new Scanner(fin);
        String s[]= new String[1000003];
        int sz = 0;
        while(sc.hasNext())
        {
           s[sz++] = sc.nextLine();
        }
        for(int i = 0; i < sz; i++)
        {
            if(getName(s[i]).equals(userName))
            {
                alreadyExist = true;
                return;
            }
        }
        s[sz] = userName+" "+password;
        BufferedWriter fout =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("userinfo.txt"))));
        int i = 0;
        while(i <= sz)
        {
            fout.write(s[i]);
            fout.newLine();
            i++;
        }
        fout.close();
        alreadyExist = false;
        return;
       // System.out.println("Amit");
    }
    String getName(String str)
    {
        String [] ar = str.split(" ");
        return ar[0];
    }
    boolean checkValidity() throws FileNotFoundException
    {   
        fin = new FileReader("userinfo.txt");
        sc = new Scanner(fin);
        String currentUser, currentPassword;
        while(sc.hasNext())
        {
            currentUser = sc.next();
            currentPassword = sc.next();
            if(currentUser.equals(userName)&&currentPassword.equals(password))
            {
                return true;
            }
        }
        return false;
    }
    
}
