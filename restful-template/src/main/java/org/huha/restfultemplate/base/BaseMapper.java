package org.huha.restfultemplate.base;

import java.util.List;

/**
 * @author: WangKun
 * @date: 2020/10/24
 */
public interface BaseMapper<T> {

    public List<T> list();

    public T single();

    public void delete(Integer id);

    public void add(T t);

    public void update(T t);
}
