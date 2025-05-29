package org.kangwooju.spring_toby_practice.domain.user.factory.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {

    @Override
    public void setMappedName(String mappedNamePattern) {
        this.setClassFilter(new SimpleClassFilter(mappedNamePattern));
        // 모든 클래스를 다 허용하던 디폴트 클래스 필터를 프로퍼티로 받은 클래스 이름을 이용해서 필터를 만들어 덮어 씌운다.
    }

    static class SimpleClassFilter implements ClassFilter{

        String mappedName;

        private SimpleClassFilter(String mappedName){
            this.mappedName = mappedName;
        }
        @Override
        public boolean matches(Class<?> clazz) {
            return PatternMatchUtils
                    .simpleMatch(mappedName,clazz.getSimpleName());
                    // simpleMatch : 와일드 카드가 들어간 문자열 비교를 지원하는 스프링의 유틸리티 메소드
        }
    }
}
