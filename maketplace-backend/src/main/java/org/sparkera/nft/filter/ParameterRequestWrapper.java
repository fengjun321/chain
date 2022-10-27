package org.sparkera.nft.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map params = new HashMap<>();
    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }

    public ParameterRequestWrapper(HttpServletRequest request, Map extendParams) {
        this(request);
        //这里将扩展参数写入参数表
        addAllParameters(extendParams);
    }

    @Override
    public Enumeration getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    @Override
    public String getParameter(String name) {
        String[] values = (String[]) params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }

        return values[0];

    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = (String[]) params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;

    }

    public void addAllParameters(Map otherParams) {
//        for (Map.Entry entry : otherParams.entrySet()) {
//            addParameter((String) entry.getKey(), entry.getValue());
//        }

    }

    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }

        }

    }
}
