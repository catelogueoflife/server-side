package com.touchkiss.catelogueoflife.kuaishou.services.impl;

import com.touchkiss.catelogueoflife.kuaishou.bean.KsRecVideo;
import com.touchkiss.catelogueoflife.kuaishou.mapper.KsRecVideoDaoM;
import com.touchkiss.catelogueoflife.kuaishou.services.KsRecVideoDaoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.List;

/**
 * Created on 2020-08-26 21:20:44
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class KsRecVideoDaoServiceImpl implements KsRecVideoDaoService {
	@Resource
	KsRecVideoDaoM ksRecVideoDaoM;

	@Override
	public boolean insert(KsRecVideo ksRecVideo) {
        long currentTimeMillis = System.currentTimeMillis();
        if (ksRecVideo.getCreateTime() == null) {
            ksRecVideo.setCreateTime(currentTimeMillis);
        }
        if (ksRecVideo.getLastModifyTime() == null) {
            ksRecVideo.setLastModifyTime(currentTimeMillis);
        }
        return ksRecVideoDaoM.insert(ksRecVideo) == 1;
	}

	@Override
	public boolean deleteByPhotoId(String photoId) {
        return ksRecVideoDaoM.deleteByPhotoId(photoId) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return ksRecVideoDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateByPhotoId(KsRecVideo ksRecVideo, String photoId) {
        long currentTimeMillis = System.currentTimeMillis();
        ksRecVideo.setLastModifyTime(currentTimeMillis);
        return ksRecVideoDaoM.updateByPhotoId(ksRecVideo, photoId) == 1;
    }

    @Override
    public boolean updateSelectiveByPhotoId(KsRecVideo ksRecVideo, String photoId) {
        long currentTimeMillis = System.currentTimeMillis();
        ksRecVideo.setLastModifyTime(currentTimeMillis);
        return ksRecVideoDaoM.updateSelectiveByPhotoId(ksRecVideo, photoId) == 1;
    }

    @Override
    public int updateSelectiveByMap(KsRecVideo ksRecVideo, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        ksRecVideo.setLastModifyTime(currentTimeMillis);
        return ksRecVideoDaoM.updateSelectiveByMap(ksRecVideo, wheres);
    }

    @Override
    public KsRecVideo selectByPhotoId(String photoId) {
        return ksRecVideoDaoM.selectByPhotoId(photoId);
    }

	@Override
	public List<KsRecVideo> queryKsRecVideoList(Map<String, String> map) {
		return ksRecVideoDaoM.queryKsRecVideoList(map);
	}

	@Override
	public Page<KsRecVideo> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return ksRecVideoDaoM.findByPage(map);
	}
}
