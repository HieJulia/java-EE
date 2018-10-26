package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.LockMode;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.domain.Inventory;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.LineItem;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * domain �����巢���˸ı䣬itemId ���� itemName���� itemId ����
 * �� hibernate �Զ�ά���� id ֵ�����ԭ�ȵ� itemId ������ itemName ͬ.
 *
 */
public class HibernateItemDao extends HibernateDaoSupport implements ItemDao {
  
  public void updateQuantity(Order order) throws DataAccessException {
    for (int i = 0; i < order.getLineItems().size(); i++) {
      LineItem lineItem = (LineItem) order.getLineItems().get(i);
      //String itemId = lineItem.getItemId();
      //String itemName = lineItem.getItem().getItemName();
      Item item = lineItem.getItem();
      Integer increment = new Integer(lineItem.getQuantity());
      
      // ���ض�Ӧ�� innventory
      List ls = getHibernateTemplate().find(
              "from Inventory inv where inv.item.itemName = ?",
              item.getItemName()); // ԭ�ȵ�itemId������Ϊ itemName
      if (ls != null && ls.size() > 0) {
        Inventory inv = (Inventory) ls.get(0);
        
        // ���������ȥ������ֵ
        inv.setQuantity(inv.getQuantity() - increment);
        getHibernateTemplate().saveOrUpdate(inv);
      }
    }
  }
  
  /**
   * todo: pp, renamed itemId to itemName
   */
  public boolean isItemInStock(String itemName) throws DataAccessException {
    boolean result = false;
    
    List ls = getHibernateTemplate().find(
            "select inv.quantity from Inventory inv where " +
            "inv.item.itemName = ?", itemName);
    if (ls != null && ls.size() > 0) {
      Integer i = (Integer) ls.get(0);
      
      result = (i != null && i.intValue() >0);
    }
    
//    List ls = getHibernateTemplate().find(
//             "from Item item where item.itemName = ?", itemName);
//    if (ls != null) {
//      Item item = (Item)ls.get(0);
//
//      ls = null;
//      // ���ض�Ӧ�� innventory
//      ls = getHibernateTemplate().find(
//              "select ity.quantity from Inventory ity where ity.item = ?", item);
//      if (ls != null) {
//        Integer i = (Integer) ls.get(0);
//
//        result = (i != null && i.intValue() >0);
//      }
//    }
    
    return result;
  }
  
  /**
   * todo: pp, renamed productId to productNumber
   */
  public List getItemListByProduct(String productNumber) throws DataAccessException {
    List list =  getHibernateTemplate().find(
            "select item from Item item, Product product " +
            "where item.product.id = product.id " +
            "and product.productNumber = ?", productNumber);
    
    // ����Ҫ�����ı������ˣ�
    // ͨ������ log4j 's log4j.logger.org.hibernate.type=all' Ч�������
    //    System.out.println(">>>>>>>>>>>>>>>" + list.size());
    return list;
  }
  
  /**
   * �ı� DAO �ӿڣ���Ϊ view ��Ҫ product
   *
   * ���ڽ�ԭ�汾�еĻ��ģ��һ��Ϊ����
   * item �� �� inventory �еõ� quantity
   * �����ڿͻ��˵���ʱ������Ҫ���ô˷����⣬
   * ����Ҫ���� getItemQuantity(String itemName)���Դﵽͬ�����߼�.
   *
   * select
   * i.itemid, listprice, unitcost, supplier, i.productid, name,
   * descn, category, status, attr1, attr2, attr3, attr4, attr5, qty
   * from item i, inventory v, product p where p.productid = i.productid
   * and i.itemid = v.itemid and i.itemid = #value#
   *
   * todo: pp, renamed itemId to itemName
   */
  public List getItem(String itemName) throws DataAccessException {
    List ls = getHibernateTemplate().find(
            "select item, item.product " +
            "from Item item " +
            "where item.itemName = ?", itemName);
    
    return ls;
    
//            "select item.itemName, item.listPrice, item.unitCost, " +
//            "item.supplier.supplierName, item.product.productNumber, " +
//            "item.product.productName, item.product.productDesc, " +
//            "item.product.category.categoryName" +
//            "item.status, item.attr1, item.attr2, item.attr3, item.attr4," +
//            "item.attr5, inv.quantity " +
//            "from Item item" +
//            "where item.itemName = ?", itemName);
    
//    if (ls != null && ls.size() > 0) {
//      item =  (Item) ls.get(0);
//    }
//
//    return item;
  }
  
  /**
   * added by pprun:
   * Ϊ����ʹ�� open session in view ģʽ������Ҫ��� viewOrder.jsp ��:
   * lineItem.item.product.productName
   * lineItem.item.*
   * ���ʽ�ķ���.
   * ע�⣬���������������У��ǲ���Ҫ�˷����ģ���Ϊ���Ǹ������� item �Ѿ���ʼ��
   * ���ˡ�
   * ֻ���ڲ鿴�û���Ϣʱ�� listOrder.jsp �У������Ӧ�Ĺ����嵥ʱ
   * (viewOrder.jsp)�Ż�ʹ��.
   *
   */
  public List getItem(Item item) throws DataAccessException {
    // ������ʵ���� retrive detached entity
    // ��Ϊ item ��ͨ����һ�ε� session �л�õ� LineItem �еõ���(detached)��
    // ������Ҫ���¹���������ǰ session
    getHibernateTemplate().lock(item, LockMode.NONE);
    List ls = getHibernateTemplate().find(
            "select item, item.product.productName " +
            "from Item item " +
            "where item.id = ?", item.getId());
    
    return ls;
    // get item : item : 
    // select from werhe 
  }
  
  /**
   * added by pprun
   * ���ڽ�ԭ�汾�еĻ��ģ��һ��Ϊ����
   * item �� �� inventory �еõ� quantity
   * �����ڿͻ��˵���ʱ������Ҫ����getItem(String itemName)�����⣬
   * ����Ҫ���ô˷������Դﵽͬ�����߼�.
   */
  public int getItemQuantity(String itemName) throws DataAccessException {
    int quantity = 0;
    
    List ls = getHibernateTemplate().find(
            "select inv.quantity from Item item, Inventory inv " +
            "where inv.item.id = item.id and item.itemName = ?", itemName);
    
    if (ls != null && ls.size() > 0) {
      Integer i = (Integer) ls.get(0);
      
      quantity =  i==null ? 0 : i.intValue();
    }
    
    return quantity;
  }
  // get item quatity name 
  // get hibernate template : find : seletio from where- 
}
