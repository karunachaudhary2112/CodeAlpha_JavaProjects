import java.util.Scanner;

public class Task2_AiChatbot {

    static String getReply(String input) {
        // basic text processing: trim + lowercase
        String msg = input.trim().toLowerCase();

        if (msg.isEmpty()) {
            return "Please type something.";
        } else if (msg.equals("hi") || msg.contains("hello") || msg.contains("hey")) {
            return "Hello! How can I help you today?";
        } else if (msg.contains("your name")) {
            return "I'm CodeBot, a simple Java chatbot.";
        } else if (msg.contains("oop")) {
            return "OOP stands for Object-Oriented Programming. Its 4 pillars are "
                    + "Encapsulation, Inheritance, Polymorphism and Abstraction.";
        } else if (msg.contains("java")) {
            return "Java is an object-oriented programming language used for apps, "
                    + "web and backend systems.";
        } else if (msg.contains("joke")) {
            return "Why do Java developers wear glasses? Because they can't C#!";
        } else if (msg.contains("help")) {
            return "You can ask me about: Java, OOP, my name, jokes, or type 'bye' to exit.";
        } else {
            return "Sorry, I didn't understand that. Try asking about Java, OOP, or say 'help'.";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== JAVA CHATBOT =====");
        System.out.println("Bot: Hi! I'm CodeBot. Ask me something, or type 'bye' to exit.");

        while (true) {
            System.out.print("\nYou: ");
            String input = sc.nextLine();

            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println("Bot: Goodbye! Have a great day!");
                break;
            }
            System.out.println("Bot: " + getReply(input));
        }
        sc.close();
    }
}
