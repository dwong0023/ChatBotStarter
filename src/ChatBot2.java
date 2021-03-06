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
	double emotion = 0.0;
	int progress = 0;
	/*
	 PROGRESS 0 = NAME
     PROGRESS 1 = PROBLEM
     PROGRESS 2 = CLARIFICATION
     PROGRESS 3 = SOLUTION
     PROGRESS 4 = REFLECTION
     PROGRESS 5 = FEEDBACK
     PROGRESS 6 = BRANCH FROM 1; REDIRECTION
    */
	String problemObject = "";
	String problemAdjective = "";
	String problemVerb = "";
	String name = "";
	boolean manualHangUp = false;

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye") && emotion > -3 && !manualHangUp)
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));


		}
        System.out.println("[The person you are speaking to hung up]");
	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		if (manualHangUp || emotion <= -3) return "";
		else return "Thank you for calling Eggs Dee™ customer support service. Can I get your name?";
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
			response = "I'm sorry, I didn't catch that. Did you say anything?";
		}
		else if (progress == 0 && response.isEmpty()) { // accounts for different ways for introducing your name
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
                if (emotion <= -3) {
                    response = "";
                }
                else response = "I need a name."; // you can't deny the bot a name
            }
            else if (findKeyword(statement,"yes") >= 0 || findKeyword(statement,"ok") >= 0 || findKeyword(statement,"sure") == 0 || findKeyword(statement,"alright") == 0 || findKeyword(statement, "okay") == 0 || findKeyword(statement, "yeah") == 0) {
                response = "Alright, what is it?";
            }
            else { // and if you just say your name, it still works
                name = statement;
                progress ++;
                response = "Hello, " + name + ". How can I help you?";
            }
        }
        else if (progress == 1 && response.isEmpty()) {
            // if problem or issue is in the statement
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
            }

            //my x is/are ...
            else if (statement.contains(" is ") || statement.contains(" are ")) { // some of these statements are the same as the other ones above, to account for if the user says the full problem first
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
            else if ((statement.contains("dont need") || statement.contains("don't need")) && statement.contains("help")) { //detects "i don(')t need (any) help"
            	emotion --;
            	progress = 6;
            	response = "Okay. Would you like me to direct you to a representative from another department?";
			}
			else if (findKeyword(statement,"my") == 0) {

				if (statement.split(" ").length == 2) {
					problemObject = statement.split(" ")[1];
					progress = 3;
					response = "What about your " + problemObject + "?";
				}
				else {
					problemObject = statement.split(" ")[1];
					for (int i = 0; i < statement.split(" ").length - 2; i ++) {
						problemVerb += statement.split(" ")[2 + i];
						if (i < statement.split(" ").length - 3) {
							problemVerb += " ";
						}
					}
					response = "I see, so your " + problemObject + " " + problemVerb + ".";
					progress = 3;
				}
			}
            else if (findKeyword(statement,"buy") >= 0) {
                response = "Sorry, customer support can't sell anything. May I recommend you contact the salesperson? Otherwise how can I help you with our products?";
            }
            else if (findKeyword(statement,"help") == 0) {
                response = "What do you need help with?";
            }
        }
        else if (progress == 2 && response.isEmpty()) { // should detect what kind of variation of "to be" is used, and repeat it accordingly
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
            else if (findKeyword(statement,"my") == 0) { // if user just blankly says "my [thing]"

                if (statement.split(" ").length == 2) {
                    problemObject = statement.split(" ")[1];
                    progress = 3;
                    response = "What about your " + problemObject + "?";
                }
                else { // if user says "my [thing] [did this]", in other words if more than one word is detected
                    problemObject = statement.split(" ")[1];
                    for (int i = 0; i < statement.split(" ").length - 2; i ++) {
						problemVerb += statement.split(" ")[2 + i];
						if (i < statement.split(" ").length - 3) {
							problemVerb += " ";
						}
					}
                    response = "I see, so your " + problemObject + " " + problemVerb + ".";
                    progress = 3;
                }
            }
            else if (findKeyword(statement,"it") >= 0 || findKeyword(statement, "they") >= 0) { // "[it/they] [did this]"
                problemVerb = statement.split(" ")[1];
                response = "I see, so your " + problemObject + " " + problemVerb + ".";
                progress = 3;
            }
        }
        else if (progress == 3 && response.isEmpty()) { // if user asks for the solution/responds to wanting the solution
			if (findKeyword(statement,"how") >= 0 || findKeyword(statement, "yes") >= 0) {
				response = getRandomSolution();
				if (response.contains("Here's a solution for you.")) manualHangUp = true;
				// ^ there is one response where if you piss off the bot enough the bot flat out refuses to even give you a solution/hangs up
				progress = 4;
			}
			else if (findKeyword(statement, "no") == 0) { // well that's just plain rude
			    emotion --;
			    response = "Okay.";
			    progress = 4;
            }
			else {
			    response = "Do you want to hear the solution?";
            }
        }
        else if (progress == 4 && response.isEmpty()) {
            response = "Are you satisfied with the answer and the Eggs Dee™ customer service?";
            progress = 5;
        }
        else if (progress == 5 && response.isEmpty()) {
            if (emotion < -1.5) {
                if (findKeyword(statement,"yes") == 0) {
                    response = "You better be.";
                    manualHangUp = true;
                }
                if (findKeyword(statement,"no") == 0) { // this is one sassy bot
                    response = "I'm not satisfied with your cooperation.";
                    manualHangUp = true;
                }
            }
            else {
                if (findKeyword(statement,"yes") == 0) {
                    response = "Great! Thank you for your feedback, and have a good day.";
                    manualHangUp = true;
                }
                if (findKeyword(statement,"no") == 0) {
                    response = "I hope we can do better in the future. Thank you for calling and have a good day.";
                    manualHangUp = true;
                }
            }
        }
        else if (progress == 6 && response.isEmpty()) {
        	if (findKeyword(statement,"yes") >= 0 || findKeyword(statement,"ok") >= 0 || findKeyword(statement,"sure") == 0 || findKeyword(statement,"alright") == 0 || findKeyword(statement, "okay") == 0 || findKeyword(statement, "yeah") == 0) {
        		manualHangUp = true;
        		response = "Please hold.";
			}
			else {
				progress = 1;
				emotion -= 0.5;
				response = "Okay, how can I help you then?";
			}
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
			emotion -= 0.2; // lowering emotions so if the user spits out things that can't be recognized for long enough, the bot hangs up
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

	private String getRandomSolution() {
        String[] randomNeutralSolutions = {
                "It's simple, " + name + ". All you need to do is turn your " + problemObject + " on and off again.",
                "I can help you with that. Have you tried giving your " + problemObject + " a light slap?",
                "I would like for you to check if your " + problemObject + " is plugged in, see if that works.",
                "Maybe your " + problemObject + " has been turned off the whole time?"
        };
        String[] randomAngrySolutions = {
                "Just read the manual.",
                "Is it really that hard?",
                "Here's a solution for you."
        }; // i'm initializing the statements in here because the inserted variables need to update when this is called
	    Random x = new Random();
        int y = x.nextInt(randomAngrySolutions.length);
	    if (emotion >= -1) return randomNeutralSolutions[x.nextInt(randomNeutralSolutions.length)];
	    else {
	        return randomAngrySolutions[y];
        }
    }

	private String [] randomNeutralResponses = {"I couldn't catch that.", "I didn't hear you.", "Sorry, could you repeat that?"};
	private String [] randomAngryResponses = {"Sorry?", "Excuse me?", "What?", "What did you say to me?"};
	private String [] randomHappyResponses = {"Sorry, I didn't quite catch that.", "Could you please speak a little slower? Take your time.", "I didn't hear you that time."};
	
}
