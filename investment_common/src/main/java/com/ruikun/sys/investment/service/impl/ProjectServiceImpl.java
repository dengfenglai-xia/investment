package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private WorkplaceMapper workPlaceMapper;
    @Autowired
    private CarplaceMapper carPlaceMapper;

    /**
     * @Description: 查询项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Project> queryProjectList(Project project) {
        String companyCode = project.getCompanyCode();
        List<Project> projectList = projectMapper.queryProjectList(project);

        Building building = new Building();
        building.setCompanyCode(companyCode);
        List<Building> buildingList = buildingMapper.queryBuildingList(building);

        Room room = new Room();
        room.setCompanyCode(companyCode);
        List<Room> roomList = roomMapper.queryRoomList(room);

        Workplace workplace = new Workplace();
        workplace.setCompanyCode(companyCode);
        List<Workplace> workplaceList = workPlaceMapper.queryWorkplaceList(workplace);

        Carplace carplace = new Carplace();
        carplace.setCompanyCode(companyCode);
        List<Carplace> carplaceList = carPlaceMapper.queryCarplaceList(carplace);

        for (Project p : projectList) {
            int buildingNum = 0;
            int roomNum = 0;
            double totalArea = 0d;
            int workplaceNum = 0;
            int carplaceNum = 0;
            Integer projectId = p.getProjectId();
            for (Building b : buildingList) {
                if (projectId.equals(b.getProjectId())) {
                    buildingNum++;
                }
            }
            for (Room r : roomList) {
                if (projectId.equals(r.getProjectId())) {
                    roomNum++;
                    totalArea += r.getBuildArea();
                }
            }
            for (Workplace w : workplaceList) {
                if (projectId.equals(w.getProjectId())) {
                    workplaceNum++;
                }
            }
            for (Carplace c : carplaceList) {
                if (projectId.equals(c.getProjectId())) {
                    carplaceNum++;
                }
            }
            p.setBuildingNum(buildingNum);
            p.setRoomNum(roomNum);
            p.setTotalArea(totalArea);
            p.setWorkplaceNum(workplaceNum);
            p.setCarplaceNum(carplaceNum);
        }
        return projectList;
    }

    /**
     * @Description: 查询项目基础信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public List<Project> queryProjectBaseList(Project project) {
        return projectMapper.queryProjectBaseList(project);
    }

    /**
     * @Description: 通过主键查询项目表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Project queryProjectDetailByPrimarykey(Integer projectId) {
        return projectMapper.queryProjectDetailByPrimarykey(projectId);
    }

    /**
     * @Description: 查询项目表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Project queryProjectDetail(Project project) {
        return projectMapper.queryProjectDetail(project);
    }

    /**
     * @Description: 新增项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertProject(Project project) {
        Date date = new Date(); // 当前时间
        project.setCreateTime(date); // 创建时间
        project.setUpdateTime(date); // 更新时间
        projectMapper.insertProject(project);
    }

    /**
     * @Description: 修改项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateProject(Project project) {
        project.setUpdateTime(new Date());
        projectMapper.updateProject(project);
    }

    /**
     * @Description: 删除项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteProjectByPrimarykey(Integer projectId) {
        projectMapper.deleteProjectByPrimarykey(projectId);
    }

}
