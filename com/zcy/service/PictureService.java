package com.zcy.service;

import com.zcy.po.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-03-18
 */

public interface PictureService {

    List<Picture> listPicture();

    Picture savePicture(Picture picture);

    Picture getPicture(Long id);

    Page<Picture> listPicture(Pageable pageable);

    Picture updatePicture(Long id, Picture picture);

    void deletePicture(Long id);
}
