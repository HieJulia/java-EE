package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Product;

public class ViewProductAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String productNumber = request.getParameter("productNumber");
    
    if (productNumber != null) {
      PagedListHolder itemList = new PagedListHolder(getPetStore().
              getItemListByProduct(productNumber));
      itemList.setPageSize(4);
      
      // �ı� DAO �Ľӿڣ�����һ��List������ product  �� categoryName
      // Ϊ���������: org.hibernate.LazyInitializationException:
      //               could not initialize proxy - the owning Session was closed
      // (��ҳ�� Product.jsp �� ${product.category.categoryName} ����).
      //
      // ��Ȼ���� "Open Session in View pattern" ģʽ, ��Ӧ Spring �м���
      // @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
      // @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
      // ���䲢���Ǹ�������Ӧ�õ���ѡ����
      // �������Ҫ�� many-to-one ��ϵ��һ�˼�(category.categoryName)�뵽 
      // request attribute, �Ա�ҳ��ɷ���.
      List list = getPetStore().getProduct(productNumber);
      Product product = null;
      String categoryName = null;
      for (Iterator it = list.iterator(); it.hasNext();) {
        Object[] pair = (Object[]) it.next();
        product = (Product)pair[0];
        categoryName = (String)pair[1];
      }

      // 
      
      // -- for debug
//      System.out.println(categoryName);
//      System.out.println(product);
      // -- 
      request.getSession().setAttribute("ViewProductAction_itemList", itemList);
      request.getSession().setAttribute("ViewProductAction_product", product);
      request.setAttribute("itemList", itemList);
      request.setAttribute("product", product);
      request.setAttribute("categoryName", categoryName);
      
    } else {
      PagedListHolder itemList = (PagedListHolder) request.getSession().
              getAttribute("ViewProductAction_itemList");
      Product product = (Product) request.getSession().
              getAttribute("ViewProductAction_product");
      String page = request.getParameter("page");
      if ("next".equals(page)) {
        itemList.nextPage();
      } else if ("previous".equals(page)) {
        itemList.previousPage();
      }
      request.setAttribute("itemList", itemList);
      request.setAttribute("product", product);
    }
    // ��ֹ�����ύ
    saveToken(request);
    //System.out.println(request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY));
    return mapping.findForward("success");
  }
  
}
