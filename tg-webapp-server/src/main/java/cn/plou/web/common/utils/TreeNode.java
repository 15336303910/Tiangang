package cn.plou.web.common.utils;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.system.baseMessage.commuity.service.CommuityService;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.station.entity.Station;
import cn.plou.web.system.baseMessage.station.info.StationInfo;
import cn.plou.web.system.baseMessage.station.service.StationService;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode<T> implements Serializable {
    private static final long serialVersionUID = -2585299218583453327L;
    @Autowired
    private StationService stationService;
    @Autowired
    private CommuityService commuityService;


    public String companyId;
    public String superCompanyId;
    private String companyName;//树节点显示文本
    private List<StationInfo> stationList;
    private T nodeData;
    /**
     * 子节点，如果没有子节点，则列表长度为0
     */
    private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getSuperCompanyId() {
        return superCompanyId;
    }

    public void setSuperCompanyId(String superCompanyId) {
        this.superCompanyId = superCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<StationInfo> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationInfo> stationList) {
        this.stationList = stationList;
    }

    public T getNodeData() {
        return nodeData;
    }

    public void setNodeData(T nodeData) {
        this.nodeData = nodeData;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }


    /**
     * 把树节点列表构造成树，最后返回树的根节点，如果传入的列表有多个根节点，会动态创建一个根节点。
     *
     * @param nodes 树节点列表
     * @return 根节点
     */


    public static <T> TreeNode<T> buildTree(List<TreeNode<T>> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return null;
        }

        if(nodes.size() == 1){
            TreeNode<T> root = new TreeNode<>();
            root.setCompanyName("全部企业");
            root.getChildren().add(nodes.get(0));
            return root;
        }

        //用来存放nodes里面的顶级树节点
        //也就是把没有父节点的节点都放到tops里面去
        List<TreeNode<T>> tops = new ArrayList<TreeNode<T>>();

        boolean hasParent = false;
        //第一次遍历，获取一个节点作为子节点
        for (TreeNode<T> child : nodes) {
            hasParent = false;

            //当前节点child的父节点id
            String superCompanyId = child.getSuperCompanyId();

            //如果pid不存在或为空
            //则当前节点为顶级节点
            if (superCompanyId == "无"|| superCompanyId==null) {
                //把当前节点添加到tops中作为顶级节点
                tops.add(child);
                //跳过当前节点，进入下一轮
                continue;
            }

            //遍历nodes上的所有节点，判断是否有child的父节点
            for (TreeNode<T> parent : nodes) {
                String id = parent.getCompanyId();
                //如果parent节点的id等于child节点的pid，则parent节点是child节点的父节点
                if (id != null && id.equals(superCompanyId)) {
                    //把child加到parent下
                    parent.getChildren().add(child);
                    //child节点有父节点
                    hasParent = true;

                    continue;
                }
            }
            //如果child节点没有父节点，则child是顶级节点
            //把child添加到tops中
            if (!hasParent) {
                tops.add(child);
            }
        }
        TreeNode<T> root = null;
        if(tops.size() == 1){
            //如果顶级节点只有一个，该顶级节点是根节点
            root = new TreeNode<>();
            root.setCompanyName("全部企业");
            root.getChildren().addAll(tops);
            //root = tops.get(0);
        }/*else {
            //创建一个根节点，把顶级节点放到根节点下
            root = new TreeNode<>();
            root.setCompanyId(nodes.get(0).getCompanyId());
            root.setCompanyName(nodes.get(0).getCompanyName());
            root.setSuperCompanyId(null);
            root.getChildren().addAll(tops);
        }*/
        else{
            root = new TreeNode<>();
            root.setCompanyName("全部企业");
            root.getChildren().addAll(tops);
        }
        return root;
    }

    //根据企业树生成企业ID列表
    public static <T> List<String> getAll(TreeNode<T> root){
        List<String> list = new ArrayList<>();
        Deque<TreeNode<T>> stack = new ArrayDeque<>();
        stack.addLast(root);
        TreeNode<T> temp;
        while ((temp = stack.pollFirst()) != null) {
            List<TreeNode<T>> children = temp.getChildren();
            if (children != null && !children.isEmpty()) {
                for (TreeNode<T> aChildren : children) {
                    stack.addLast(aChildren);
                }
            }
            list.add(temp.getCompanyId());
        }
        return list;
    }

   //根据企业树生成列表
   public static <T> List<Company> findDownList(TreeNode<T> root){
        List<Company> list = new ArrayList<>();
        Deque<TreeNode<T>> stack = new ArrayDeque<>();
        stack.addLast(root);
        TreeNode<T> temp;
        while ((temp = stack.pollFirst()) != null) {
            List<TreeNode<T>> children = temp.getChildren();
            if (children != null && !children.isEmpty()) {
                for (TreeNode<T> aChildren : children) {
                    stack.addLast(aChildren);
                }
            }
            Company company = new Company();
            company.setCompanyId(temp.getCompanyId());
            company.setCompanyName(temp.getCompanyName());
            list.add(company);
        }
        return list;
    }

    //根据企业树生成站列表
    public static <T> List<StationInfo> findStationList(TreeNode<T> root){
        List<StationInfo> list = new ArrayList<>();
        Deque<TreeNode<T>> stack = new ArrayDeque<>();
        stack.addLast(root);
        TreeNode<T> temp;
        while ((temp = stack.pollFirst()) != null) {
            List<TreeNode<T>> children = temp.getChildren();
            if (children.isEmpty()&&temp.getStationList().size()!=0) {
                list.addAll(temp.getStationList());
            }
        }
        return list;
    }

    //生成当前企业树
   public static <T> TreeNode<T> getRootTree(TreeNode<T> nodes , String entId){
        Deque<TreeNode<T>> stack = new ArrayDeque<>();
        stack.addLast(nodes);
        TreeNode<T> temp = stack.pollFirst();
        while (temp != null && temp.getCompanyId().equals(entId)==false) {
            List<TreeNode<T>> children = temp.getChildren();
            if (children != null && !children.isEmpty()) {
                for (TreeNode<T> aChildren : children) {
                    stack.addLast(aChildren);
                }
            }
            temp = stack.pollFirst();
        }
        if(temp == null ||temp.getCompanyId().equals(entId)==false){
            throw new BusinessException("没有找到对应的企业ID");
        }else {
            return temp;
        }
    }
}
