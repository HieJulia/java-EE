package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.CategoryDao;
import org.springframework.samples.jpetstore.domain.Category;


public class HibernateCustomerDao extends HibernateDaoSupport implements CustomerDao {
    // class extends hibernateDaoSupport - implements CustomerDoa 
  
  
  public List getCustomerList() throws DataAccessException {
      return getHibernateTemplate().find("from Customer");

  }
  

// get customer by customer name
public Customer getCustomer(String name) throws DataAccessException {
    Customer c = null ;
    
    List ls = getHibernateTemplate().find(
        "from Customer c where c.username =?", name);
    )

if (ls != null && ls.size() > 0) {
  category =  (Category) ls.get(0);
}

return category;


// get list : from category.list()
// by nmae fien rom wehr e