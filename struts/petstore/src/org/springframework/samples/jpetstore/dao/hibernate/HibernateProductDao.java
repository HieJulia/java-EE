package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;
import java.util.Scanner;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.ProductDao;

/**
 * domain �����巢���˸ı䣬productId ���� productNumber���� productId ����
 * �� hibernate �Զ�ά���� id ֵ�����ԭ�ȵ� productId ������ productNumber ͬ.
 */
public class HibernateProductDao extends HibernateDaoSupport implements ProductDao {
  
  public List getProductListByCategory(String categoryName) throws DataAccessException {
    return getHibernateTemplate().find(
            "from Product product where product.category.categoryName = ?", categoryName);
  }
  
  /**
   * �ı� DAO �Ľӿڣ�����һ��List������ product  �� categoryName
   * // Ϊ���������: org.hibernate.LazyInitializationException:
   * //               could not initialize proxy - the owning Session was closed
   * // (��ҳ�� Product.jsp �� ${product.category.categoryName} ����).
   * //
   * // ��Ȼ���� "Open Session in View pattern" ģʽ, ��Ӧ Spring �м���
   * // @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
   * // @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
   * // ���䲢���Ǹ�������Ӧ�õ���ѡ����
   * // �������Ҫ�� many-to-one ��ϵ��һ�˼��뵽 request attribute, �Ա�ҳ���
   * // ����.
   * todo: pp renamed productId to productNumber
   */
  public List getProduct(String productNumber) throws DataAccessException {
    return getHibernateTemplate().find(
            "select p , c.categoryName " +
            "from Product p, Category c " +
            "where c.id = p.category.id and p.productNumber = ?", productNumber);
    
//            "select p , c.categoryName " +
//            "from Product p inner join Category c " +
//            "where c.id = p.category.id and p.productNumber = ?", productNumber)
//            .get(0);
  }
  // get product by product number select from product category where 

  /**
   * ȥ��ԭ���� iBatis ����ӳ����ڲ��࣬ȡ����֮�ֹ���װ like '%' ��
   * ƥ����ַ���.
   */
  public List searchProductList(String keywords) throws DataAccessException {
    // ��װ��ѯ like(%) �ַ���
    StringBuilder likeString = new StringBuilder();
    
    Scanner s = new Scanner(keywords);
    while (s.hasNext()) {
      String keyword = s.next().toUpperCase();
      likeString.append("upper(p.productName) like '%" + keyword + "%'");
      likeString.append(" OR upper(p.productDesc) like '%" + keyword + "%'");
      likeString.append(" OR upper(c.categoryName) like '%" + keyword + "%'");
    }
    
    return getHibernateTemplate().find(
            // distinct
            "select distinct p " +
            "from Product p, Category c " +
            "where c.id = p.category.id and ( " + likeString.toString() + " )");
  }

  // search product - select distinc p from product , category where id  category id and upper(proudctnMae) like % keyword %
  // or upper ( product )
  
  
  /**
   * �ڲ��࣬������������ؼ��֣��õ���ѯ�ַ���.
   * not used in hibernate
   */
//  public static class ProductSearch {
//
//    private List keywordList = new ArrayList();
//
//    public ProductSearch(String keywords) {
//      StringTokenizer splitter = new StringTokenizer(keywords, " ", false);
//      while (splitter.hasMoreTokens()) {
//        this.keywordList.add("%" + splitter.nextToken() + "%");
//      }
//    }
//
//    public List getKeywordList() {
//      return keywordList;
//    }
//  }
  
}
