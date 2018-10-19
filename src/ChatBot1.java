import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
Zeng Chen
*/

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot1
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int item = 0;
	int turns = 0;
	double money = 0.00;
	String[]userArr = {};
	String[]responseArr = {};
	String[]praiseArr = {"Those eggs are one of our best selling products. It's rarely in stock, so buy it before it runs out.", "These eggs are our best rated product. Our customers often survive the purchase" , "These eggs are organic and contain a wide variety of nutrients. In fact, many of our customer would recommend this product to someone they know!"};
	int match;
	ArrayList userInput = new ArrayList(Arrays.asList(userArr));
	ArrayList responseOutput = new ArrayList(Arrays.asList(responseArr));
	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		getGreeting();
		while (!statement.equals("Bye"))
		{
			statement = in.nextLine();
			if(responseOutput.toArray(userArr).length > 0)
			{
				match = -1;
				for (int i = 0; i == responseOutput.toArray(responseArr).length -1; i++) {
					if(userInput.toArray(userArr)[i].equals(statement.toLowerCase())) {
						match = i;
					}
					else if(responseOutput.toArray(responseArr)[i].equals(getResponse(statement)))
					{
						match = i;
					}
				}
				if (match != -1) {
					System.out.println("As I said before, " + responseOutput.toArray(responseArr)[match]);
				} else {
					System.out.println(getResponse(statement));
					userInput.add(turns, statement.toLowerCase());
					responseOutput.add(turns, getResponse(statement));
					turns++;
				}
			}
			else
			{
				System.out.println(getResponse(statement));
				userInput.add(turns, statement.toLowerCase());
				responseOutput.add(turns, getResponse(statement));
				turns++;
			}
		}

	}
	/**
	 * Get a default greeting
	 * @return a greeting
	 */
	public int outArrCheck(String x) {
		match = -1;
		for (int i = 0; i == responseOutput.toArray(responseArr).length - 1; i++) {
			if (findKeyword(responseOutput.toArray(responseArr)[i].toString(), x) >= 0) {
				match = i;
			}
		}
		return match;
	}
	public void getGreeting()
	{
		System.out.println("Greetings. Welcome to Eggs Dee Sales Bot. What can I do for you?");
		System.out.println("Option 1: Please say 'I want to buy large eggs' to see our selection of large eggs. This includes our famous Dragon Eggs.");
		System.out.println("Option 2: Please say 'I want to buy small eggs' to see our selection of small eggs.");
		System.out.println("Option 3: Please say 'Tell me about the quality of the eggs.' to know more about how we at Eggs Dee.inc obtain our eggs.");
	}

	public String Connection(String search) //provide the link for the google search for *type* eggs.
	{
		return ("https://www.google.com/search?safe=strict&source=hp&ei=ZqzEW-z7C4ep_QbU77PgAQ&q=" + search + "&btnK=Google+Search&oq=" + search);
	}

	public String eggs(String statement)
	{
		String output = "";
		if (findKeyword(statement, "large eggs") >= 0) {
			output = largeEggs();
		} else if (findKeyword(statement, "small eggs") >= 0) {
			output = smallEggs();
		}
		else if (findKeyword(statement, "large egg") >= 0) {
			return largeEggs();
		} else if (findKeyword(statement, "small egg") >= 0) {
			output = smallEggs();
		}
		else
		{
			output = null;
		}
		return output;
	}
	public String largeEggs()// provide command menu for the large eggs
	{
		return "We have a variety of large eggs. Our products includes eggs from the following animals: dragons, dinosaurs, ostriches. " +
				"\nYou can say 'I want to buy *animal* eggs' and I will add said eggs to the shopping cart for checkout. However, some of our eggs may be out of stock." +
				"\nYou can also say 'Tell me about *animal* eggs' and I will provide you information regarding that egg.";
	}

	public String smallEggs()// provide command menu for the small eggs
	{
		return "We sell all kinds of small eggs, from the normal chicken eggs to the unusually shaped eggs from Dimension 142." +
				"\nYou can say 'I want to buy *type* eggs' and I will add said eggs to the shopping cart for checkout. However, some of our eggs may be out of stock." +
				"\nYou can also say 'Tell me about *animal* eggs' and I will provide you information regarding that egg.";
	}

	public String quality()// provide command menu for the quality of the eggs
	{
		return "Our eggs are produced through the use of our patented 'The Egg' machine invented by our founder, Dean Eggs";
	}

	public String stocks(String statement)
	{
		int amount = (int)(Math.random() * 10);
		String info = "";
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn;
		String type;
		if(findKeyword(statement,"Tell me about the") >=0) {
			psn = findKeyword(statement, "Tell me about the", 0);
			type = statement.substring(psn + 18).trim();
		}
		else {
			psn = findKeyword(statement, "Tell me about", 0);
			type = statement.substring(psn + 13).trim();
		}
		if(amount > 0)
		{
			info = "We still have " + amount +" " + type + " in stock.";

		}
		else if(amount == 0)
		{
			info = "Sorry, the " + type + " are out of stock.";
		}
		return info;
	}
	public String moreInfo1(String statement)
	{
		String data;
		String type;
		int psn;
		if(findKeyword(statement,"Tell me about the") >=0) {
			psn = findKeyword(statement, "Tell me about the", 0);
			type = statement.substring(psn + 18).trim();
		}
		else {
			psn = findKeyword(statement, "Tell me about", 0);
			type = statement.substring(psn + 13).trim();
		}
		data = stocks(statement);
		data += "\n" + praiseArr[(int)Math.random() * 3];
		String search = type;
		while(search.indexOf(" ") != -1)
		{
			search = search.substring(0 , type.indexOf(" ")) + "+" + type.substring(type.indexOf(" ")+1);
		}
		data += "\nFor more information go to:" + Connection(search);
		return data;
	}
	public void checkout (String statement) {
			int test = outArrCheck("eggs");
			if (test != -1) {
				if (findKeyword(responseOutput.toArray(responseArr)[test].toString(), "out of stock") >= 0) {
					money += 0;
				} else {
					if(findKeyword(responseOutput.toArray(responseArr)[test].toString(), "large eggs") != -1 || findKeyword(responseOutput.toArray(responseArr)[test].toString(), "small eggs") != -1)
					money += (Math.random() * 10 + 17);
				}
			}
		}
	public String purchase(String statement)
	{
		String out = "";
		if(eggs(statement) != null)
		{
			out = eggs(statement);
		}
		else {
			statement = statement.trim();
			String lastChar = statement.substring(statement.length() - 1);
			if (lastChar.equals(".")) {
				statement = statement.substring(0, statement.length() - 1);
			}
			int psn;
			String type = "";
			if (findKeyword(statement, "I want to buy the") >= 0) {
				psn = findKeyword(statement, "I want to buy the", 0);
				type = statement.substring(psn + 17).trim();
			}
			else{
				psn = findKeyword(statement, "I want to buy", 0);
				type = statement.substring(psn + 13).trim();
			}
			int test = outArrCheck(type);
			if(type.equals(" ") || type.equals("") || type == null)
			{
				out = "We sell eggs. What do you want to buy?";
			}
			else
			{
				if (test != -1) {
					if (findKeyword(responseOutput.toArray(responseArr)[test].toString(), "out of stock") >= 0) {
						out = "The item is out of stock";
					} else {
						out = "The item have been added to your shopping cart";
					}
				} else {
					if (findKeyword(stocks(statement), "out of stock") >= 0) {
						out = "The item is out of stock";
					} else {
						out = "The item have been added to your shopping cart";
					}
				}
			}
		}
		return out;
	}

	/**
	 * Gives a response to a user statement
	 *
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";

		if (statement.length() == 0)
		{
			int x = (int) Math.random();
			if(x == 1)
			{
				response = "Please say that again, I didn't quite catch that.";
			}
			else
			{
				response = "I'm sorry, but can you say that again? I don't believe I heard you correctly.";
			}
		}
		else if(findKeyword(statement,"checkout") >= 0)
		{
			checkout(statement);
			response = "Your total is $" + money;
		}
		else if(findKeyword(statement,"I want to buy") >=0)
		{
			response = purchase(statement);
		}
		else if(findKeyword(statement, "Tell me about") >= 0)
		{
			if(findKeyword(statement,"quality") >= 0)
			{
				response = quality();
			}
			else if(findKeyword(statement,"eggs") >= 0 || findKeyword(statement,"egg") >= 0)
			{
				response = moreInfo1(statement);
			}
			else
			{
				response = "Tell you about what? I can only provide information regarding eggs.";
			}
		}
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}
		else
		{
			response = "I don't understand that command, please try again.";
		}

		return response;
	}

	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}

	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}



	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
}
