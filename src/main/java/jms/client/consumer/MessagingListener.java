package jms.client.consumer;

import java.util.concurrent.atomic.AtomicLong;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MessagingListener implements MessageListener {
	AtomicLong count = new AtomicLong(0);

	public void onMessage(Message m) {
		long i;

		try {
			
			
			if (m instanceof MapMessage) {
				i = count.incrementAndGet();
				MapMessage mapMessage = (MapMessage) m;

				String userId = mapMessage.getString("User_ID");
                String courseId = mapMessage.getString("Course_ID");
                String courseName = mapMessage.getString("Course_Name");
                String dateOfRegistration = mapMessage.getString("Date_of_Registration");

                System.out.println("Received Message: standalone program output--->>>");
                System.out.println("User_ID: " + userId);
                System.out.println("Course_ID: " + courseId);
                System.out.println("Course_Name: " + courseName);
                System.out.println("Date_of_Registratio: " + dateOfRegistration);
				
				
			} else {
                System.out.println("Received message is not a MapMessage.");
			}
			
		} catch (JMSException e) {
			System.err.println("Exception in onMessage(): " + e.toString());
		}
	}

	public long getCount() {
		return count.get();
	}

}
