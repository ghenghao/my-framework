package com.smart.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.utils.CollectionUtil;
import com.smart.utils.PropsUtil;

/**
 * 封装数据库相关操作
 *
 * @author huangyong
 * @since 1.0
 */
public class DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    
    private static final QueryRunner QUERY_RUNNER;
	
    /**
     * 定义一个局部线程变量（使每个线程都拥有自己的连接）
     */
    private static final ThreadLocal<Connection> connContainer;
    
    private static final BasicDataSource DATA_SOURCE;
    
	static{
		connContainer = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		
		Properties conf = PropsUtil.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password= conf.getProperty("jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
		
		
	}
	
	/**
	 * 获取数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		return DATA_SOURCE;
	}
    

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
    	// 先从 ThreadLocal 中获取 Connection
        Connection conn = connContainer.get();;
        if(conn == null){
        	try {
        		conn = DATA_SOURCE.getConnection();
        	} catch (SQLException e) {
	            logger.error("获取数据库连接出错！", e);
	            throw new RuntimeException(e);
        	} finally{
        		connContainer.set(conn);
        	}
        }
        return conn;
    }
    
    /**
     * 关闭数据库连接
     * 使用了数据源连接就去掉该方法
     */
/*    public static void closeConnection(){
    	Connection conn = connContainer.get();
    	if(conn != null){
    		try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close connection failer", e);
				throw new RuntimeException(e);
			} finally{
				connContainer.remove();
			}
    	}
    }*/

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("开启事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                connContainer.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                logger.error("提交事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                logger.error("回滚事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

//    /**
//     * 初始化 SQL 脚本
//     */
//    public static void initSQL(String sqlPath) {
//        try {
//            File sqlFile = new File(ClassUtil.getClassPath() + sqlPath);
//            List<String> sqlList = FileUtils.readLines(sqlFile);
//            for (String sql : sqlList) {
//                update(sql);
//            }
//        } catch (Exception e) {
//            logger.error("初始化 SQL 脚本出错！", e);
//            throw new RuntimeException(e);
//        }
//    }
//
    /**
     * 根据 SQL 语句查询 Entity
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
    	T entity;
    	try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch (Exception e) {
			logger.error("query entity failure!", e);
			throw new RuntimeException(e);
		} 
    	
        return entity;
    }

    /**
     * 根据 SQL 语句查询 Entity 列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
    	List<T> entityList;
    	try {
    		Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
		} catch (SQLException e) {
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		} 
    	
        return entityList;
    }
    
    /**
     * 
     * @param sql
     * @param paramas
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object...params){
    	List<Map<String, Object>> result;
    	try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
		} catch (Exception e) {
			logger.error("executeQuery failure!", e);
			throw new RuntimeException(e);
		} 
    	return result;
    }
//
//    /**
//     * 根据 SQL 语句查询 Entity 映射（Field Name => Field Value）
//     */
//    public static <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
//        return dataAccessor.queryEntityMap(entityClass, sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询 Array 格式的字段（单条记录）
//     */
//    public static Object[] queryArray(String sql, Object... params) {
//        return dataAccessor.queryArray(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询 Array 格式的字段列表（多条记录）
//     */
//    public static List<Object[]> queryArrayList(String sql, Object... params) {
//        return dataAccessor.queryArrayList(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询 Map 格式的字段（单条记录）
//     */
//    public static Map<String, Object> queryMap(String sql, Object... params) {
//        return dataAccessor.queryMap(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询 Map 格式的字段列表（多条记录）
//     */
//    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
//        return dataAccessor.queryMapList(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询指定字段（单条记录）
//     */
//    public static <T> T queryColumn(String sql, Object... params) {
//        return dataAccessor.queryColumn(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询指定字段列表（多条记录）
//     */
//    public static <T> List<T> queryColumnList(String sql, Object... params) {
//        return dataAccessor.queryColumnList(sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询指定字段映射（多条记录）
//     */
//    public static <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
//        return dataAccessor.queryColumnMap(column, sql, params);
//    }
//
//    /**
//     * 根据 SQL 语句查询记录条数
//     */
//    public static long queryCount(String sql, Object... params) {
//        return dataAccessor.queryCount(sql, params);
//    }
//
    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int executeUpdate(String sql, Object... params) {
    	int rows = -1;
		try {
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			logger.error("update entity failure! ", e);
			throw new RuntimeException(e);
		} 
    	
        return rows;
    }
    
    /**
     * 根据executeUpdate,封装insertEntity
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){
    	if(CollectionUtil.isEmpty(fieldMap)){
    		String errMsg = "can not insert entity: fieldMap is empty!";
    		logger.error(errMsg);
    		throw new RuntimeException(errMsg);
    	}
    	
    	String sql = "INSERT INTO " + getTableName(entityClass);
    	StringBuilder columns = new StringBuilder("(");
    	StringBuilder values = new StringBuilder("(");
    	for(String fieldName : fieldMap.keySet()){
    		columns.append(fieldName).append(", ");
    		values.append("?, ");
    	}
    	columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
    	values.replace(values.lastIndexOf(", "), values.length(), ")");
    	sql += columns + "VALUES" + values;
    	
    	Object[] params = fieldMap.values().toArray();
    	return executeUpdate(sql, params) == 1;
    }
    
    
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
    	if(CollectionUtil.isEmpty(fieldMap)){
    		String errMsg = "can not update entity: fieldMap is empty!";
    		logger.error(errMsg);
    		throw new RuntimeException(errMsg);
    	}
    	
    	String sql = "UPDATE " + getTableName(entityClass) + " SET ";
    	StringBuilder columns = new StringBuilder();
    	for(String fieldName : fieldMap.keySet()){
    		columns.append(fieldName).append("=?, ");
    	}
    	sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";
    	
    	List<Object> paramList = new ArrayList<Object>();
    	paramList.addAll(fieldMap.values());
    	paramList.add(id);
    	Object[] params = paramList.toArray();
    	
    	return executeUpdate(sql, params) == 1;
    }
    
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
    	String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
    	return executeUpdate(sql, id) == 1;
    }
    
    private static String getTableName(Class<?> entityClass){
    	return entityClass.getSimpleName();
    }
    
    
    public static void executeSqlFile(String filePath) {
        InputStream is = null;
    	BufferedReader reader = null;
    	try {
    		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    		 reader = new BufferedReader(new InputStreamReader(is));
			String sql;
			while( (sql=reader.readLine()) != null ){
				executeUpdate(sql);
			}
		} catch (Exception e) {
			logger.error("execute sql file error!", e);
			throw new RuntimeException(e);
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("close reader stream failure", e);
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("close input stream failure", e);
				}
			}
			
		}
    }

//    /**
//     * 执行插入语句，返回插入后的主键
//     */
//    public static Serializable insertReturnPK(String sql, Object... params) {
//        return dataAccessor.insertReturnPK(sql, params);
//    }
}
