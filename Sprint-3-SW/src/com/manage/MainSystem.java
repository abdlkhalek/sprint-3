package com.manage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainSystem {

	private  ArrayList<String> mailUsers = new ArrayList<>();
	private  ArrayList<String> smsUsers = new ArrayList<>();
	public  DataConnector data;
	public static  int type = 1;
	private NotificationModel notification = new NotificationModel();
	public  static Scanner get = new Scanner(System.in);
	private String subject;
	private String content;
	Sender sender;
	DataConnector connector1,connector2;
	public  void execute() throws SQLException, ClassNotFoundException {
		switch(type) {
		case 1:{
			System.out.print("\n1- Mails.\n 2- SMS\nEnter your choose :");
			type = get.nextInt();
			switch(type) {
				case 1:
				{
					data = new MailConnector();
					System.out.print("Enter Notification Subject : ");
					@SuppressWarnings("unused")
					String dumy = get.nextLine();
					subject = get.nextLine();
					System.out.println("Enter Content");
					content = get.nextLine();
					notification.setSubject(subject);
					notification.setContent(content);
					if (((MailConnector) data).create(notification)) {
						System.out.println("Created Successfully.");
						return;
					}
					System.out.println("Already Exist.");
				}
					break;
				case 2:
				{
					data = new SMSConnector();
					System.out.print("Enter Notification Content : \n Subject : ");
					subject = get.nextLine();
					content = get.nextLine();
					notification.setSubject(subject);
					notification.setContent(content);
					
					if (((SMSConnector) data).create(notification)) {
						System.out.println("Created Successfully.");
						return;
					}
						System.out.println("Already Exist.");
				}
					break;
			}
		}
			break;
		case 2:
		{
			System.out.print("Enter the number of Notification you wanna delete : ");
			type = get.nextInt();
			if (!((MailConnector) data).delete(type)) {
				System.out.println("Deleted successfully.");
			}
			else {
				System.err.println("Notification wasn't deleted");
			}
		}
			break;
		case 3:
			System.out.print("Enter notification number : ");
			type = get.nextInt();
			
			@SuppressWarnings("unused") 
			String n = get.nextLine();
			
			System.out.print("subject :");
			subject = get.nextLine();
			System.out.print("Content : ");
			content = get.nextLine();
			if (((MailConnector) data).update(type,subject,content)) {
				System.out.println("Updated Successfully.");
			}
			else {
				System.err.println("wasn't updated.");
			}
			break;
		case 4:
			System.out.print("Enter Notification Number : ");
			type = get.nextInt();
			if (!((MailConnector) data).read(type)) {
				System.err.println("Notification doesn't exist.");
			}	
			break;
		case 5:
			((MailConnector) data).read();
			break;
		default:
			System.out.println("Not a type of our service.");
			get.close();
		}
	}
	public  void defaultUser() {
		smsUsers.add("01023232323");
		smsUsers.add("01024242424");
		smsUsers.add("01025252525");
		mailUsers.add("abdu@gmail.com");
		mailUsers.add("example@gmail.com");

		mailUsers.add("root@gmail.com");
	}
	
	public void dequeuing(int type,String notificationType,String user) throws ClassNotFoundException, SQLException {
		switch(type) {
			
			case 1 :
				sender = new MailSender();
				
				if (mailUsers.contains(user) && !sender.send(notificationType)) {
					System.out.println("Notification Sent Successfully to " + user +".");
					mailUsers.remove(mailUsers.indexOf(user));
				}else System.out.println("failed to sent Mail Notification");
				break;
			
			case 2:
				sender = new SMSSender();
//				connector = new SMSConnector();
				if (smsUsers.contains(user) && !sender.send(notificationType)) {
					System.out.println("SMS was Sent Successfully to " + user + ".");
					smsUsers.remove(smsUsers.indexOf(user));
				}else System.out.println("Failed to Sent SMS Message.");
				
				break;
			default :
				System.out.println("Not such a type of sender");
				//get.close();
		}
	}
	
	public boolean getUsersSize() {
		return (smsUsers.size()>0 || mailUsers.size()>0) ?true:false;
	}
	
	public void menu() {
		System.out.println("1- Mails.\n 2- SMS\nEnter your choose :");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		MainSystem main = new MainSystem();
		main.connector1 = new SMSConnector();
		main.connector2 = new MailConnector();
		main.defaultUser();
		String notificationType,user;
		System.out.println("Hello You're allowed To Send Notification Now.");
		do {
			main.menu();
			type = get.nextInt();
			System.out.println("Enter Notification Type  :");
			user = get.nextLine();
			notificationType= get.nextLine();
			System.out.println("Enter User :");
			user = get.nextLine();
			main.dequeuing(type, notificationType, user);
		}while(main.getUsersSize());
		get.close();
		//((MailConnector)main.connector2).delete(22);
//			main.execute();
	}
}
