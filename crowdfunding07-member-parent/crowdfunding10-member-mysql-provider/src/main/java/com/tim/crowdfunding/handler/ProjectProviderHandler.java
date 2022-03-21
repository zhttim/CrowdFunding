package com.tim.crowdfunding.handler;

import com.tim.crowdfunding.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectProviderHandler {
    @Autowired
    private ProjectService projectService;
}
