package com.holyliao;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.holyliao.visitor.SelectVisitorImpl;

/**
 * @author liaoqixing
 * @description 数据权限拦截器
 * @create 2018-04-24 下午3:12
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        }
)
public class DataPermissionInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(DataPermissionInterceptor.class);
    public static final String DATA_PERMISSION = "_DATA_PERMISSION";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        //从当前线程获取需要进行数据权限控制的业务
        DataPermission dataPermission = DPHelper.getLocalDataPermissions();
        //判断有没有进行数据权限控制，是不是最高权限的管理员（这里指的是数据权限的白名单用户）
        if (dataPermission != null && dataPermission.getAdmin() == false) {
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();
            //获得方法类型
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            select.getSelectBody().accept(new SelectVisitorImpl());
            //判断当前sql是否被修改
            if (DPHelper.getChangeTable()) {
                //访问各个visitor
                //TODO:解析动态sql会失败
                BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), select.toString(), boundSql
                        .getParameterMappings(), parameter);
                String newMsId = mappedStatement.getId() + DATA_PERMISSION;
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql), newMsId);
                args[0] = newMs;
                DPHelper.clearChangeTable();
            }
        }
        return invocation.proceed();
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, String newMsId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newMsId, newSqlSource,
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        // setStatementTimeout()
        builder.timeout(ms.getTimeout());
        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());
        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
