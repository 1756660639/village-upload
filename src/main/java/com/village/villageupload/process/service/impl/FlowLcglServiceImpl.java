package com.village.villageupload.process.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.cache.UserCache;
import com.village.villageupload.common.UserUtil;
import com.village.villageupload.process.entity.FlowLcglEntity;
import com.village.villageupload.process.entity.FlowLcjdsxEntity;
import com.village.villageupload.process.entity.FlowLcsprEntity;
import com.village.villageupload.process.entity.FlowYfqlcglEntity;
import com.village.villageupload.process.mapper.FlowLcglMapper;
import com.village.villageupload.process.service.FlowLcglService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowLcglServiceImpl extends ServiceImpl<FlowLcglMapper, FlowLcglEntity> implements FlowLcglService {

    @Autowired
    private FlowLcglMapper flowLcglMapper;

    @Override
    public List<FlowLcglEntity> listAll(FlowLcglEntity resource) {
        return flowLcglMapper.listAll(resource);
    }

    @Override
    public PageResource<FlowLcglEntity> listAllPage(FlowLcglEntity resource) {
        Page<FlowLcglEntity> page = flowLcglMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public FlowLcglEntity insert(FlowLcglEntity resource) {
        List<Map<String,Object>> processNodeList = new ArrayList<>();
        resource.setId(UuidUtils.uuid(true));
        flowLcglMapper.insert(resource);
        this.insertProcessNode(resource.getFlowData(),resource.getId());
        return resource;
    }

    public void insertProcessNode(String processNode,String lcglId){
        flowLcglMapper.deleteByLcglId(lcglId);
        Map process = JSON.parseObject(processNode,Map.class);
        List<Map<String,Object>> processNodeList = new ArrayList<>();
        this.processNode((Map<String, Object>) process.get("process"),processNodeList,lcglId,false,"");
        flowLcglMapper.insertLcjdsx(processNodeList);
    }

    public void processNode(Map<String,Object> process,List<Map<String,Object>> processNodeList,String lcglId,boolean isbranch,String toNext){
        Map<String,Object> children = new HashMap<>();
        Map<String,Object> props = new HashMap<>();
        if(ObjectUtil.isNotEmpty(process.get("children"))){
            children = JSON.parseObject(process.get("children").toString(),Map.class);
        }
        if(ObjectUtil.isNotEmpty(process.get("props"))){
            props = JSON.parseObject(process.get("props").toString(),Map.class);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",UuidUtils.uuid(true));
        map.put("name",process.get("name"));
        map.put("lcglId",lcglId);
        map.put("type",process.get("type"));
        map.put("number",process.get("id"));
        map.put("tjfznrSql",props.get("expression"));
        // ???????????????
        if(ObjectUtil.isNotEmpty(children)){
            // ????????????????????? ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if("CONDITIONS".equals(children.get("type"))){
                List<Map<String,Object>> childrenList = (List)children.get("branchs");
                String to ="";
                Map<String,Object> childrenEmpty = JSON.parseObject(children.get("children").toString(),Map.class);
                for(int i = 0;i<childrenList.size();i++){
                    this.processNode(childrenList.get(i),processNodeList,lcglId,true,childrenEmpty.get("id").toString());
                    to += childrenList.get(i).get("id")+",";
                    // ???????????????????????????????????????????????????
                }
                this.processNode(childrenEmpty,processNodeList,lcglId,isbranch,toNext);
                map.put("to",to.substring(0,to.length()-1));
            }else {
                this.processNode(children,processNodeList,lcglId,isbranch,toNext);
                map.put("to",children.get("id"));
            }
        }else if(isbranch){
            // ?????????????????????????????????????????????????????????????????????id
            map.put("to",toNext);
            isbranch = false;
            toNext = "";
        }
        processNodeList.add(map);
    }

    @Override
    public FlowLcglEntity update(FlowLcglEntity resource){
        flowLcglMapper.updateById(resource);
        this.insertProcessNode(resource.getFlowData(),resource.getId());
        return resource;
    }

    @Override
    public void updateProcessData(FlowLcglEntity resource) {
        flowLcglMapper.updateProcessData(resource.getId(),resource.getFlowData());
        this.insertProcessNode(resource.getFlowData(),resource.getId());
    }

    @Override
    public int deleteById(String id) {
        return flowLcglMapper.deleteById(id);
    }

    @Override
    public FlowLcglEntity findById(String id) {
        return flowLcglMapper.selectById(id);
    }

    @Override
    public List<FlowLcsprEntity> getApproveNode(String lcid,String dataId) {
        List<FlowLcsprEntity> list = new ArrayList<>();
        List<FlowLcjdsxEntity> approveList = flowLcglMapper.getApproveNode(lcid);
        approveNodeListOrder(approveList,list,dataId);
        return list;
    }

    public void approveNodeListOrder(List<FlowLcjdsxEntity> approveList, List<FlowLcsprEntity> list,String dataId){
        String currentId = "";
        for(FlowLcjdsxEntity flowLcjdsxEntity : approveList){
            if(flowLcjdsxEntity.getType().equals("ROOT")){
                currentId = flowLcjdsxEntity.getTo();
            }
        }
        for (int i = 0; i < approveList.size(); i++) {
            // currentId???null ??????????????????????????????????????????
            if(ObjectUtil.isEmpty(currentId)){
                break;
            }
            String[] currentIds = currentId.split(",");
            // ?????????????????????????????????????????????????????????????????????????????????
            for(String ids:currentIds){
                currentId = approveNodePush(approveList,list,ids,dataId);
                // ??????currentId?????????ids????????????????????????????????????????????????currentId ??????????????????id
                if(ObjectUtil.isEmpty(currentId) || currentId.indexOf(ids)==-1) break;
            }
        }
    }

    public String approveNodePush(List<FlowLcjdsxEntity> approveList, List<FlowLcsprEntity> list,String currentId,String dataId){
        for (FlowLcjdsxEntity flowLcjdsxEntity : approveList) {
            // ??????????????????????????????????????????
            if(flowLcjdsxEntity.getNumber().equals(currentId)){
                // ?????????????????????????????????????????????????????????
                if("APPROVAL".equals(flowLcjdsxEntity.getType()) || "CC".equals(flowLcjdsxEntity.getType())){
                    FlowLcsprEntity flowLcsprEntity = new FlowLcsprEntity();
                    flowLcsprEntity.setNodeName(flowLcjdsxEntity.getName());
                    flowLcsprEntity.setSort(list.size()+1);
                    list.add(flowLcsprEntity);
                    currentId = flowLcjdsxEntity.getTo();
                } else if("CONDITION".equals(flowLcjdsxEntity.getType())){// ???????????????????????????????????????????????????????????????????????????currentId?????????????????????to??? ???????????????
                    if(conditionSelect(flowLcjdsxEntity.getTjfznrSql(),dataId)){
                        currentId = flowLcjdsxEntity.getTo();
                    }
                }else { // ????????????????????????????????????nodeid
                    currentId = flowLcjdsxEntity.getTo();
                }
            }
        }
        return currentId;
    }

    public boolean conditionSelect(String sql,String dataId){
        sql = sql.replaceAll("\\{dataId}",dataId);
        int count = flowLcglMapper.conditionSelect(sql);
        return count > 0 ? true:false;
    }

    @Override
    public void saveApproveUser(FlowYfqlcglEntity resource) {
        String yfqlcId = UuidUtils.uuid(true);
        resource.setId(yfqlcId);
        resource.setCreateUser(UserUtil.getUserId());
        resource.setUpdateUser(UserUtil.getUserId());
        // ???????????????????????????
        flowLcglMapper.savaYfqlc(resource);
        // ???????????????????????????????????????
        List<FlowLcsprEntity> flowLcsprEntities = new ArrayList<>();
        lcsprList(resource.getFlowLcsprEntities(),flowLcsprEntities,yfqlcId);
        flowLcglMapper.saveLcspr(flowLcsprEntities);
    }

    public void lcsprList(List<FlowLcsprEntity> list,List<FlowLcsprEntity> flowLcsprEntities,String yfqlcId){
        for (FlowLcsprEntity flowLcsprEntity:list){
            // state 1????????? 2???????????? 3?????????
            if(flowLcsprEntity.getSort() == 1){
                flowLcsprEntity.setState("1");
                flowLcsprEntity.setStartDate(new Timestamp(System.currentTimeMillis()));
            }else {
                flowLcsprEntity.setState("2");
            }
            // ?????????????????????????????????
            if(flowLcsprEntity.getApproveId().split(",").length>1){
                String[] approveId = flowLcsprEntity.getApproveId().split(",");
                String[] approveName = flowLcsprEntity.getApproveName().split(",");
                String[] approveDept = flowLcsprEntity.getApproveDept().split(",");
                for (int i = 0; i < approveId.length; i++) {
                    FlowLcsprEntity flowLcsprEntity1 = flowLcsprEntity;
                    flowLcsprEntity1.setId(UuidUtils.uuid(true));
                    flowLcsprEntity1.setYfqlcId(yfqlcId);
                    flowLcsprEntity1.setApproveId(approveId[i]);
                    flowLcsprEntity1.setApproveName(approveName[i]);
                    flowLcsprEntity1.setApproveDept(approveDept[i]);
                    flowLcsprEntities.add(flowLcsprEntity1);
                }
            }else{ // ?????????????????????????????????
                flowLcsprEntity.setId(UuidUtils.uuid(true));
                flowLcsprEntity.setYfqlcId(yfqlcId);
                flowLcsprEntities.add(flowLcsprEntity);
            }
        }
    }

    @Override
    public List<FlowLcsprEntity> getApproveNodeDetail(String lcid, String dataId) {
        return flowLcglMapper.getApproveNodeDetail(dataId);
    }

    @Override
    public List<Map<String, Object>> getApprovalPending() {
        return flowLcglMapper.getApprovalPending(UserUtil.getUserId());
    }
}
