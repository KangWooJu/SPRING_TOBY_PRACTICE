package org.kangwooju.spring_toby_practice.domain.message.entity;

public class Message {

    String text;

    private Message(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public static Message newMessage(String text){ // 스태틱 팩토리 메소드 제공
        return new Message(text);
    }
}
