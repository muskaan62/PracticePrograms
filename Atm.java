import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.exit;

public class Atm {
    String s1;
    public static void main(String args[]) throws Exception {
        Scanner s1 = new Scanner(System.in);
        bank();
        }
static void bank() throws IOException {
        Scanner s1=new Scanner(System.in);
    System.out.println("ENter account id");
    int act = s1.nextInt();
    File f = new File("/home/gaian/IdeaProjects/ATM_demo/src/main/java/data.txt");
    boolean flag = false;
    Scanner s = new Scanner(f);
    while (s.hasNextLine()) {
        String aid = s.next();
        String[] sp = aid.split("=", 2);
        int id = Integer.parseInt(sp[0]);
        String file = sp[1];
        if (id == act) {
            File f1 = new File(file);
            Scanner sc = new Scanner(f1);
            sc.useDelimiter("\\Z");
            String str = sc.next();
            //HashMap<String, String> map = new ObjectMapper().readValue(str, HashMap.class);
            HashMap<String, String> map = new ObjectMapper().readValue(str, HashMap.class);
            System.out.println("Enter pin associated with the account");
            String input = s1.next();
            String  name = "name", amount = "amount";
            if (map.containsValue(input))
                operation(String.valueOf(map.get(amount)),String.valueOf(map.get(name)),map,file);
            else
                System.out.println("incorrect pin");
            flag = true;
            break;
        }
    }
    if (flag)
        System.out.println();
    else
        System.out.println("account doest not exist");
    System.out.println("Do you want to continue?");
    System.out.println("1.Yes\n2.No");
    int st=s1.nextInt();
    switch(st)
    {
        case 1:
            bank();
            break;
        case 2:
            exit(0);
    }
}
   static  void operation(String amt,String name, HashMap f1, String file) throws IOException
    {
        int a = Integer.parseInt(amt);
        Scanner s1 = new Scanner(System.in);
        System.out.println("do you want to\n 1.Deposit \n 2.withdraw");
        int in = s1.nextInt();
        switch (in) {
            case 1:
                Deposit(a,name,f1,file);
                break;
            case 2:
                Withdraw(a,name,f1,file);
                break;

        }
    }
    static <Map> void Deposit(int amt, String name,HashMap f1, String file) throws IOException {
        String m="amount";
        System.out.println("Enter amount to be deposit");
        Scanner s = new Scanner(System.in);
        int amount = s.nextInt();
        System.out.println("Account Holder :" + name);
        System.out.println("previous balance :" + amt);
        int r = amt + amount;
        System.out.println("Current Balance is " + r);
        f1.put(m,r);
       String json = new ObjectMapper().writeValueAsString(f1);
        Files.writeString(Path.of(file),json);
    }
    static void Withdraw(int amt, String name,HashMap f1,String file) throws IOException {
        System.out.println("Enter amount to be Withdraw");
        Scanner s = new Scanner(System.in);
        String m="amount";
        int amount = s.nextInt();
        if (amt < amount)
            System.out.println("insufficient Balance");
        else {
            System.out.println("Account Holder :" + name);
            System.out.println("previous balance :" + amt);
            int r = amt - amount;
            System.out.println("Current Balance is " + r);
            f1.put(m,r);
            String json = new ObjectMapper().writeValueAsString(f1);
            Files.writeString(Path.of(file),json);
        }
    }
}
