package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면  JDK 동적 프록시 사용")
    void  interfaceProxy(){
        ServiceInterface target = new ServiceImpl();
        //생성자에 프록시의 호출 대상을 함께 넘겨줌
        //프록시는 인스턴스 기반으로 프록시를 만들어냄
        //만약 이 인스턴스에 인터페이스가 있다면 JDK 동적 프록시를 기본으로 사용
        //구체 클래스만 있다면  CGLIB를 통해 동적 프록시를 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //프록시 팩토리를 통해서 만든 프록시가 사용할 부가 기능 로직을 설정
        //Advice -> 프록시가 제공하는 부가 기능
        proxyFactory.addAdvice(new TimeAdvice());
        //프록시를 객체를 생성하고 그 결과를 받는다
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); //proxyClass=class jdk.proxy3.$Proxy13

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void  concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고 클래스 기반 프록시 사용")
    void  proxyTargetClass(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 스프링 부트는 AOP 적용 시 기본 적으로 proxyFactory.setProxyTargetClass(true); 로 설정하여 사용
        proxyFactory.setProxyTargetClass(true); // 구체 클래스를 상속받아 CGLIB 사용
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); //proxyClass=class hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$72c0553e

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
