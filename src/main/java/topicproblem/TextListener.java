package topicproblem;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class TextListener implements MessageListener {
	AtomicLong count = new AtomicLong(0);

	public void onMessage(Message m) {
		long i;

		try {
			if (m instanceof TextMessage) {
				i = count.incrementAndGet();
				System.out.println("Reading message: " + m.getBody(String.class));
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
