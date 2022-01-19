package com.hashtech.config.validate;

import com.hashtech.config.SystemCodeService;
import com.hashtech.config.exception.ParamException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author liyanhui
 */
@Aspect
@Component
public class BusinessParamsValidateAspect {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 参数校验对象
     */
    @Autowired
    private Validator validator;

    /**
     * 消息源资源对象
     */
    @Autowired
    private SystemCodeService systemCodeService;

    /**
     * 获取对应编码的信息描述
     *
     * @param code 编码
     * @return
     */
    public String getMessage(String code) {
        return this.systemCodeService.getMessageOptional(code).orElse("Invalid Parameter");
    }

    /**
     * 定义切面，扫描所有service的实现类
     */
    @Pointcut("@annotation(com.hashtech.config.validate.BusinessParamsValidate)")
    public void validatePointcut() {
    }

    /**
     * 使用环绕通知对service实现类的参数进行检查
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("validatePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = joinPoint.getTarget().getClass().getMethod(
                methodSignature.getName(), methodSignature.getParameterTypes());

        logger.debug("正在对类{}中方法{}进行参数检查", joinPoint.getTarget().getClass().getName(), targetMethod.getName());

        // 查找带有参数校验的注解
        com.hashtech.config.validate.BusinessParamsValidate paramsValidate = targetMethod.getAnnotation(com.hashtech.config.validate.BusinessParamsValidate.class);
        if (paramsValidate == null) {
            return joinPoint.proceed();
        }

        // 遍历方法的所有参数，通过注解指定需要检查的索引，逐个检查
        int[] needCheckParamIndexes = paramsValidate.argsIndexs();

        // 校验分组，如果未设置，使用默认的分组
        Class<?>[] groupsClazz = paramsValidate.groups();

        Object[] args = joinPoint.getArgs();
        for (int index : needCheckParamIndexes) {
            Object arg = args[index];

            Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(arg, groupsClazz);
            if (constraintViolationSet != null && constraintViolationSet.size() > 0) {
                for (ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                    String code = constraintViolation.getMessage();
                    throw new ParamException(code, getMessage(code));
                }
            }
        }

        logger.debug("类{}中方法{}参数检查通过", joinPoint.getTarget().getClass().getName(), targetMethod.getName());
        // 参数检测通过
        return joinPoint.proceed();
    }
}
