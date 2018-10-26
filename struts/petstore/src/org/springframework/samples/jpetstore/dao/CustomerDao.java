package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.jpetstore.domain.Order;




public interface CustomerDao {
    List getCustomerByUsername(String username) throws DataAccessException;
}
