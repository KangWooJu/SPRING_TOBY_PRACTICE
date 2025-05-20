package org.kangwooju.spring_toby_practice.domain.user.Service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import java.util.*;

public class MockMailSender implements MailSender {

    private List<String> requests = new ArrayList<String>();

    public List<String> getRequests(){
        return requests;
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        requests.add(simpleMessage.getTo()[0]); // 전송 요청을 받은 이메일 주소를 저장
    }
}
