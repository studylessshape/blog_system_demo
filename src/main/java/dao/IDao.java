package dao;

import java.util.List;

public interface IDao<T> {
    List<T> find(String id);
    boolean addSql(T t);
    boolean updateSql(T t);
    boolean deleteSql(String id);
}
