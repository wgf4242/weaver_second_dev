// 节点后附加操作 外部接口， 接口来源的加号,
// 接口动作类文件 weaver.interfaces.workflow.action.demo.ActionDemo2 , 接口动作名称，标识随便填
// 参数设置, 添加 actionMaxNum 值为 2000
// 可以从路径设置导入 workflow.ActionDemo2.wewf , 在创建节点 - 节点后附加操作操作 添加上面的类名
package weaver.interfaces.workflow.action.demo;

import com.engine.integration.bean.DataSource;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.HashMap;

public class ActionDemo2 implements Action {
    /**
     * 接口附加操作配置中，常量
     * 注意点：
     * 1. 必须是String类型
     * 2. 必须有setter方法
     * 3. 这个action是单例的，多线程的情况，该属于是公用的
     * 多线程的，不要传requestId
     */
    private String actionMaxNum;

    // action中定义属性，接收参数值，参数值为数据值，则注入的为DataSource对象
//    private DataSource bbb = null;
    @Override
    public String execute(RequestInfo request) {
        // 操作类型
        String src = request.getRequestManager().getSrc(); // √退回时触发时： 提交退回都会触发

        // 提交时是 submit 退回时是 reject
        // 提交执行退回不执行
        if ("reject".equals(src)) {
            return "1";
        }

        // 金额报销最大值
        int maxNum = 1000;
        int maxNum2 = Util.getIntValue(actionMaxNum, 0);
        RequestManager requestManager = request.getRequestManager();
        MainTableInfo mainTableInfo = request.getMainTableInfo();

        int je = 0;

        // 取表单金额上的值
        HashMap<String, String> fieldValueMap = new HashMap<>();
        for (Property property : mainTableInfo.getProperty()) {
            if ("je".equals(property.getName())) {
                je = Util.getIntValue(property.getValue());
            }
        }

        if (je > maxNum) {
            request.getRequestManager().setMessagecontent("金额报销超过大最值: " + maxNum + "~请重新填写");
            return FAILURE_AND_CONTINUE; // 返回0时才会提示
        }


        return SUCCESS;
    }

    public void setActionMaxNum(String actionMaxNum) {
        this.actionMaxNum = actionMaxNum;
    }
}
