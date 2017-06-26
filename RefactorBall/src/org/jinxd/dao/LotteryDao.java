
package org.jinxd.dao;

import java.util.List;
import java.util.Map;

import org.jinxd.annotation.MyBatisRepository;
import org.jinxd.entity.Lottery;
import org.jinxd.entity.Winner;
import org.jinxd.entity.Winner2;

@MyBatisRepository
public interface LotteryDao {
	 Winner2 findById(int id);
	 
	 
	 Winner findWinnerIdById(int id);
	 
	 Lottery findByMaxId();
     
     List<Lottery> findLotteryByPage(Map<String,Object> map);
     
     int findIdByReds(String reds);
     
     void save(Winner winner);
     
     //≈˙¡ø≤Â»Î
     void saves(List<Winner> list);
     
     void update(Winner winner);
     
     void downWincounts(String reds);
     
     void delWinner(int id);
     
     void upWincounts(String reds);
     
}
