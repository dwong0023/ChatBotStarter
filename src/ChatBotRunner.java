import java.util.Scanner;

/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		ChatBot1 chatbot1 = new ChatBot1();
		ChatBot2 chatbot2 = new ChatBot2();
		ChatBot3 chatbot3 = new ChatBot3();
		ChatBot4 chatbot4 = new ChatBot4();

		Scanner in = new Scanner (System.in);
		System.out.println("Welcome to the Chatbot representing Eggs Dee™, an egg company.");
		System.out.println("Please type 1 for a salesperson (by Zeng Chen),");
		System.out.println("2 for customer support (by Jesse Han),");
		System.out.println("3 for the manager if you want a job (by Daniyah Wong),");
		System.out.println("or 4 for the health inspector (by Parker Ahmed).");
		String statement = in.nextLine();


		while (!statement.equals("Bye"))
		{
			//Use Logic to control which chatbot is handling the conversation\
			//This example has only chatbot1


			if (statement.equals("1")) {
				chatbot1.chatLoop(statement);
			}
			else if (statement.equals("2")) {
				chatbot2.chatLoop(statement);
			}
			else if (statement.equals("3")) {
			    chatbot3.chatLoop(statement);
            }
            else if (statement.equals("4")) {
                chatbot4.chatLoop(statement);
            }
            else {
                System.out.println("Sorry, I didn't catch anything. Your options are 1, 2, 3, or 4.");
                in = new Scanner(System.in);
            }

			statement = in.nextLine();


		}
	}

}
