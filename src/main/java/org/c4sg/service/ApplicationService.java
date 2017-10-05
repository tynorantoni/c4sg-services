package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.ApplicantDTO;
import org.c4sg.dto.ApplicationDTO;
import org.c4sg.dto.ProjectDTO;


public interface ApplicationService {
	
	/*ApplicationDTO getApplicationByProjectandByUser(Integer userId, Integer projectId);
	ApplicationDTO getApplicationsByProjectAndByUser(Integer userId, Integer projectId, String status);
	List<ApplicationDTO> getApplicationsByUser(Integer userId);		
	List<ApplicationDTO> getApplicationsByProject(Integer projectId);
	List<ApplicationDTO> getApplicationsByProject(Integer projectId, String status);*/
	
	List<ProjectDTO> getApplicationsByUser(Integer userId, String status);
	List<ApplicantDTO> getApplicants(Integer projectId);
	
	ApplicationDTO createApplication(ApplicationDTO application);
	ApplicationDTO updateApplication(ApplicationDTO application);
	Long deleteApplication(Integer id);

}
