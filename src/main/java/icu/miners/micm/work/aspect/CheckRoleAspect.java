package icu.miners.micm.work.aspect;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.StudentService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.aspect
 * @version: v1.0.0
 */
@Component
@Aspect
public class CheckRoleAspect {

    @Resource
    private StudentService studentService;

    @Pointcut("@within(icu.miners.micm.work.annotation.CheckRole) || @annotation(icu.miners.micm.work.annotation.CheckRole)")
    public void pointcut() {
    }


    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) throws Exception {
        String name = joinPoint.getSignature().getName();
        //用的最多通知的签名
        Signature signature = joinPoint.getSignature();
        MethodSignature msg=(MethodSignature) signature;
        Object target = joinPoint.getTarget();
        //获取注解标注的方法
        Method method = target.getClass().getMethod(msg.getName(), msg.getParameterTypes());
        //通过方法获取注解
        CheckRole checkRole = method.getAnnotation(CheckRole.class);
        short value = checkRole.value();
        Student student = studentService.getCurrentUser();
        if (student == null) {
            throw new Exception("还未登陆");
        }
        if (student.getRole() != value) {
            throw new Exception("权限不足");
        }
    }

    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "结束执行");
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "返回" + result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "抛出异常" + e.getMessage());
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }
}
