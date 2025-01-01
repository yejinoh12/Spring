package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {

//       @Around 는 ProceedingJoinPoint 를 사용해야 함
//       proceed() 를 통해 다음 어드바이스나 타켓을 호출해야하기 때문
//       @Around 어노테이션을 달고 proceed() 하지 않으면, 타켓이 호출되지 않는 심각한 버그 발생


//    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try{
//            //@Before
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//            return result;
//        }catch (Exception e){
//            //@AfterThrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        }finally {
//            //@After
//            log.info("[리소스 릴리스] {}", joinPoint.getSignature());
//        }
//    }

    //조인 포인트 실행 이전에 실행
    //작업 흐름을 변경하지 않음
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());

    }

    //조인 포인트가 정상 완료후 실행
    //returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 함
    //returning 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행 됨
    //반환 객체 조작 불가, 객체 변경하려면 @Around 사용
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return = {}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, String result) {
        log.info("[return2] {} return = {}", joinPoint.getSignature(), result);
    }

    //메서드가 예외를 던지는 경우 실행
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message = {}", joinPoint.getSignature(), ex.getMessage());
    }

    //조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
    //일반적으로 리소스 해제 시 사용
    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doThrowing(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
