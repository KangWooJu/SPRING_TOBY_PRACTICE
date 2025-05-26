package org.kangwooju.spring_toby_practice.domain.user.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    private Object target; // 어떤 종류의 인터페이스를 구현한 타켓도 적용 가능하도록 Object 타입으로 수정

    public UppercaseHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String)method.invoke(target,args); // 타켓으로 위임. 인터페이스 메소드 호출에 모두 적용된다.
        if(ret instanceof String){ // 호출한 메소드의 리턴 타입이 String인 경우만 대문자 변경 기능을 적용하도록 수정
            return ret.toUpperCase();
        }
        return ret;
    }
}
