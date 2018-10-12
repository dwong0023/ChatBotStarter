import java.util.Random;
import java.util.Scanner;

/**
 * MANAGER - DANIYAH WONG
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot3
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	int nocount = 0;
	String callername;


	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public String chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while(!statement.equals("Bye") && emotion >= -3)
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));
		}
		if(emotion == -3)
		{
			return "The manager has hung up.";
		}
		else
		{
			return "...";
		}
	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		System.out.print("Hello, this is the manager for the Eggs Company. Who am I speaking to?");
		Scanner newname = new Scanner(System.in);
		callername = newname.nextLine();
		System.out.print("Alright, " + callername + ", are you calling about the job offer?");
		Scanner yesno = new Scanner(System.in);
		String joboff = yesno.nextLine();
		if(joboff.equals("yes"))
		{
			return "That's great! Talk to me about what job you're interested in, what salary you're looking for, your qualifications, or hours.";
		}
		else if(joboff.equals("no"))
		{
			emotion--;
			return "Then is there a problem you'd like to report? Please contact customer service instead. Talk to me about our open positions, salaries, qualifications, or hours if you're interested in a job.";
		}
		else
		{
			emotion--;
			return "It's a yes or no question. You can talk to me about our open positions, salaries, qualifications, or hours if you're interested in a job.";
		}
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
			response = "Say something, please.";
			emotion--;
		}
		
		else if (findKeyword(statement, "get a job") >= 0)
		{
			response = "Alright. What jobs are you interested in, " + callername + "?";
			emotion++;
			response = askAboutJobs(statement);
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to be the ", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want a salary of",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}
		else if (findKeyword(statement, "I",0) >= 0 && findKeyword(statement, "you",0) >= 0)
		{
			response = transformIYouStatement(statement);
		}
		else if (findKeyword(statement, "I can",0) >= 0)
		{
			response = qualifications(statement);
		}
		else if (findKeyword(statement, "I want to work for ",0) >= 0)
		{
			response = hours(statement);
		}
		else
		{
			response = getRandomResponse();
		}
		
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I want to be the ", 0);
		String restOfStatement = statement.substring(psn + 17).trim();
		return "Why do you want to be the " + restOfStatement + "?";
	}
	
	/**
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I want a salary of ", 0);
		String restOfStatement = statement.substring(psn + 19).trim();
		return "Hm. A salary of $" + restOfStatement + ", you say? " + salaryResponse(restOfStatement);
	}

	private String qualifications(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I can ", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "So, you can " + restOfStatement + "? Interesting.";
	}
	
	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "You " + restOfStatement + " me? Well, that's not very professional, " + callername + ", but thank you for that information.";
	}

	public String salaryResponse(String moneyrequested) {
		int money = Integer.parseInt(moneyrequested);
		if (money <= 0) {
			emotion--;
			return "Isn't that a bit low?";
		}
		else if (money >= 100000) {
			emotion--;
			return "Isn't that a bit high?";
		}
		else
		{
			emotion++;
			return "That seems somewhat reasonable.";
		}
	}

	public String askAboutJobs(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}

		int psnOfI = findKeyword (statement, "I am interested in being a ", 0);

		String restOfStatement = statement.substring(psnOfI + 27).trim();
		return "You're " + restOfStatement + "? I'll look into that for you, " + callername + ". If it's your lucky day, those jobs may be open. What else do you want to discuss?";
	}

	private String hours(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I want to work for ", 0);
		String restOfStatement = statement.substring(psn + 20).trim();
		return "So, you want to work for " + restOfStatement + " hours? Interesting.";
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
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}	
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	private String [] randomNeutralResponses = {"Interesting, " + callername + ", tell me more.",
			"Please respond in full sentences, I can't hear you too well so I'll need the context.",
			"What else do you want to discuss, " + callername + "?",
			"What do you want a salary of?",
			"What do you mean by that, " + callername + "?",
			"What employee do you want to be?",
			"How many hours do you want to work?",
			"What can you do?"
	};
	private String [] randomAngryResponses = {"You know if you want this job, you'll have to speak in full sentences, right?","Are you kidding me, " + callername + "?", "Please get to the point, I wish to be done.", "I'm going to hang up soon. Ask what you need of me, or get lost."};
	private String [] randomHappyResponses = {"Please clarify.", "I'm feeling generous. You just might get this job.", "Today is a good day for you, " + callername + ".", "You seem like a pretty good candidate, " + callername + "."};
	
}
