package cn.gilight.personal.teacher.research.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.personal.teacher.research.dao.TeacherResearchDao;
import cn.gilight.personal.teacher.research.service.TeacherResearchService;

@Service("teacherResearchService")
public class TeacherResearchServiceImpl implements TeacherResearchService{

	@Autowired
	private TeacherResearchDao teacherResearchDao;

	@Override
	public Map<String, Object> getResearchCounts(String tea_id) {
		Map<String,Object> map = new HashMap<String,Object>();
		int projectCounts = teacherResearchDao.getProjectCounts(tea_id);
		int thesisCounts = teacherResearchDao.getThesisCounts(tea_id);
		int workCounts = teacherResearchDao.getWorkCounts(tea_id);
		int patentCounts = teacherResearchDao.getPatentCounts(tea_id);
		int outcomeAwardCounts = teacherResearchDao.getOutcomeAwardCounts(tea_id);
		int outcomeAppraisalCounts = teacherResearchDao.getOutcomeAppraisalCount(tea_id);
		int outcomeCounts = outcomeAwardCounts + outcomeAppraisalCounts;
		int softCounts = teacherResearchDao.getSoftCounts(tea_id);
		
		map.put("projectCounts", projectCounts);
		map.put("thesisCounts", thesisCounts);
		map.put("workCounts", workCounts);
		map.put("patentCounts", patentCounts);
		map.put("outcomeCounts", outcomeCounts);
		map.put("softCounts", softCounts);
		return map;
	}

	@Override
	public Map<String, Object> getThesisCounts(String tea_id) {
		int thesisCounts = teacherResearchDao.getThesisCounts(tea_id);
		int awardThesisCounts = teacherResearchDao.getAwardThesisCounts(tea_id);
		int inThesisCounts = teacherResearchDao.getInThesisCounts(tea_id);
		int meetingThesisCounts = teacherResearchDao.getMeetingThesisCounts(tea_id);
		int reshipThesisCounts = teacherResearchDao.getReshipThesisCounts(tea_id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("thesisCounts", thesisCounts);
		map.put("awardThesisCounts", awardThesisCounts);
		map.put("inThesisCounts", inThesisCounts);
		map.put("meetingThesisCounts", meetingThesisCounts);
		map.put("reshipThesisCounts", reshipThesisCounts);
		return map;
	}

	@Override
	public List<Map<String, Object>> getThesis(String tea_id,String flag) {
		List<Map<String,Object>> list = null;
		if("publisher".equals(flag)){
			list = teacherResearchDao.getThesises(tea_id);
			for(Map<String,Object> map : list){
				String authors = MapUtils.getString(map, "AUTHORS");
				String author = MapUtils.getString(map, "FIRST_AUTHOR");
				String[] as = authors.split(",");
		        List<String> arrayList = new ArrayList<String>();  
		        for(int i=0;i<as.length;i++){
		        	if(!as[i].equals(author)){
		        		arrayList.add(as[i]);
		        	}
		        }
		        String otherAuthors ="";
		        if(arrayList != null && arrayList.size()>0){
			        otherAuthors = arrayList.get(0);
			        for(int i=1;i<arrayList.size();i++){
			        	otherAuthors = otherAuthors + ","+arrayList.get(i);
			        }
		        }
		        map.remove("AUTHORS");
		        map.put("OTHERAUTHORS", otherAuthors);
			}
		}else if("award".equals(flag)){
			list = teacherResearchDao.getAwardThesis(tea_id);
		}else if("in".equals(flag)){
			list = teacherResearchDao.getInThesis(tea_id);
		}else if("meeting".equals(flag)){
			list = teacherResearchDao.getMeetingThesis(tea_id);
		}else if("reship".equals(flag)){
			list = teacherResearchDao.getReshipThesis(tea_id);
		}
		return list;
	}

	@Override
	public Map<String, Object> getProjectCounts(String tea_id) {
		
		int projectCounts = teacherResearchDao.getProjectCounts(tea_id);
		int nationalProject = teacherResearchDao.getNationalProject(tea_id);
		int inquestsProject = teacherResearchDao.getInquestsProject(tea_id);
		int leaderProject = teacherResearchDao.getLeaderProject(tea_id);
 		Map<String,Object> map = new HashMap<String,Object>();
		map.put("projectCounts", projectCounts);
		map.put("nationalProject", nationalProject);
		map.put("inquestsProject", inquestsProject);
		map.put("leaderProject", leaderProject);
		
		return map;
	}

	@Override
	public List<Map<String, Object>> getProjects(String tea_id,String flag) {
		return teacherResearchDao.getProjects(tea_id,flag);
	}

	@Override
	public List<Map<String, Object>> getWorks(String tea_id,String flag) {
		return teacherResearchDao.getWorks(tea_id,flag);
	}

	@Override
	public Map<String, Object> getPatentCounts(String tea_id) {
		int acceptPatent = teacherResearchDao.getAcceptPatent(tea_id);
		int accreditPatent = teacherResearchDao.getAccreditPatent(tea_id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("acceptPatent", acceptPatent);
		map.put("accreditPatent", accreditPatent);
		return map;
	}

	@Override
	public List<Map<String, Object>> getPatents(String tea_id,String flag) {
		return teacherResearchDao.getPatents(tea_id,flag);
	}

	@Override
	public Map<String, Object> getOutcomeCounts(String tea_id) {
		int appraisaloutcome = teacherResearchDao.getOutcomeAppraisalCount(tea_id);
		int awardoutcome = teacherResearchDao.getOutcomeAwardCounts(tea_id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appraisaloutcome", appraisaloutcome);
		map.put("awardoutcome", awardoutcome);
		return map;
	}

	@Override
	public List<Map<String, Object>> getOutcomes(String tea_id,String flag) {
		List<Map<String,Object>> list = null;
		if("appraisal".equals(flag)){
			list = teacherResearchDao.getAppraisalOutcomes(tea_id);
		}else if("award".equals(flag)){
			list = teacherResearchDao.getAwardOutcomes(tea_id);
		}
		return list;
	}

	
	@Override
	public Map<String, Object> getWorksCounts(String tea_id) {
		int chief_editor_counts = teacherResearchDao.getChiefEditor(tea_id);
		int partake_counts = teacherResearchDao.getPartake(tea_id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("chief_editor", chief_editor_counts);
		map.put("partake", partake_counts);
		return map;
	}

	@Override
	public List<Map<String, Object>> getSofts(String tea_id) {
		return teacherResearchDao.getSofts(tea_id);
	}
	
	

}
