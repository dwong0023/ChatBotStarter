import java.util.Random;
import java.util.Scanner;
// JESSE'S SIDE - CUSTOMER SUPPORT
/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot2
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	int progress = 0;
	/*
	 PROGRESS 0 = NAME
     PROGRESS 1 = PROBLEM
     PROGRESS 2 = CLARIFICATION
     PROGRESS 3 = SOLUTION
     PROGRESS 4 = REFLECTION
    */
	String problemObject = "";
	String problemAdjective = "";
	String problemVerb = "";
	String name = "";

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye") && emotion > -2)
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));


		}
        System.out.println("The person you are speaking to hung up.");
	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Thank you for calling Eggs Dee™ customer support service. Can I get your name?";
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
        if (!statement.isEmpty() && statement.substring(statement.length() - 1).equals(".")) {
            statement = statement.substring(statement.length() - 1);
        }
		
		if (statement.isEmpty()) {
			response = "I'm sorry, I didn't catch that.";
		}
		else if (progress == 0) {
		    if (findKeyword(statement,"my name is", 0) >= 0 || findKeyword(statement, "i'm",0) >= 0 || findKeyword(statement, "im", 0) >= 0) {
                if (statement.contains("my name is") || statement.contains("My name is")) {
                    name = statement.substring(findKeyword(statement,"my name is",0) + "my name is".length() + 1);
                }
                else if (statement.contains("I'm") || statement.contains("i'm")) {
                    name = statement.substring(findKeyword(statement,"i'm",0) + "I'm".length() + 1);

                }
                else if (statement.contains("im") || statement.contains("Im")) {
                    name = statement.substring(findKeyword(statement, "im", 0) + "im".length() + 1);
                }
                progress ++;
                response = "Hello, " + name + ". How can I help you?";
            }
            else if (findKeyword(statement,"no",0) >= 0 && (statement.length() == 2 || statement.length() == 3) ) {
                emotion --;
                response = "I need a name.";
            }
            else {
                name = statement;
                progress ++;
                response = "Hello, " + name + ". How can I help you?";
            }
        }
        else if (progress == 1) {
            // if problem or issue is in the statement
            if (statement.contains("problem") || statement.contains("issue")) {
                progress++;
                // if "problem with" is in the statement
                if (statement.contains("with")) {
                    // "problem with my" ...
                    if (statement.contains("my")) {
                        problemObject = statement.substring(findKeyword(statement,"my",0) + 3);
                        // what comes after "problem with my" = problemObject
                        response = "What seems to be the problem with your " + problemObject + ", " + name + "?";
                        progress = 2;
                    }
                    else { // problem with ...
                        if (statement.substring(statement.length() - 1).equals(".")) {
                            statement = statement.substring(statement.length() - 1);
                        }
                        problemObject = statement.substring(findKeyword(statement, "with ", 0) + 5);
                        response = "What seems to be the problem with " + problemObject + "?";
                        progress = 2;
                    }
                } else {
                    response = "What's the issue?";
                    progress = 2;
                }
            }
            //my x is/are ...
            else if (statement.contains("is") || statement.contains("are")) {
                String isare = "";
                if (statement.contains("is")) {
                    isare = "is";
                }
                else if (statement.contains("are")) {
                    isare = "are";
                }
                progress = 3;
                if (statement.contains("my")) {
                    problemObject = statement.substring(findKeyword(statement, "my") + 3, findKeyword(statement, isare) - 1);
                }
                    problemAdjective = statement.substring(findKeyword(statement, isare, 0) + isare.length() + 1);
                response = "I see, so your " + problemObject + " " + isare + " " + problemAdjective + ".";
            }
            else if (statement.contains("can't") || statement.contains("cant") || statement.contains("won't") || statement.contains("wont") || statement.contains("not")) {
                String notter = "";
                if (statement.contains("can't") || statement.contains("won't")){notter = "'t";}
                else if (statement.contains("not")){notter = "not";}
                else if (statement.contains("wont")){notter = "wont";}
                else if (statement.contains("cant")){notter = "cant";}
                problemVerb = statement.substring(statement.indexOf(notter) + notter.length() + 1);
                if (findKeyword(statement,"my",0) >= 0) {problemObject = statement.substring(findKeyword(statement,"my",0) + 3, statement.indexOf(notter));}
                else {problemObject = statement.substring(0,statement.indexOf(notter));}
                response = "I see, so your " + problemObject + " " + problemVerb + ".";
                progress = 3;
            }
        }
        else if (progress == 2) {
            if (statement.contains("is") || statement.contains("are")) {
                String isare = "";
                if (statement.contains("is")) {
                    isare = "is";
                }
                else if (statement.contains("are")) {
                    isare = "are";
                }
                progress = 3;
                if (statement.contains("my")) {
                    problemObject = statement.substring(findKeyword(statement, "my") + 3, findKeyword(statement, isare) - 1);
                }
                problemAdjective = statement.substring(findKeyword(statement, isare, 0) + isare.length() + 1);
                response = "I see, so your " + problemObject + " " + isare + " " + problemAdjective + ".";
            }
            else if (statement.contains("can't") || statement.contains("cant") || statement.contains("won't") || statement.contains("wont") || statement.contains("not")) {
                String notter = "";
                if (statement.contains("can't") || statement.contains("won't")){notter = "'t";}
                else if (statement.contains("not")){notter = "not";}
                else if (statement.contains("wont")){notter = "wont";}
                else if (statement.contains("cant")){notter = "cant";}
                problemVerb = statement.substring(statement.indexOf(notter) + notter.length() + 1);
                if (findKeyword(statement,"my",0) >= 0) {problemObject = statement.substring(findKeyword(statement,"my",0) + 3, statement.indexOf(notter));}
                else {problemObject = statement.substring(0,statement.indexOf(notter));}
                response = "I see, so your " + problemObject + " isn't able to " + problemVerb + ".";
                progress = 3;
            }
        }
        else if (progress == 3) {

        }
		// Response transforming I want to statement
		/*else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}	*/
		if (response.isEmpty())
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
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
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
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
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
		return "Why do you " + restOfStatement + " me?";
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
	
	private String [] randomNeutralResponses = {"I couldn't catch that.", "I didn't hear you.", "Sorry, could you repeat that?"};
	private String [] randomAngryResponses = {"Sorry?", "Excuse me?", "What?", "What did you say to me?"};
	private String [] randomHappyResponses = {"Sorry, I didn't quite catch that.", "Could you please speak a little slower? Take your time.", "I didn't hear you that time."};
	
}
