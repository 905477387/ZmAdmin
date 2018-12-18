package com.smallchill.common.base;

import com.smallchill.core.annotation.Json;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.ConstCacheKey;
import com.smallchill.core.constant.ConstCurd;
import com.smallchill.core.toolbox.ApiResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.log.BladeLogManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuangqian on 2017/6/22.
 */
public class BaseRestController extends BladeController implements ConstCurd, ConstCache, ConstCacheKey {

    @InitBinder
    protected void init(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Json
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ApiResult result = new ApiResult();
        String msg = ex.getMessage();
        Object resultModel = null;
        try {
            result.addFail(msg);
            resultModel = result;
            if(StrKit.notBlank(msg)){
                BladeLogManager.doLog("异常日志", msg, false);
            }
            return resultModel;
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return resultModel;
        } finally {
            LOGGER.error(msg, ex);
        }
    }

}
