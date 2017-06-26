package org.jinxd.dao;

import java.util.List;

import org.jinxd.annotation.MyBatisRepository;
import org.jinxd.entity.Winner;

@MyBatisRepository
public interface WinnerDao {
      Winner findWinnerById(int id);
      
      List<Winner> findByPage();
      
      void save(Winner winner);
      
      void update(Winner winner);
      
      void delete(int id);
      
}
