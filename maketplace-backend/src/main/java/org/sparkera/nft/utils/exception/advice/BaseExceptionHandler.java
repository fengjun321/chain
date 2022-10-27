package org.sparkera.nft.utils.exception.advice;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.utils.ResultUtil;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.TranslateUtil;
import org.sparkera.nft.utils.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 统一异常处理类
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @Autowired
    private HttpServletRequest req;

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultVo handleXCloudException(CommonException e) {

        String errorMsg = "操作失败";
        String errorAll=e.getMessage();
        if (e != null) {
            errorMsg = e.getMessage();
            e.printStackTrace();
            log.error(e.toString());
        }
        boolean isM=false;
        String othMsg="";
        if (errorMsg.indexOf(":")>0){
            isM=true;
            //包含变量
            String[] split = errorMsg.split(":");
            errorMsg=split[0];
            othMsg=split[1];
        }
        String value = req.getHeader("language");
        if (value.equals("cn")) {
            return new ResultUtil().setErrorMsg(500, errorAll);
        } else if (value.equals("eu")) {
            String msg="";
            if (isM){
                try {
                    errorMsg=errorMsg.replace(",","");
                    msg= TranslateUtil.handle(errorMsg);
                    if (StringUtils.isEmpty(msg)){
                        return new ResultUtil().setErrorMsg(500, e.getMessage());
                    }
                    return new ResultUtil().setErrorMsg(500, msg+othMsg);
                }catch (Exception e1){
                    return new ResultUtil().setErrorMsg(500, e.getMessage());
                }
            }else {
                try {
                    errorMsg=errorMsg.replace(",","");
                    errorMsg=TranslateUtil.handle(errorMsg);
                    if (StringUtils.isEmpty(errorMsg)){
                        return new ResultUtil().setErrorMsg(500, e.getMessage());
                    }
                    return new ResultUtil().setErrorMsg(500, errorMsg);
                }catch (Exception e1){
                    return new ResultUtil().setErrorMsg(500, e.getMessage());
                }
            }
        }
        e.printStackTrace();
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultVo handleException(Exception e) {
        e.printStackTrace();
        String errorMsg="Exception";
        if (e!=null){
            errorMsg=e.getMessage();
            e.printStackTrace();
            log.error(e.toString());
        }
        return new ResultUtil().setErrorMsg(500, errorMsg);
    }
}
