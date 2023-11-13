package jms.client.consumer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

public class MessageReader {

	private static ConnectionFactory connectionFactory;
	
	public static void main(String[] args) throws NamingException {

		Context ctx = new InitialContext();
		
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		env.put(Context.SECURITY_PRINCIPAL, "murad");
		env.put(Context.SECURITY_CREDENTIALS, "Murad@1234");
		ctx = new InitialContext(env);
			
		connectionFactory = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
		Topic topic = (Topic) ctx.lookup("jms/topic/myTopic");
		JMSContext context = null;

		try {
			context = connectionFactory.createContext("murad","Murad@1234");
			JMSConsumer consumer = context.createSharedConsumer(topic, "SubName");
			// JMSConsumer consumer=context.createSharedDurableConsumer(topic,
			// "MakeItLast");
			System.out.println("Waiting for messages on topic");
			MessagingListener listener = new MessagingListener();
			consumer.setMessageListener(listener);
			System.out.println("To end program, enter Q or q, " + "then <return>");
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			char answer = '\0';
			while (!((answer == 'q') || (answer == 'Q'))) {
				try {
					answer = (char) inputStreamReader.read();
				} catch (IOException e) {
					System.err.println("I/O exception: " + e.toString());
				}
			}
			//System.out.println("Text messages received: " + listener.getCount());
		} catch (JMSRuntimeException e) {
			System.err.println("Exception occurred: " + e.toString());
			System.exit(1);
		} finally {
			ctx.close();
			context.close();
		}

		System.exit(0);

	}

}
