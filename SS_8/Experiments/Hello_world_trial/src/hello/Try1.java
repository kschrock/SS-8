package hello;

import java.util.Scanner;

public class Try1 {
	public static void main(String[] args) {
	System.out.println("v2");
	System.out.println("hello world");
	System.out.println("how are you today?");
	System.out.println("\nwelcome to calculator 3000");
	Scanner sc = new Scanner(System.in);
	int result =0;
	int choice =0;
	start: //label to break out of while loop if conditions become satisfied
		while(true){
	System.out.println("press 1 for addition, 2 for subtraction, 3 for multiplication, or 4 for division");
    choice = sc.nextInt();
    if (choice == 1 || choice == 2 || choice == 3 || choice == 4) { //if correct input
    	break start; //break out of while label containing while loop
    	}
    System.out.println("not a valid input, please try again");
	}
	System.out.println("okay pick number 1");
	int a = sc.nextInt();
	System.out.println("okay pick number 2");
	int b = sc.nextInt();
	switch(choice) {	//switch casing for arithmetic operation chosen
	case 1: result =a+b;
		break;
	case 2: result =a-b;
		break;
	case 3: result =a*b;
		break;
	case 4: result =a/b;
		break;
		}
	System.out.println("your result is: " + result);
	System.out.println("have a nice day");
	sc.close();
	}
	
}
