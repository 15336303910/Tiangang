package cn.plou.web.common.exception;

import cn.plou.web.common.config.common.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/28 8:46
 * 参数校验处理器
 */
public class BindingResultHandler {
    public  static   void  handle(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(
                    fieldError -> {
                        //开发用，方便调试
                        // throw  new RuntimeException("参数绑定错误!错误参数 :"+fieldError.getField()+",错误信息为 :"+ fieldError.getDefaultMessage());
                        //正式部署用，只返回错误信息给用户
                        throw  new BusinessException(fieldError.getDefaultMessage());

                    }
            );
        }

    }
}
