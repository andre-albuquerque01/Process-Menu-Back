package com.ae.tech.ProcessMenu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ae.tech.ProcessMenu.entity.dto.RegisterDTO;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;

@Service
public class MailSenderService {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public SimpleMailMessage template;

	public void sendNewMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

	public void recoverPassword(String email) throws Exception {
		UserDetails userDetails = userRepository.findByEmail(email);
		if (userDetails == null) {
			throw new Exception("usuário não localizado");
		}
		var newRandom = RandomService.generateRandomAlphaNumeric(9);
		String encrytedPassword = new BCryptPasswordEncoder().encode(newRandom);

		User userToUpdate = (User) userDetails;
		userToUpdate.setPassword(encrytedPassword);
		userRepository.save(userToUpdate);

		String text = String.format(template.getText());
		String subject = "Recover password";

		sendNewMail(email, subject, text + newRandom + "\n\n\nAtenciosamente\nEquipe de segurança.");
	}
}
