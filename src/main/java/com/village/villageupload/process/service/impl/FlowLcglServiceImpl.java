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
        // 有子集情况
        if(ObjectUtil.isNotEmpty(children)){
            // 判断子集的类型 如果是审批人抄送人等则直接为当前分支的下一节点。如果为分支类则直接需要循环指定所有节点
            if("CONDITIONS".equals(children.get("type"))){
                List<Map<String,Object>> childrenList = (List)children.get("branchs");
                String to ="";
                Map<String,Object> childrenEmpty = JSON.parseObject(children.get("children").toString(),Map.class);
                for(int i = 0;i<childrenList.size();i++){
                    this.processNode(childrenList.get(i),processNodeList,lcglId,true,childrenEmpty.get("id").toString());
                    to += childrenList.get(i).get("id")+",";
                    // 分支结束后把当前子集链接在分支后面
                }
                this.processNode(childrenEmpty,processNodeList,lcglId,isbranch,toNext);
                map.put("to",to.substring(0,to.length()-1));
            }else {
                this.processNode(children,processNodeList,lcglId,isbranch,toNext);
                map.put("to",children.get("id"));
            }
        }else if(isbranch){
            // 分支最后一个节点链接到分支的开始节点的孩子节点id
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
            // currentId为null 则证明没有后续节点了直接跳出
            if(ObjectUtil.isEmpty(currentId)){
                break;
            }
            String[] currentIds = currentId.split(",");
            // 如果下一个节点存在多个，则循环遍历找出符合的下一个节点
            for(String ids:currentIds){
                currentId = approveNodePush(approveList,list,ids,dataId);
                // 如果currentId里包含ids则证明当前的不满足分支要求，否则currentId 为下个节点的id
                if(ObjectUtil.isEmpty(currentId) || currentId.indexOf(ids)==-1) break;
            }
        }
    }

    public String approveNodePush(List<FlowLcjdsxEntity> approveList, List<FlowLcsprEntity> list,String currentId,String dataId){
        for (FlowLcjdsxEntity flowLcjdsxEntity : approveList) {
            // 判断当前是否为要取的节点数据
            if(flowLcjdsxEntity.getNumber().equals(currentId)){
                // 如果是审批人和抄送人则直接加入审批列表
                if("APPROVAL".equals(flowLcjdsxEntity.getType()) || "CC".equals(flowLcjdsxEntity.getType())){
                    FlowLcsprEntity flowLcsprEntity = new FlowLcsprEntity();
                    flowLcsprEntity.setNodeName(flowLcjdsxEntity.getName());
                    flowLcsprEntity.setSort(list.size()+1);
                    list.add(flowLcsprEntity);
                    currentId = flowLcjdsxEntity.getTo();
                } else if("CONDITION".equals(flowLcjdsxEntity.getType())){// 如果是分支选择则判断是否满足分支条件，满足条件跟新currentId为当前分支的“to” 下一个节点
                    if(conditionSelect(flowLcjdsxEntity.getTjfznrSql(),dataId)){
                        currentId = flowLcjdsxEntity.getTo();
                    }
                }else { // 其他节点直接取下一节点的nodeid
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
        // 插入已发起流程数据
        flowLcglMapper.savaYfqlc(resource);
        // 插入发起流程数据中的审批人
        List<FlowLcsprEntity> flowLcsprEntities = new ArrayList<>();
        lcsprList(resource.getFlowLcsprEntities(),flowLcsprEntities,yfqlcId);
        flowLcglMapper.saveLcspr(flowLcsprEntities);
    }

    public void lcsprList(List<FlowLcsprEntity> list,List<FlowLcsprEntity> flowLcsprEntities,String yfqlcId){
        for (FlowLcsprEntity flowLcsprEntity:list){
            // state 1审批中 2未流转到 3已审批
            if(flowLcsprEntity.getSort() == 1){
                flowLcsprEntity.setState("1");
                flowLcsprEntity.setStartDate(new Timestamp(System.currentTimeMillis()));
            }else {
                flowLcsprEntity.setState("2");
            }
            // 如果节点存在多个审批人
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
            }else{ // 只有一个审批人直接加入
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
