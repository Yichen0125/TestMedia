package com.test.cmoa.dao.i;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.test.cmoa.entity.Media;
@Repository
public interface MediaMapper {

	void saveMedia(Media m);
	
	List<Media> selectAll();

	List<Media> selectAllUnConvertUI();

	void updateConvertByEntity(Media m);

	Media selectMediaById(Integer id);

	void updateConvertAreaById(Integer id);

	List<Media> selectUnConvertList();

}
