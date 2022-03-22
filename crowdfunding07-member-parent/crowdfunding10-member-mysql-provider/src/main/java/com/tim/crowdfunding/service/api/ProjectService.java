package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.vo.ProjectVO;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
}
