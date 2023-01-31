import java.io.*;
import java.util.*;

public class restaurant {
	private String itemName;
	private String restName;
	private double price;
	private final ArrayList<restaurant> rest= new ArrayList<>();
	private final String path = "restaurant.csv";


	public restaurant()
	{

	}

	public restaurant(String restName,String itemName,double price)
	{
		this.restName=restName;
		this.itemName=itemName;
		this.price=price;
	}

	public void insert(restaurant t)
	{
		rest.add(t);
	}

	public String getRestName()
	{
		return restName;
	}
	public String getName()
	{
		return itemName;
	}
	public ArrayList<restaurant> getAllMenu()
	{
		return rest;
	}
	public double getPrice()
	{
		return price;
	}


	public void setRestName(String name)
	{
		this.restName=name;
	}
	public void setItemName(String name)
	{
		this.itemName = name;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}


	public void modifyRestName(String RN, String nRN)
	{
		for (restaurant restaurant: rest)
			if (restaurant.getRestName().equalsIgnoreCase(RN))
				restaurant.setRestName(nRN);
	}
	public void modifyItemName(String restName,String oldName,String newName)
	{
		for (restaurant restaurant : rest)
			if (restaurant.getRestName().equalsIgnoreCase(restName) && restaurant.getName().equalsIgnoreCase(oldName))
			{
				restaurant.setItemName(newName);
				break;
			}

	}
	public void modifyItemPrice(String restName,String itemName,double price)
	{
		for (restaurant restaurant : rest)
		{
			if (restaurant.getRestName().equalsIgnoreCase(restName) && restaurant.getName().equalsIgnoreCase(itemName))
			{
				restaurant.setPrice(price);
				break;
			}
		}
	}


	public void deleteMenu(String RNName)
	{
		ArrayList<String> name1= new ArrayList<>();

		for (restaurant restaurant : rest)
			if (restaurant.getRestName().equalsIgnoreCase(RNName))
				name1.add(restaurant.getRestName());

		for (String s : name1)
			if (s.equalsIgnoreCase(RNName))
				for (int j = 0; j < rest.size(); j++)
					if (rest.get(j).getRestName().equalsIgnoreCase(RNName))
						rest.remove(j);
	}
	public void deleteItem(String RN, String iName)
	{
		ArrayList<String> name1= new ArrayList<>();

		for (restaurant restaurant : rest)
			if (restaurant.getRestName().equalsIgnoreCase(RN))
				name1.add(restaurant.getRestName());

		for (String s : name1)
			if (s.equalsIgnoreCase(RN))
				for (int j = 0; j < rest.size(); j++)
					if ((rest.get(j).getRestName().equalsIgnoreCase(RN)) && (rest.get(j).getName().equalsIgnoreCase(iName)))
						rest.remove(j);
	}


	public int searchRN(String name) throws FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		int empty = 2;
		try
		{

			while ((line = reader.readLine()) != null)
			{
				String[] row = line.split(",");

				for (int i = 0; i < row.length; i++)
					if (row[0].equalsIgnoreCase(name))
						return 1;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return 0;

	}
	public int searchIN(String RN, String iName) throws FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;

		try
		{

			while ((line = reader.readLine()) != null)
			{
				String[] row = line.split(",");

				for (int i = 0; i < row.length; i++)
					if (row[0].equalsIgnoreCase(RN) && row[1].equalsIgnoreCase(iName))
						return 1;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	public String Menus() throws FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		StringBuilder detail = new StringBuilder();
		int skip = 0;
		String[] check = new String[rest.size()];
		int check_counter = 0;
		Arrays.fill(check, "1");

		try {

			while ((line = reader.readLine()) != null)
			{
				if (skip == 0)
				{
					skip = 1;
					continue;
				}

				String[] row = line.split(",");
				boolean l_counter = false;

				for (String s : check)
					if (s.equalsIgnoreCase(row[0]))
					{
						l_counter = true;
						break;
					}

				if (!l_counter)
				{
					detail.append(row[0]).append(" | ");
					check[check_counter++] = row[0];
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return detail.toString();
	}
	public String display(String name) throws FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		StringBuilder detail = new StringBuilder();
		try
		{

			while ((line = reader.readLine()) != null)
			{
				String[] row = line.split(",");
				for (int i = 0; i < row.length; i++)
					if (row[i].equalsIgnoreCase(name))
						detail.append(row[i + 1]).append(", ").append("Price: ").append(row[i + 2]).append(" | ");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return detail.toString();
	}

}