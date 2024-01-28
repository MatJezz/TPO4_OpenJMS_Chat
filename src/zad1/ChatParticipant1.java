package zad1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ChatParticipant1 {
    private static MessageConsumer consumer;

    public static void main(String[] args) {
        try {
            Context ctx = new InitialContext();

            ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");

            Topic chatTopic = (Topic) ctx.lookup("ChatTopic");

            Connection connection = connectionFactory.createConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(chatTopic);

            consumer = session.createConsumer(chatTopic);

            connection.start();

            Scanner scanner = new Scanner(System.in);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    checkForMessages();
                }
            }, 1000, 1000);

            while (true) {
                String messageText = scanner.nextLine();
                String username = "Ucczestnik1";
                TextMessage textMessage = session.createTextMessage(username + ": " + messageText);
                producer.send(textMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkForMessages() {
        try {
            Message message = consumer.receiveNoWait();
            if (message != null) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String receivedMessage = textMessage.getText();

                    System.out.println(receivedMessage);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
