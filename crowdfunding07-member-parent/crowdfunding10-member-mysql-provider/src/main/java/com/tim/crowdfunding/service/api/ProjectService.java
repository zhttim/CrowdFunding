package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.vo.DetailProjectVO;
import com.tim.crowdfunding.entity.vo.PortalTypeVO;
import com.tim.crowdfunding.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
