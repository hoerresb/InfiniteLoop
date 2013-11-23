package edu.uwm.cs361;

import java.io.IOException;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@SuppressWarnings("serial")
public class EmailTestServlet extends HttpServlet {
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String email = req.getParameter("email");
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
 
        String msgBody = name + "\n" + description + "\n" + email;
 
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("njbuwm@gmail.com",
                    "Your admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    "njbuwm@gmail.com", "Your name"));
            msg.setSubject("Feedback");
            msg.setText(msgBody);
            Transport.send(msg);
 
        } catch (Exception e) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Something went wrong. Please try again.");
            throw new RuntimeException(e);
        }
 
        resp.setContentType("text/plain");
        resp.getWriter().println(
                "Thank you for your feedback. An Email has been send out.");
    }
}