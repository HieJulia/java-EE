package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class BaseActionForm extends ActionForm {
    /**
     * ������ԭʼ�Ĵ��������������д�����Ϣ���뵽һ���б��Ȼ����뵽
     * Servlet ���������й�ҳ��ʹ��.
     * ���ִ��ķ�����ʹ�� Struts1.1 ֮��� commons-validator,�������������
     * ���ֹ��� Struts �Ĳο����鼮�ж��н��ܡ�
     *
     * �Ƿ�Ϊ���ô˷�����ͨ������ validate �����Ƶģ��磺
     * <action path="/shop/signon" type="org.springframework.samples.jpetstore.web.struts.SignonAction"
     * name="accountForm" scope="session" validate="false">
     * �ǲ�����õģ���Ϊ validate="false".
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errorMessages = null;
        
        // ����ϵͳ�Ĵ�����Ϣ�б�ͨ������ doValidate(mapping, request, errorList);
        // addErrorIfStringEmpty �Ὣ������Ϣ���뵽�б��У����������������������Ե���.
        ArrayList errorList = new ArrayList();
        doValidate(mapping, request, errorList);
        request.setAttribute("errors", errorList);
        if (!errorList.isEmpty()) {
            errorMessages = new org.apache.struts.action.ActionErrors();
            errorMessages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("global.error"));
        }
        return errorMessages;
    }

    // validate action erros : mapping m reque s
    // 
    
    /**
     * �˷��������Ϊ�����า�ǵ�(overriding).
     * �κ�����ʵ����������������Զ�������� validate �������á�
     * ��Ҳ�����ģʽ��һ�֣������ṩ��ģ�壬���ཫ��ʵ�֡�
     * @param mapping
     * @param request
     * @param errors
     */
    public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
    }
    
    /**
     * �˸�������������������ҳ�����������Ƿ�Ϊ�գ�����ǿյĻ�������ʾ�����ĳ�����Ϣ��
     *
     * @param errors ������Ϣ�б�
     * @param message �� value Ϊ��ʱ������ʾ���������Ϣ
     * @param value ҳ��Ԫ�ض�Ӧ��ֵ
     */
    protected void addErrorIfStringEmpty(List errors, String message, String value) {
        if (value == null || value.trim().length() < 1) {
            errors.add(message);
        }
    }
}
