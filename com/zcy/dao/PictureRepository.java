package com.zcy.dao;

import com.zcy.po.Picture;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-03-18
 */

public interface PictureRepository extends JpaRepository<Picture,Long> {
    List<Picture> findAll(Sort sort);

    Picture getPictureById(Long id);

}
