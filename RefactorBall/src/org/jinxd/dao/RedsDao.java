package org.jinxd.dao;

import org.jinxd.annotation.MyBatisRepository;
import org.jinxd.entity.Reds;

@MyBatisRepository
public interface RedsDao {

	Reds findById(int id);
      
     
}
