package org.kangwooju.spring_toby_practice.domain.message.factory;

import org.kangwooju.spring_toby_practice.domain.message.entity.Message;
import org.springframework.beans.factory.FactoryBean;

public class messageFactory implements FactoryBean<Message> {

    String text;

    public messageFactory(String text){
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(this.text);
    }

    @Override
    public Class<? extends Message> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
