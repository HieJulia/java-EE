/*
 * Account.java
 *
 * Created on 2006��10��1��, ����7:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * domain �����巢���˸ı䣬userId ���� username���� userId ����
 * �� hibernate �Զ�ά���� id ֵ�����ԭ�ȵ� userId ������ username ͬ.
 * �ҽ�ԭ�ȵ� Profile, Singon ���ϲ����˱����ˡ�
 *
 * @author pprun
 */
public class Customer implements java.io.Serializable ,Comparable {
  private Long id;
  private int version;
 
  private String username;
  private String firstName;
  private String lastName;
  
}
