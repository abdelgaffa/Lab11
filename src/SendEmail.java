import com.sun.jdi.connect.Transport;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.sql.DataSource;

public class SendEmail {
    public static void main(String [] args) {
        // user inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email: ");
        String from = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        System.out.println("Enter the recipient's email: ");
        String to = scanner.nextLine();

        System.out.println("Enter your group number: ");
        String groupNumber = scanner.nextLine();

        System.out.println("Enter your last name and first name: ");
        String fullName = scanner.nextLine();

        System.out.println("Enter your city: ");
        String city = scanner.nextLine();

        System.out.println("Describe today's weather: ");
        String weatherDescription = scanner.nextLine();

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(groupNumber + ", " + fullName);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText("Date: " + new Date() + "\nLocation: " + city + "\nWeather: " + weatherDescription);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach image
            messageBodyPart = new MimeBodyPart();
            String filename = "E:/my projects/AhmedLaps/cat.jpg";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Message sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
