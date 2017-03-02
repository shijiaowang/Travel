package simpledao.cityoff.com.easydao;

import java.util.List;

/**
 * Created by wangyang on 2017/2/13.
 */

public interface IEasyDao<T> {
    long insert(List<T> datas);//插入数据集合
    long insert(T t);//插入一条数据
    List<T> queryAll(T t);//查询一条数据
    T query(T t);
    int delete(T t);//删除对象
    int update(T t);//修改数据
    void exec(String sql) throws Exception;//执行sql
}
