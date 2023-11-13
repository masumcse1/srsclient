package jms.client.consumer;

import java.util.Enumeration;
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

				Enumeration enumeration = ((MapMessage) m).getMapNames();
				while (enumeration.hasMoreElements()) {
					String key = (String) enumeration.nextElement();

				}

				System.out.println("Reading message: " + m.getBody(MapMessage.class));
			} else {
				System.err.println("Message is not a TextMessage");
			}
		} catch (JMSException e) {
			System.err.println("Exception in onMessage(): " + e.toString());
		}
	}

	public long getCount() {
		return count.get();
	}

}
