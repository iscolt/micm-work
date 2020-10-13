package icu.miners.micm.work.utils;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Map;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/13
 * @see: icu.miners.micm.work.utils
 * @version: v1.0.0
 */
public class JexlUtil {

    private static JexlEngine jexlEngine = new Engine();

    public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
        JexlExpression expression = jexlEngine.createExpression(jexlExpression);
        JexlContext context = new MapContext();
        map.forEach(context::set);
        return expression.evaluate(context);
    }
}
