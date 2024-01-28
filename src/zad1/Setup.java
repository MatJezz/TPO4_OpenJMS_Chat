package zad1;

import org.exolab.jms.administration.AdminConnectionFactory;
import org.exolab.jms.administration.JmsAdminServerIfc;

import javax.jms.JMSException;
import java.net.MalformedURLException;

public class Setup {
    public static void main(String[] args) throws MalformedURLException, JMSException {
        String url = "tcp://localhost:3035/";
        JmsAdminServerIfc admin = AdminConnectionFactory.create(url);

        String topic = "ChatTopic";
        Boolean isQueue = Boolean.FALSE;
        if (!admin.addDestination(topic, isQueue)) {
            System.err.println("Failed to create topic " + topic);
        }
    }
}
