package com.hn004.reddit.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.NotificationEmail;
import com.hn004.reddit.Service.CustomException.StringRedditException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
//@NoArgsConstructor
@AllArgsConstructor
@Data
public class MailService {

//	@Autowired(required=true)
	private final JavaMailSender mailSender;

//	public MailService(JavaMailSender mailSender) {
//		super();
//		this.mailSender = mailSender;
//	}

//	public MailService() {
//		this.mailSender = null;
////		super();
//		// TODO Auto-generated constructor stub
//	}

//	@Autowired
	private final MailContentBuilder mailContentBuilder;
    @Async
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("35fe3fef4a-589042@inbox.mailtrap.io");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
			log.info("email sent!");

		} catch (MailException e) {
			throw new StringRedditException("exception occured");
		}
	}

}
