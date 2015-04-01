package edu.rosehulman.mjcommons;

public interface mjPlugin {
	public void stop();
	
	public Message getMessage();
	public void receiveMessage(Message m);
}
