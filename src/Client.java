import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;


public class Client
{

    public static void main(String[] args) throws IOException
    {
        Socket Client = new Socket("localhost", 4999);
        System.out.println("Client Created");
        PrintWriter ToServer = new PrintWriter(Client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
        Scanner input = new Scanner(System.in);
        int choice;

        do
        {
            try
            {
            System.out.println("""
                    Choose your service\s
                    1-Create a menu\s
                    2-Update a menu\s
                    3-Delete a menu\s
                    4-Search for a menu\s
                    5-Exit""");

            System.out.print("=> ");
            choice = input.nextInt();

            if (choice < 1 || choice > 6)
                System.out.println("------------\nError, enter number from 1 to 5\n------------");
            else
            {
                String RN;

                switch (choice)
                {
                    //Create a Menu
                    case 1 -> {
                            ToServer.println("1"); // send choice 1 to server
                            System.out.print(in.readLine());//Enter username
                            RN = input.next();// Scan the username
                            ToServer.println(RN);  // send username to the server

                            int exist = Integer.parseInt(in.readLine());
                            if (exist == 1) //if the restaurnat name already exists
                            {
                                System.out.println("Restaurant already exist!\n---------------------------------------\n---------------------------------------");
                            }
                            else if (exist == 0) //doesn't exist
                            {
                                int count;
                                System.out.print("Enter how many items you want in your menu: ");
                                try
                                {
                                    count = input.nextInt();
                                    ToServer.println(count);
                                    int loop4 = count;

                                    for (int i = 0; i < loop4; i++)
                                    {
                                        System.out.print(in.readLine());//read name
                                        ToServer.println(input.next());//send name

                                        int checkItem = Integer.parseInt(in.readLine()); //check with the server if the item name already exist in the restaurant
                                        if (checkItem == 1) //exists
                                        {
                                            System.out.println(in.readLine());
                                            count--;
                                        }
                                        else //doesn't exist
                                        {
                                            System.out.print(in.readLine());//read price prompt
                                            do
                                            {
                                                try
                                                {
                                                    double price = input.nextDouble();
                                                    ToServer.println(price);//send price to the server
                                                    break;
                                                } catch (InputMismatchException g)
                                                {
                                                    System.out.println("Error, wrong input!");
                                                    System.out.print("Enter the price: ");
                                                    input.next();
                                                }
                                            } while (true);
                                        }
                                    }

                                    //confirmation messages
                                    System.out.println("-" + in.readLine());//Confirmation message

                                    for (int i = 0; i < count; i++)
                                    {
                                        System.out.println("->" + in.readLine());//name confirmation
                                        System.out.println("->" + in.readLine());//price confirmation
                                        System.out.println("---------------------------------------");
                                    }
                                }catch (InputMismatchException x)
                                {
                                    ToServer.println("-1");
                                    System.out.println("Error, try a valid input!");
                                    input.next();
                                }

                            }
                        }

                    //Modify Menu
                    case 2 -> { // Update a Menu
                            ToServer.println("2"); // send choice 2 to server
                            System.out.print(in.readLine());//Enter restaurant name
                            RN = input.next();// Scan the username
                            ToServer.println(RN);  // send username to the server

                            int exist2 = Integer.parseInt(in.readLine());
                            if (exist2 == 0) //if the restaurant name doesn't exist
                            {
                                System.out.println("Restaurant Does not exist!\n---------------------------------------\n---------------------------------------");
                            }
                            else if (exist2 == 1) //if the restaurant name exists
                            {
                                System.out.print("1-Add\n2-Update\n3-Delete an item\n(By choice number) => ");
                                int up = input.nextInt();
                                ToServer.println(up);

                                if (up == 1) //Add
                                {
                                    ///////////////////////////same as case 1 process
                                    int count;
                                    System.out.print("Enter how many items you want in your menu: ");
                                    try
                                    {
                                        count = input.nextInt();
                                        ToServer.println(count);
                                        int loop4 = count;

                                        for (int i = 0; i < loop4; i++)
                                        {
                                            System.out.print(in.readLine());//read name
                                            ToServer.println(input.next());//send name

                                            int checkItem = Integer.parseInt(in.readLine());
                                            if (checkItem == 1)
                                            {
                                                System.out.println(in.readLine());
                                                count--;
                                            }
                                            else
                                            {
                                                System.out.print(in.readLine());//read price
                                                do
                                                {
                                                    try
                                                    {
                                                        double price = input.nextDouble();
                                                        ToServer.println(price);//send price

                                                        System.out.println("-" + in.readLine());//Confirmation message

                                                        for (int l = 0; l < count; l++)
                                                        {
                                                            System.out.println("->" + in.readLine());//name confirmation
                                                            System.out.println("->" + in.readLine());//price confirmation
                                                            System.out.println("---------------------------------------");
                                                        }
                                                        break;
                                                    } catch (InputMismatchException g)
                                                    {
                                                        System.out.println("Error, wrong input!");
                                                        System.out.print("Enter the price: ");
                                                        input.next();
                                                    }
                                                } while (true);
                                            }
                                        }
                                    }catch (InputMismatchException x)
                                    {
                                        ToServer.println("-1");
                                        System.out.println("Error, try a valid input!");
                                        input.next();
                                    }
                                }
                                else if (up == 2) //Update
                                {
                                    System.out.print("Enter what to change: \n1-Restaurant name\n2-item\n3-price\n(By choice number) => ");
                                    int prompt = input.nextInt();
                                    ToServer.println(prompt);

                                    if (prompt == 1) // restaurant name
                                    {
                                        System.out.println(in.readLine()); //Display restaurant names
                                        System.out.print("Enter the new Restaurant name: ");
                                        String newRN = input.next();
                                        ToServer.println(newRN);

                                        int checkRN = Integer.parseInt(in.readLine()); //check if updated restaurant name already exists or not
                                        if (checkRN == 1) //exists
                                        {
                                            System.out.println(in.readLine());
                                        }
                                        else //doesn't exist
                                        {
                                            System.out.println(in.readLine());
                                            System.out.println("---------------------------------------");
                                        }
                                    }
                                    else if (prompt == 2) // item name
                                    {
                                        System.out.println(in.readLine());//Dispay restaurant items and prices
                                        System.out.print("Select what item to change(by name): ");
                                        ToServer.println(input.next());//Send the selected item

                                        int checkItem = Integer.parseInt(in.readLine()); //check if the item name exist in the csv file
                                        if (checkItem == 0) //doesn't exist
                                            System.out.println(in.readLine());
                                        else //exists
                                        {
                                            System.out.print("Enter the new item (by name): "); // new item name
                                            ToServer.println(input.next());//Send the required update

                                            int checkNewItem = Integer.parseInt(in.readLine());
                                            if (checkNewItem == 1) // if the updated item name already exist in the restaurant menu
                                                System.out.println(in.readLine());
                                            else //doesn't exist
                                                System.out.println(in.readLine());//Finish dialogue
                                        }
                                    }
                                    else if (prompt == 3) // item price
                                    {
                                        System.out.println(in.readLine());//Dispay restaurant items and prices
                                        System.out.print("Select what item to change(by name): ");
                                        ToServer.println(input.next());//Send the selected item for the price change

                                        int checkItem = Integer.parseInt(in.readLine()); //check if the item name exists in the restaurant menu
                                        if (checkItem == 0) //doesn't exist
                                            System.out.println(in.readLine());
                                        else //exist
                                        {
                                            System.out.print("Enter the new price: "); // new item price
                                            do //while the new price in a valid input (double)
                                            {
                                                try //to handle input mismatch error
                                                {
                                                    double nna = input.nextDouble();
                                                    ToServer.println(nna);//Send the required update
                                                    System.out.println(in.readLine());//Finish dialogue
                                                    break;
                                                }catch (InputMismatchException ex)
                                                {
                                                    System.out.println("Error, Wrong input!");
                                                    System.out.print("Enter the new price: ");
                                                    input.next();
                                                }
                                            }while (true);
                                        }
                                    }
                                }
                                else if (up == 3) //Delete an item
                                {
                                    System.out.println(in.readLine()); //Display the restaurant items and prices
                                    System.out.print("Choose what to delete\n(By name) => ");
                                    String del = input.next();
                                    ToServer.println(del);
                                    int ex = Integer.parseInt(in.readLine()); //check if the selected item exist in the csv file

                                    if (ex == 0) //doesn't exist
                                        System.out.println("Wrong item name");
                                    else if (ex == 1) //exist
                                    {
                                        String check;
                                        do
                                        {
                                        System.out.print("Confirm item deletion? \nY/N => "); //confirmation message
                                        check = input.next();

                                            if (check.equalsIgnoreCase("Y"))
                                            {
                                                ToServer.println("Y");
                                                System.out.println(in.readLine()); // show response message
                                                break;
                                            } else if (check.equalsIgnoreCase("N"))
                                            {
                                                ToServer.println("N");
                                                System.out.println(in.readLine()); // show response message
                                                break;
                                            }
                                        }while (true);
                                    }
                                }
                            }
                        }

                     //Delete a Menu
                    case 3 -> { // delete a menu
                            ToServer.println("3"); // send choice 3 to server
                            System.out.println(in.readLine());
                            System.out.print(in.readLine());//Enter RestName
                            RN = input.next();// Scan the RestName
                            ToServer.println(RN);  // send RestName to the server
                            int exist3 = Integer.parseInt(in.readLine()); //check

                            if (exist3 == 0) //if the restaurnat name doesn't exist
                            {
                                System.out.println("Restaurant does not exist!\n---------------------------------------"
                                                                         + "\n---------------------------------------");
                            }
                            else if (exist3 == 1) //if the restaurant name exists
                            {
                                String check;
                                do
                                {
                                System.out.print("Confirm Menu deletion? \nY/N => "); //confirmation part
                                check = input.next();

                                if (check.equalsIgnoreCase("Y"))
                                {
                                    ToServer.println("Y");
                                    System.out.println(in.readLine()); // show response message
                                    System.out.println("---------------------------------------");
                                    break;
                                } else if (check.equalsIgnoreCase("N"))
                                {
                                    ToServer.println("N");
                                    System.out.println(in.readLine()); // show response message
                                    System.out.println("---------------------------------------");
                                    break;
                                }
                                }while (true);
                            }
                        }

                    //Search for a Menu
                    case 4 -> { // search for a menu
                            ToServer.println("4"); // send choice 4 to server
                            System.out.println(in.readLine());//receive all restaurants names from server
                            System.out.print("Enter restaurant name: ");
                            ToServer.println(input.next());

                            int exist4 = Integer.parseInt(in.readLine()); //check with server if the selected restaurant name exist in the csv file
                            if (exist4 == 0) //doens't exist
                            {
                                System.out.println("Restaurant does not exist!\n---------------------------------------\n---------------------------------------");
                            }
                            else if (exist4 == 1) //exists
                            {
                                System.out.println("\n" + in.readLine() + "\n---------------------------------------");
                            }
                        }

                        //Exit
                    case 5 -> { //Exit
                            ToServer.println("5"); // send choice 5 to server
                            System.out.println(in.readLine()); // exit response-message
                            Client.close();
                            System.exit(0);
                        }
                }
            }
            }
            catch (Exception e)
            {
                System.out.println("Error, Try again\n---------------------------------------");
                input.next();
            }
        } while(true);

    }
}
