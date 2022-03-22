package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.po.MemberConfirmInfoPO;
import com.tim.crowdfunding.entity.po.MemberLaunchInfoPO;
import com.tim.crowdfunding.entity.po.ProjectPO;
import com.tim.crowdfunding.entity.po.ReturnPO;
import com.tim.crowdfunding.entity.vo.*;
import com.tim.crowdfunding.mapper.*;
import com.tim.crowdfunding.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        // 1.查询得到 DetailProjectVO 对象
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);

        // 2.根据 status 确定 statusText
        Integer status = detailProjectVO.getStatus();

        switch (status) {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }

        // 3.根据 deployeDate 计算 lastDay
        String deployDate = detailProjectVO.getDeployDate();

        // 获取当前日期
        Date currentDay = new Date();

        // 把众筹日期解析成 Date 类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = format.parse(deployDate);
            // 获取当前当前日期的时间戳
            long currentTimeStamp = currentDay.getTime();
            // 获取众筹日期的时间戳
            long deployTimeStamp = deployDay.getTime();
            // 两个时间戳相减计算当前已经过去的时间
            Integer pastDays = (int) (currentTimeStamp - deployTimeStamp) / 1000 / 60 / 60 / 24;
            // 获取总的众筹天数
            Integer totalDays = detailProjectVO.getDay();
            // 使用总的众筹天数减去已经过去的天数得到剩余天数
            Integer lastDay = totalDays - pastDays;
            detailProjectVO.setLastDay(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return detailProjectVO;
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 一、保存ProjectPO对象
        // 1.创建空的ProjectPO对象
        ProjectPO projectPO = new ProjectPO();

        // 2.把projectVO中的属性复制到projectPO中
        BeanUtils.copyProperties(projectVO, projectPO);
        projectPO.setMemberid(memberId);

        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createDate);
        projectPO.setStatus(0);
        // 3.保存projectPO
        projectPOMapper.insertSelective(projectPO);

        // 4.从projectPO对象中获取自增主键
        Integer projectId = projectPO.getId();
        // 二、保存项目、分类的关联信息
        // 1. 从projectVO中获取typeList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationShip(typeIdList, projectId);
        // 三、保存项目、标签的关联信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationShip(tagIdList, projectId);
        // 四、保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(detailPicturePathList, projectId);
        // 五、保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        // 六、保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

        List<ReturnPO> returnPOList = new ArrayList<>();

        for (ReturnVO returnVO : returnVOList) {

            ReturnPO returnPO = new ReturnPO();

            BeanUtils.copyProperties(returnVO, returnPO);

            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectId);
        // 七、保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }
}
