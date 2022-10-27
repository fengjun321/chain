package org.sparkera.nft.utils;

public class ResultUtil {
    private ResultVo result;

    public ResultUtil(){
        result=new ResultVo();
        result.setSuccess(true);
        result.setMsg("success");
        result.setCode(200);
    }

    public ResultVo setData(Object t){
        this.result.setData(t);
        this.result.setCode(200);
        return this.result;
    }

    public ResultVo setSuccessMsg(String msg){
        this.result.setSuccess(true);
        this.result.setMsg(msg);
        this.result.setCode(200);
        this.result.setData(null);
        return this.result;
    }

    public ResultVo setSuccessMsg(){
        this.result.setSuccess(true);
        this.result.setCode(200);
        this.result.setMsg("success");
        this.result.setData(null);
        return this.result;
    }

    public ResultVo setSuccessMsg(boolean isUs){
        this.result.setSuccess(true);
        String msg="success";
        this.result.setCode(200);
        if (isUs){
            this.result.setMsg("success");
//            try {
//                this.result.setMsg(TranslateUtil.handle(msg));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else {
            this.result.setMsg(msg);
        }
        this.result.setData(null);
        return this.result;
    }
    public ResultVo setData(Object t, String msg){
        this.result.setData(t);
        this.result.setCode(200);
        this.result.setMsg(msg);
        return this.result;
    }

    public ResultVo setErrorMsg(){
        this.result.setSuccess(false);
        this.result.setCode(500);
        this.result.setMsg("Operation fail");
        return this.result;
    }

    public ResultVo setErrorMsg(boolean isUs){
        this.result.setSuccess(false);
        String msg="Operation fail";
        this.result.setCode(500);
        if (isUs){
            this.result.setMsg("failed");
//            try {
//                this.result.setMsg(TranslateUtil.handle(msg));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else {
            this.result.setMsg(msg);
        }
        this.result.setData(null);
        return this.result;
    }
    public ResultVo setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMsg(msg);
        this.result.setCode(500);
        return this.result;
    }

    public ResultVo setLoginErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMsg(msg);
        this.result.setCode(401);
        return this.result;
    }

    public ResultVo setErrorMsg(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMsg(msg);
        this.result.setCode(code);
        return this.result;
    }
}
