package jms.client.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MyListener implements MessageListener{
	
	
	  public void onMessage(Message message) {
          try {
              if (message instanceof MapMessage) {
                  MapMessage mapMessage = (MapMessage) message;

                  // Extract data from the MapMessage
                  String user_ID = mapMessage.getString("User_ID");
                  String course_ID = mapMessage.getString("Course_ID");

                  System.out.println("Received MapMessage: clien app");
                  System.out.println("User_ID--<<: " + user_ID);
                  System.out.println("Age:--->> " + course_ID);
              } else {
                  System.out.println("Received message is not a MapMessage.");
              }
          } catch (JMSException e) {
              e.printStackTrace();
          }
      }
  }


