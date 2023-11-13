package topicproblem;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Producer {

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
		
		try  {
			 context = connectionFactory.createContext("murad","Murad@1234");
			String message = "This is message from producer gulam samdani";
			System.out.println("Sending message: " + message);
			context.createProducer().send(topic, message);
		} catch (JMSRuntimeException e) {
			System.err.println("Exception occurred: " + e.toString());
			System.exit(1);
		}finally {
			ctx.close();
			context.close();
		}
		System.exit(0);
	}

}
