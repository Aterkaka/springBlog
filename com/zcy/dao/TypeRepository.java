package com.zcy.dao;

import com.zcy.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-02-24 00:07
 */

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    Type getTypeById(Long id);

    @Query("select  t from Type t")
    List<Type> findTop(Pageable pageable);
}
