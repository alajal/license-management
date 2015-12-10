package ee.cyber.licensing.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ee.cyber.licensing.entity.MailBody;

import java.util.List;

public class SendMailTLS{

	static Properties mailServerProperties;
	static Properties props;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	static InputStream input = null;

	public static void generateAndSendEmailWithoutFile(MailBody mailbody, List<String> receivers) throws AddressException, MessagingException {

		String subject = mailbody.getSubject();
		String body = mailbody.getBody();

		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		try {
			input = SendMailTLS.class.getClassLoader().getResourceAsStream("config.properties");
			mailServerProperties = System.getProperties();
			mailServerProperties.load(input);
		}

		catch(IOException ex) {
			ex.printStackTrace();
		}

		finally {
			if(input != null) {
				try {
					// Step2
					final String email = mailServerProperties.getProperty("fromEmail");
					final String password = mailServerProperties.getProperty("password");
					final String host = mailServerProperties.getProperty("mail.smtp.host");

					System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

					Authenticator authentication = new Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(email, password);
							}
					};

					System.out.println("Mail Server Properties have been setup successfully..");

					// Step3
					System.out.println("\n\n 3rd ===> get Mail Session..");
					getMailSession = Session.getDefaultInstance(mailServerProperties, authentication);
					generateMailMessage = new MimeMessage(getMailSession);
					for(String receiver : receivers) {
						generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
					}
					generateMailMessage.setSubject(subject);
					String emailBody = body + "<br><br> Regards, <br>Jens-Stefan";
					generateMailMessage.setContent(emailBody, "text/html");
					System.out.println("Mail Session has been created successfully..");

					// Step4
					System.out.println("\n\n 4th ===> Get Session and Send mail");
					Transport transport = getMailSession.getTransport("smtp");

					// Enter your correct gmail UserID and Password
					// if you have 2FA enabled then provide App Specific Password
					transport.connect(host, email, password);
					transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
					transport.close();
					input.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void generateAndSendEmailWithFile(MailBody mailbody, List<String> receivers, String file_name, byte[] file_bytes) throws AddressException, MessagingException {

		String subject = mailbody.getSubject();
		String body = mailbody.getBody();

		// Step0
		try {
			input = SendMailTLS.class.getClassLoader().getResourceAsStream("config.properties");
			props = System.getProperties();
			props.load(input);
		}

		catch(IOException ex) {
			ex.printStackTrace();
		}

		finally {
			if(input!=null) {
				try {
					// Step1
					System.out.println("\n 1st ===> setup Mail Server Properties..");

					final String email = props.getProperty("fromEmail");
					final String password = props.getProperty("password");

					System.out.println("Mail Server Properties have been setup successfully..");

					// Step2
					System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

						Authenticator authentication = new Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
										return new PasswordAuthentication(email, password);
								}
						};

					// Step3
					System.out.println("\n\n 3rd ===> get Mail Session..");
					getMailSession = Session.getInstance(props, authentication);
					System.out.println(getMailSession);

					try {
			            System.out.println("\n4th ===> generateAndSendEmail() starts..");
									System.out.println(getMailSession);
			            MimeMessage crunchifyMessage = new MimeMessage(getMailSession);
			            crunchifyMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			            crunchifyMessage.addHeader("format", "flowed");
			            crunchifyMessage.addHeader("Content-Transfer-Encoding", "8bit");

			            crunchifyMessage.setFrom(new InternetAddress("ametliktest@gmail.com",
			                    "NoReply-Crunchify"));
			            crunchifyMessage.setReplyTo(InternetAddress.parse("ametliktest@gmail.com", false));
			            crunchifyMessage.setSubject(subject, "UTF-8");
			            crunchifyMessage.setSentDate(new Date());

			            //crunchifyMessage.setRecipients(Message.RecipientType.TO,
			            //        InternetAddress.parse(toEmail, false));

									for(String receiver : receivers) {
										crunchifyMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
									}

									// Create a multipart message for attachment
									Multipart multipart = new MimeMultipart();

			            // Create the message body part
			            BodyPart messageBodyPart = new MimeBodyPart();
			            messageBodyPart.setContent(body, "text/html");




			            // Set text message part
			            multipart.addBodyPart(messageBodyPart);

									//Attachment part
			            messageBodyPart = new MimeBodyPart();

			            // Valid file location
									ByteArrayDataSource source = new ByteArrayDataSource(file_bytes, "application/octet-stream");
									System.out.println(source);
			            messageBodyPart.setDataHandler(new DataHandler(source));
			            messageBodyPart.setFileName(file_name);
			            multipart.addBodyPart(messageBodyPart);

			            System.out.println("\n5th ===> Finally Send message..");

			            // Finally Send message
			            //Transport.send(crunchifyMessage);
									System.out.println(getMailSession);
									crunchifyMessage.setContent(multipart);
									Transport transport = getMailSession.getTransport("smtp");
									final String host = props.getProperty("mail.smtp.host");
									// Enter your correct gmail UserID and Password
									// if you have 2FA enabled then provide App Specific Password
									transport.connect(host, email, password);
									transport.sendMessage(crunchifyMessage, crunchifyMessage.getAllRecipients());
									transport.close();

			            System.out
			                    .println("\n6th ===> Email Sent Successfully With Image Attachment. Check your email now..");
			            System.out.println("\n7th ===> generateAndSendEmail() ends..");

			        } catch (MessagingException e) {
			            e.printStackTrace();
			        } catch (UnsupportedEncodingException e) {
			            e.printStackTrace();
			        }
							input.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
