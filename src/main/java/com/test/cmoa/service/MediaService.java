package com.test.cmoa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.cmoa.dao.i.MediaMapper;
import com.test.cmoa.entity.Media;

@Service
public class MediaService {
	@Autowired
	private MediaMapper mediaMapper;
	
	public void savemedia(Media m) {
		mediaMapper.saveMedia(m);
	}

	public List<Media> selectAll() {
		
		return mediaMapper.selectAll();
	}
	public List<Media> selectAllUnConvertUI() {
		
		return mediaMapper.selectAllUnConvertUI();
	}
	//修改转码状态
	public void updateConvertByEntity(Media m) {
		mediaMapper.updateConvertByEntity(m);
	}
	//根据id查Media
	public Media selectMediaById(Integer id) {
		return mediaMapper.selectMediaById(id);
	}

	public void updateConvertAreaById(Integer id) {
		mediaMapper.updateConvertAreaById(id);
	}

	public List<Media> selectUnConvertList() {
		List<Media> unConvertList= mediaMapper.selectUnConvertList();
		return unConvertList;
	}

}
