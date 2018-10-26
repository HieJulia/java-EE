package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.LineItem;

import org.springframework.samples.jpetstore.domain.Order;

public class ViewOrderAction extends SecureBaseAction {
  
  protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    AccountActionForm acctForm = (AccountActionForm) form;
    long id = Integer.parseInt(request.getParameter("id")); // orderId
    
    // �ı� DAO �ӿڣ�
    //Ϊ��� order.getUser().getUsername() ����Ҫ����
    // �ֱ���ʹ�� open session in view ģʽ
    List list = getPetStore().getOrderAndUserName(id);
    Order order = null;
    String username = null;
    for (Iterator it = list.iterator(); it.hasNext();) {
      Object[] pair = (Object[]) it.next();
      order = (Order)pair[0];
      username = (String)pair[1];
    }

    // view order acton : secure base action 
    // exevute : form ; 
    // orm ; get or der user 
    
    // ��ʼ������Ĺ�ϵ LineItem -> Item (�費��Ҫ��ʼ������ Hibernate ���жϵ�
    // ���磺��������������̽����ҲҪ��ʾ ViewOrder.jsp, ����ʱ�ǲ���Ҫ��ʼ
    // �ģ���Ϊ��ʱ���е�ʵ�嶼��ʼ�������ˣ�ֻ����ֱ�Ӵ� EditAccountForm ��
    // �����ӡ�My Orders ʱ������Ҫ��ʾ�ĳ�ʼ����ϵ�� LineItem -> Item )
    // ��Ϊ LineItem �е� Item ������Ϊ update = false, ����û�� setItem() ������
    // ����������� ..., �Թ�ҳ�����
    List<Item> items = new ArrayList<Item>();
    List<String> productNames = new ArrayList<String>();
    
    List<LineItem> ls = order.getLineItems();
    for (LineItem li : ls) {
      // detached entiry, �� getPetStore().getItem(i) ���ٴι����� session
      Item i = li.getItem();
      Object[] pair = (Object[])getPetStore().getItem(i).get(0);
      Item item = (Item)pair[0];
      String pn = (String)pair[1]; 
      items.add(item);
      productNames.add(pn);
    }
    // ���� request attribut
    request.setAttribute("items", items);
    request.setAttribute("productNames", productNames);
    
    if (acctForm.getAccount().getUsername().equals(username)) {
      request.setAttribute("order", order);
      
      // ѡ�� ViewOrder.jsp �е���ʾ��ʽ
      request.setAttribute("viewOrder", true);
      return mapping.findForward("success");
    } else {
      request.setAttribute("message", "You may only view your own orders.");
      return mapping.findForward("failure");
    }
  }
  // request set in - . get user name 
  // request. set attributed 
  // mapping fine forward 
  
}
