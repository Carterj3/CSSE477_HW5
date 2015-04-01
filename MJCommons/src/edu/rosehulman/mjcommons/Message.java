package edu.rosehulman.mjcommons;

public class Message {

	public String sender;
	public String receiver;
	public String message;
	
	public Message(String sender, String receiver, String message){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
}
