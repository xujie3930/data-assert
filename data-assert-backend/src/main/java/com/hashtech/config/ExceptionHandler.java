package com.hashtech.config;

import com.hashtech.common.AppException;
import com.hashtech.config.exception.*;
import feign.codec.DecodeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 描述 ：异常处理
 *
 * @author liyanhui
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver, Ordered, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Value("${system.errorCode}")
    private String systemErrorCode;

    @Value("${system.errorMsg:系统处理异常，请联系管理员！}")
    private String DEFAULT_ERR_MSG;

    @Autowired
    private SystemCodeService systemCodeService;

    private ApplicationContext context;

    @Override
    public int getOrder() {
        return -2147483648;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return resolveException0(request, response, handler, ex);
    }

    private ModelAndView resolveException0(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String errorCode = systemErrorCode, errorMsg = DEFAULT_ERR_MSG, errorDetail = getErrorStack(ex);
        if (ex instanceof DecodeException) {
            DecodeException decodeException = (DecodeException) ex;
            Throwable throwable = decodeException.getCause();
            if (throwable instanceof AppException) {
                AppException appException = (AppException) throwable;
                errorCode = appException.getErrorCode();
                errorMsg = appException.getErrorMsg();
                if (StringUtils.isEmpty(errorMsg)) {
                    errorMsg = systemCodeService.getMessage(errorCode, appException.getAll());
                }
                LOGGER.error(errorMsg + " : " + errorCode, appException.getCause());
            }
        } else if (ex instanceof AppException) {
            AppException appException = (AppException) ex;
            errorCode = appException.getErrorCode();
            errorMsg = appException.getErrorMsg();
            if (StringUtils.isEmpty(errorMsg)) {
                errorMsg = systemCodeService.getMessage(errorCode, appException.getAll());
            }
            this.publishEvent(new AppExceptionEvent(appException));
            LOGGER.error(errorMsg + " : " + errorCode, appException.getCause());
        } else if (ex instanceof FrameworkException) {
            FrameworkException frameworkException = (FrameworkException) ex;
            errorCode = frameworkException.getErrorCode();
            errorMsg = frameworkException.getErrorMsg();
            if (StringUtils.isEmpty(errorMsg)) {
                errorMsg = systemCodeService.getMessage(errorCode);
            }
            LOGGER.error(errorMsg + " : " + errorCode, frameworkException.getCause());
        } else if (ex instanceof IgnoredException) {
            IgnoredException ignoredException = (IgnoredException) ex;
            errorCode = ignoredException.getErrorCode();
            errorMsg = ignoredException.getErrorMsg();
            if (StringUtils.isEmpty(errorMsg)) {
                errorMsg = systemCodeService.getMessage(errorCode);
            }
            LOGGER.warn(errorMsg + " : " + errorCode, ignoredException.getCause());
        } else if (ex instanceof SystemException) {
            SystemException systemException = (SystemException) ex;
            errorCode = systemException.getErrorCode();
            errorMsg = systemException.getErrorMsg();
            if (StringUtils.isEmpty(errorMsg)) {
                errorMsg = systemCodeService.getMessage(errorCode);
            }
            this.publishEvent(new SystemExceptionEvent(systemException));
            LOGGER.error(errorMsg + " : " + errorCode, systemException.getCause());
        } else if (ex instanceof DataAccessException) {
            DataAccessException dataAccessException = (DataAccessException) ex;
            errorCode = dataAccessException.getErrorCode();
            errorMsg = dataAccessException.getErrorMsg();
            if (StringUtils.isEmpty(errorMsg)) {
                errorMsg = systemCodeService.getMessage(errorCode);
            }
            LOGGER.error(errorMsg + " : " + errorCode, dataAccessException.getCause());
        } else if (ex instanceof ParamException) {
            ParamException paramException = (ParamException) ex;
            errorCode = paramException.getErrorCode();
            errorMsg = paramException.getErrorMsg();
            LOGGER.error(errorMsg);
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                errorCode = item.getMessage();
                break;
            }
            errorMsg = systemCodeService.getMessageOptional(errorCode).orElse("Invalid Parameter");
            LOGGER.error(errorMsg);
        } else {
//            errorMsg = ex.getMessage();
            LOGGER.error("Uncaught exception", ex);
        }

        if (DEFAULT_ERR_MSG.equals(errorMsg)) {
            errorMsg = systemCodeService.getMessageOptional(errorCode).orElse(DEFAULT_ERR_MSG);
        }
        CommonOuterResponse commonOuterResponse = new CommonOuterResponse();
        commonOuterResponse.setReturnCode(errorCode);
        commonOuterResponse.setReturnMsg(errorMsg);
        commonOuterResponse.setReturnError(errorDetail);

        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getOutputStream().write(commonOuterResponse.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception ee) {
            LOGGER.error("resolveException", ee);
        }

        return new ModelAndView();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    void publishEvent(BaseEvent event) {
        this.context.publishEvent(event);
    }

    /**
     * 获取错误堆栈信息
     * @author  maoc
     * @create  2022/6/24 13:52
     * @desc
     **/
    private String getErrorStack(Exception ex){
        if(null==ex){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try{
            //String property = System.getProperty("line.separator");
            StackTraceElement[] stackTrace = ex.getStackTrace();
            String message = ex.getMessage();
            sb.append(ex.toString());
            if(!org.springframework.util.StringUtils.isEmpty(message)){
                sb.append(":").append(message);
            }
            if(null!=stackTrace && stackTrace.length>0){
                for(int i=0;i<stackTrace.length;i++){
                    StackTraceElement st = stackTrace[i];
                    sb.append("\\r\\n").append("    at ").append(st.getClassName()).append(".").append(st.getMethodName()).append("(").append(st.getFileName()).append(":").append(st.getLineNumber()).append(")");
                }
            }
        }finally {
            return sb.toString();
        }
    }
}
