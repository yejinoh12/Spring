package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //hello.aop.order 패키지와 하위 패키지
    //반환타입은 void 여야 함
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} //메서드 이름과 파라미터를 합쳐서 포인트 컷 시그니처라고 함

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder() && allService()
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
