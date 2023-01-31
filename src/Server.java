import java.io.*;
import java.util.Scanner;
import java.net.*;

public class Server
{

    public static void main(String[] args) throws IOException
    {
        restaurant rest = new restaurant();
        System.out.println("Establishing Clients ...");
        ServerSocket Server = new ServerSocket(4999);
        Socket ClientSocket = Server.accept();
        System.out.println("Connection Created ");
        BufferedReader input = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
        PrintWriter outToClient = new PrintWriter(ClientSocket.getOutputStream(), true);
        //transfer data from csv file to system
        File file2 = new File("restaurant.csv");

        //transfer data from user to alluser
        try
        {
            //transfer data from restaurant to rest
                Scanner input2 = new Scanner(file2);
                while (input2.hasNext())
                {
                    String data2 = input2.next();
                    String[] sentence2 = data2.split(",");
                    try
                    {
                        rest.insert(new restaurant(sentence2[0],sentence2[1], Double.parseDouble(sentence2[2])));
                    }
                    catch (Exception ignored)
                    {

                    }
                }
                input2.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //---------------------------
        String choice;
        String RN;
        //--------------------------start the service

        do
        {
            choice = input.readLine();
            switch (choice)
            {
                //Create a Menu
                case "1" -> {
                    outToClient.println("Enter Restaurant name: ");
                    RN = input.readLine();

                    //check if rest name in the csv file
                    int check = 0;
                    if (rest.searchRN(RN) == 1)
                        check = 1;

                    if (check == 1) // exists
                        outToClient.println("1");
                    else // doesn't exist
                    {
                        outToClient.println("0");
                        int count = Integer.parseInt(input.readLine()); // how many items will be added to the restaurant
                        if (count != -1)
                        {
                            String iName;
                            double price;
                            int loop4 = count;

                            for (int i = 0; i < loop4; i++) // loop the count of items that will be added
                            {
                                outToClient.println("Enter the item name: ");
                                iName = input.readLine();
                                int checkItem = rest.searchIN(RN,iName);

                                if (checkItem == 1) // check if the item already exists
                                {
                                    outToClient.println("1");
                                    outToClient.println("Item already exist!");
                                    count--;
                                    continue;
                                }

                                outToClient.println("0");
                                outToClient.println("Enter the price: ");
                                price = Double.parseDouble(input.readLine());
                                restaurant res = new restaurant(RN, iName, price);
                                rest.insert(res);

                                // write the info again in the csv file
                                PrintWriter pw = new PrintWriter(file2);
                                int keep = 0;
                                for (int j = 0; j < rest.getAllMenu().size(); j++)
                                {
                                    if (keep == 0)
                                        pw.println("Restaurant_Name,Item_Name,Price");

                                    pw.printf("%s,%s,%s\n",
                                        rest.getAllMenu().get(j).getRestName(),
                                        rest.getAllMenu().get(j).getName(),
                                        rest.getAllMenu().get(j).getPrice());
                                    keep = 1;
                                }
                                pw.close();
                            }

                            //confirmation message to the added items
                            outToClient.println("Restaurant added, With: ");
                            int p = rest.getAllMenu().size()-1;
                            for (int i = p; i > p-count; i--)
                            {
                                outToClient.println(rest.getAllMenu().get(i).getName());
                                outToClient.println("Price: " + rest.getAllMenu().get(i).getPrice());
                            }

                        }
                    }
                }

                //modify Menu
                case "2" -> {
                    outToClient.println("Enter Restaurant name: ");
                    RN = input.readLine();

                    //check if rest name in the csv file
                    int check2 = 0;
                    if (rest.searchRN(RN) == 1)
                        check2 = 1;

                    if (check2 == 0)
                        outToClient.println("0");
                    else
                    {
                        outToClient.println("1");
                        int up = Integer.parseInt((input.readLine()));

                        if (up == 1) //Add
                        {
                            int ModifyAddCount= Integer.parseInt(input.readLine()); // how many items will be added

                            if (ModifyAddCount != -1)
                            {
                                String iName;
                                double price;
                                int loop4 = ModifyAddCount;

                                for (int i = 0; i < loop4; i++)  // loop the count of items that will be added
                                {
                                    outToClient.println("Enter the item name: ");
                                    iName = input.readLine();
                                    int checkItem = rest.searchIN(RN, iName);

                                    if (checkItem == 1)  // check if the item already exists
                                    {
                                        outToClient.println("1");
                                        outToClient.println("Item already Exist!");
                                        ModifyAddCount--;
                                        continue;
                                    }
                                    outToClient.println("0");
                                    outToClient.println("Enter the price: ");
                                    price = Double.parseDouble(input.readLine());
                                    restaurant res = new restaurant(RN, iName, price);
                                    rest.insert(res);

                                    //confirmation message to the added items
                                    outToClient.println("Addition Completed!, What's new:");

                                    int p = rest.getAllMenu().size() - 1;
                                    for (int j = p; j > p - ModifyAddCount; j--)
                                    {
                                        outToClient.println(rest.getAllMenu().get(j).getName());
                                        outToClient.println("Price: " + rest.getAllMenu().get(j).getPrice());
                                    }

                                    // write the info again in the csv file
                                    PrintWriter pw = new PrintWriter(file2);
                                    int keep = 0;
                                    for (int k = 0; k < rest.getAllMenu().size(); k++)
                                    {
                                        if (keep == 0)
                                            pw.println("Restaurant_Name,Item_Name,Price");

                                        pw.printf("%s,%s,%s\n",
                                                rest.getAllMenu().get(k).getRestName(),
                                                rest.getAllMenu().get(k).getName(),
                                                rest.getAllMenu().get(k).getPrice());
                                        keep = 1;
                                    }
                                    pw.close();
                                }

                            }
                        }
                        else if (up == 2)//Update
                        {
                            int prompt = Integer.parseInt(input.readLine());

                            if (prompt == 1) // update restaurant name
                            {
                                outToClient.println(rest.Menus()); //Display restaurant names
                                String newRN = input.readLine(); // new restaurant name
                                int checkRN = rest.searchRN(newRN);

                                if (checkRN == 1) // if the updated restaurant name already exists
                                {
                                    outToClient.println("1");
                                    outToClient.println("Restaurant name already exist!");
                                }
                                else //doesn't exist
                                {
                                    outToClient.println("0");
                                    rest.modifyRestName(RN, newRN); //update the selected restaurant name
                                    outToClient.println("Update Completed!");
                                }

                                //write on the csv file the updated list
                                PrintWriter pw = new PrintWriter(file2);
                                int keep = 0;
                                for (int i = 0; i < rest.getAllMenu().size(); i++)
                                {
                                    if (keep == 0)
                                        pw.println("Restaurant_Name,Item_Name,Price");

                                    pw.printf("%s,%s,%s\n",
                                            rest.getAllMenu().get(i).getRestName(),
                                            rest.getAllMenu().get(i).getName(),
                                            rest.getAllMenu().get(i).getPrice());
                                    keep = 1;
                                }
                                pw.close();

                            }

                            else if (prompt == 2) // update an item name
                            {
                                outToClient.println(rest.display(RN)); // display the restaurant items and prices
                                String ina = input.readLine(); // receive the item name
                                int checkItem = rest.searchIN(RN, ina);

                                if (checkItem == 0) // the item doesn't exist
                                {
                                    outToClient.println("0");
                                    outToClient.println("Item doesn't exist!");
                                }
                                else //exist
                                {
                                    outToClient.println("1");
                                    String nna = input.readLine(); // receive the update item name
                                    int checkNewItem = rest.searchIN(RN, nna); //check if the updated item name exist in the restaurant

                                    if (checkNewItem == 1) //exists
                                    {
                                        outToClient.println("1");
                                        outToClient.println("Item already exist!");
                                    }
                                    else //doesn't exist
                                    {
                                        outToClient.println("0");
                                        rest.modifyItemName(RN, ina, nna); //update the item name in the list
                                        outToClient.println("Update finished!");

                                        // write back on the csv file the updated list
                                        PrintWriter pw = new PrintWriter(file2);
                                        int keep = 0;
                                        for (int i = 0; i < rest.getAllMenu().size(); i++)
                                        {
                                            if (keep == 0)
                                                pw.println("Restaurant_Name,Item_Name,Price");

                                            pw.printf("%s,%s,%s\n",
                                                    rest.getAllMenu().get(i).getRestName(),
                                                    rest.getAllMenu().get(i).getName(),
                                                    rest.getAllMenu().get(i).getPrice());
                                            keep = 1;
                                        }
                                        pw.close();
                                    }
                                }
                            }

                            else if (prompt == 3) // update an item price
                            {
                                outToClient.println(rest.display(RN)); // display the restaurant items and prices
                                String ina = input.readLine(); // receive the item name
                                int checkItem = rest.searchIN(RN, ina); //check for the item name in the list

                                if (checkItem == 0) //doesn't exist
                                {
                                    outToClient.println("0");
                                    outToClient.println("Item doesn't exist!");
                                }
                                else //exists
                                {
                                    outToClient.println("1");
                                    double nna = Double.parseDouble(input.readLine()); // receive the update item price
                                    rest.modifyItemPrice(RN, ina, nna);
                                    outToClient.println("Update finished!");

                                    //write back on the csv file the updated list
                                    PrintWriter pw = new PrintWriter(file2);
                                    int keep = 0;
                                    for (int i = 0; i < rest.getAllMenu().size(); i++)
                                    {
                                        if (keep == 0)
                                            pw.println("Restaurant_Name,Item_Name,Price");

                                        pw.printf("%s,%s,%s\n",
                                                rest.getAllMenu().get(i).getRestName(),
                                                rest.getAllMenu().get(i).getName(),
                                                rest.getAllMenu().get(i).getPrice());
                                        keep = 1;
                                    }
                                    pw.close();
                                }
                            }

                        }
                        else if (up == 3)//Delete an item
                        {
                            outToClient.println(rest.display(RN)); // display the restaurant items and prices
                            String del = input.readLine();
                            int ex= 0;// search for the item(not restName) in the menu

                            if (rest.searchIN(RN,del) == 1)
                                ex = 1;

                            if (ex == 0) //doesn't exist
                                outToClient.println("0");
                            else // exist
                            {
                                outToClient.println("1");
                                String c = input.readLine();
                                if (c.equalsIgnoreCase("Y")) // check for the conformation of deletion (Y)
                                {
                                    rest.deleteItem(RN,del); // delete the selected item
                                    outToClient.println("Deletion finished!");

                                    //write on the csv file the updated list
                                    PrintWriter pw = new PrintWriter(file2);
                                    int keep = 0;
                                    for (int i = 0; i < rest.getAllMenu().size(); i++)
                                    {
                                        if (keep == 0)
                                            pw.println("Restaurant_Name,Item_Name,Price");

                                        pw.printf("%s,%s,%s\n",
                                                rest.getAllMenu().get(i).getRestName(),
                                                rest.getAllMenu().get(i).getName(),
                                                rest.getAllMenu().get(i).getPrice());
                                        keep = 1;
                                    }
                                    pw.close();
                                } else if (c.equalsIgnoreCase("N")) // check for the conformation of deletion (N)
                                    outToClient.println("No deletion happened!");
                            }
                        }

                    }
                }

                //Delete a Menu
                case "3" -> {
                    outToClient.println(rest.Menus());
                    outToClient.println("Enter Restaurant name: ");
                    RN = input.readLine();

                    //check if rest name in the csv file
                    int check3 = 0;
                    if (rest.searchRN(RN) == 1)
                        check3 = 1;

                    if (check3 == 0) // doesn't exist
                        outToClient.println("0");
                    else // exist
                    {
                        outToClient.println("1");
                        String c = input.readLine();
                        if (c.equalsIgnoreCase("Y")) // check for the conformation of deletion (Y)
                        {
                            rest.deleteMenu(RN);
                            outToClient.println("Deletion finished!"); // deletion finished

                            //write back on the csv file the updated list
                            PrintWriter pw = new PrintWriter(file2);
                            int keep = 0;
                            for (int i = 0; i < rest.getAllMenu().size(); i++)
                            {
                                if (keep == 0)
                                    pw.println("Restaurant_Name,Item_Name,Price");

                                pw.printf("%s,%s,%s\n",
                                        rest.getAllMenu().get(i).getRestName(),
                                        rest.getAllMenu().get(i).getName(),
                                        rest.getAllMenu().get(i).getPrice());
                                keep = 1;
                            }
                            pw.close();
                        }
                        else if (c.equalsIgnoreCase("N")) // check for the conformation of deletion (N)
                            outToClient.println("No deletion happened!");
                    }
                }

                //Search for a Menu
                case "4" -> { //display menu
                    outToClient.println(rest.Menus()); // print all restaurants
                    RN = input.readLine();

                    //check if rest name in the csv file
                    int check4 = 0;
                    if (rest.searchRN(RN) == 1)
                        check4 = 1;

                    if (check4 == 0) // doesn't exists
                        outToClient.println("0");
                    else // exists
                    {
                        outToClient.println("1");
                        outToClient.println(rest.display(RN));
                    }
                }

                //Exit
                case "5" -> { //exit
                    outToClient.println("Thanks good bye!");
                    Server.close();
                    System.exit(0);
                }
            }
        } while (true);

    }
}