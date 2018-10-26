package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;

public class AccountActionForm extends BaseActionForm {
    
    /** ���ڼ���ĳ������壬��Ϊ���½��ʻ����޸��ʻ�ʱ�����߼��ǲ�һ���ġ�
     * �������޸��ʻ�ʱ���ʻ������Ѿ������� */
    public static final String VALIDATE_EDIT_ACCOUNT = "editAccount";
    public static final String VALIDATE_NEW_ACCOUNT = "newAccount";
    
    /** ���ڴ����û����������Ե��б� */
    private static final ArrayList LANGUAGE_LIST = new ArrayList();
    
    /* Private Fields */
    // ������������ Account �еĳ�Ա�ظ��ˣ�������Ϊ�� Form �����ҳ���ظ�ʹ�õ�
    // �������Ϊ�ڵ�¼ҳ��ʱ����ʱ���������� Account, ���Բ�����ͨ��
    // account.getUsername() �� account.getPassword() ���õ��û�������ֵ�ģ�
    
    // ����������ڵ�¼��ʱ�ռ����� ��Ϣ��
    // ��������������޸ģ��½��ʻ�ʱ�����Ǽ��ʹ���� Account �еĳ�Ա����ʱ��
    // �Ѿ��� session �д����һ�� Account ��ʵ��
    
    // �����������д��۵ģ�ʹ���벻��ôֱ���ˣ������һ��ҳ��� Form ��Ӧһ��
    // FormBean �Ļ������³�Ա��ҳ���е�����Ԫ����һһ��Ӧ�ģ�
    
    // ����¼ҳ��ʹ�õ� Ԫ��
    private String username;
    private String password;
    
    // ��¼�����ʻ���ص�Ԫ��
    private String repeatedPassword;
    private List languages;
    private List categories;

    // account action form : username, password , language , categories 
    
    /**
     * �����Ա��ֵ��ͨ��ҳ������Ԫ�ش���ģ�
     * NewAccountForm.jsp ��: <html:hidden name="workingAccountForm" property="validate" value="newAccount"/>
     * EditAccountForm.jsp ��:<html:hidden name="workingAccountForm" property="validate" value="editAccount" />
     */
    private String validate;
    
    /**
     * ������ס�û��Ǵ�������ת�����ģ���Ϊ׼���Թ��ﳵ���н���ʱ�����û�е�¼
     * �Ļ������Ƚ���������ҳ��ĵ�ַ����˳�Ա�У���¼�ɹ�������ת��ȥ��
     * ���û������һ�������Ļ�����ô�ͻ��������Ľ����ͻ���ҳ��(Ҳ���ǳ���
     * ���߼����̴������û��Ľ��̣�������Ӧ������ġ�)
     */
    private String forwardAction;
    
    /**
     * ���е��ʺ���Ϣ������� POJO ��
     */
    private Account account;
    
    // added by pprun
    /**
     * ������ʾ����������û���Ϣҳ��ѡ����ʾ����ʱ
     */
    private String bannerName;
    
    /**
     * ������ʾ�����û���ϲ�ñ��Ƽ��ĳ����б�,����ѡ������ʾ���б�ʱ��
     */
    private PagedListHolder myList;
    
    /**
     * �û���ϲ���ĳ������
     */
    private String favCategoryName;
    
    /* Static Initializer */
    
    static {
        LANGUAGE_LIST.add("english");
        LANGUAGE_LIST.add("japanese");
    }
    
    public AccountActionForm() {
        languages = LANGUAGE_LIST;
    }
    
    public PagedListHolder getMyList() {
        return myList;
    }
    public void setMyList(PagedListHolder myList) {
        this.myList = myList;
    }
    
    public String getForwardAction() {
        return forwardAction;
    }
    public void setForwardAction(String forwardAction) {
        this.forwardAction = forwardAction;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRepeatedPassword() {
        return repeatedPassword;
    }
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public List getLanguages() {
        return languages;
    }
    public void setLanguages(List languages) {
        this.languages = languages;
    }
    
    public List getCategories() {
        return categories;
    }
    public void setCategories(List categories) {
        this.categories = categories;
    }
    
    public String getBannerName() {
        return bannerName;
    }
    
    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }
    
    public String getFavCategoryName() {
        return favCategoryName;
    }
    
    public void setFavCategoryName(String favCategoryName) {
        this.favCategoryName = favCategoryName;
    }
    
    public String getValidate() {
        return validate;
    }
    public void setValidate(String validate) {
        this.validate = validate;
    }
    
    /**
     * ���Ǹ����еķ�������������У��
     */
    public void doValidate(ActionMapping mapping,
            HttpServletRequest request, List errors) {
        if (validate != null) {
            if (VALIDATE_EDIT_ACCOUNT.equals(validate) ||
                    VALIDATE_NEW_ACCOUNT.equals(validate)) {
                if (VALIDATE_NEW_ACCOUNT.equals(validate)) {
                    // ���½��ʻ�ʱ����Ҫ�����У��
                    
                    account.setStatus("OK");
                    addErrorIfStringEmpty(errors, "User ID is required.",
                            account.getUsername());
                    
                    if (account.getPassword() == null ||
                            account.getPassword().length() < 1 ||
                            !account.getPassword().equals(repeatedPassword)) {
                        errors.add("Passwords did not match or were not provided.  " +
                                "Matching passwords are required.");
                    }
                }
                
                if (account.getPassword() != null &&
                        account.getPassword().length() > 0) {
                    if (!account.getPassword().equals(repeatedPassword)) {
                        errors.add("Passwords did not match.");
                    }
                }
                
                addErrorIfStringEmpty(errors, "First name is required.",
                        this.account.getFirstname());
                addErrorIfStringEmpty(errors, "Last name is required.",
                        this.account.getLastname());
                addErrorIfStringEmpty(errors, "Email address is required.",
                        this.account.getEmail());
                addErrorIfStringEmpty(errors, "Phone number is required.",
                        this.account.getPhone());
                addErrorIfStringEmpty(errors, "Address (1) is required.",
                        this.account.getUserAddr().getAddr1());
                addErrorIfStringEmpty(errors, "City is required.",
                        this.account.getUserAddr().getCity());
                addErrorIfStringEmpty(errors, "State is required.",
                        this.account.getUserAddr().getState());
                addErrorIfStringEmpty(errors, "ZIP is required.",
                        this.account.getUserAddr().getZipcode());
                addErrorIfStringEmpty(errors, "Country is required.",
                        this.account.getUserAddr().getCountry());
            }
        }
    }
    
    /**
     * �˷�����һ������Ҫ�ķ��������ǿ��������жԸ÷���������:
     *
     * Reset bean properties to their default state, as needed.
     * This method is called before the properties are repopulated by the controller.
     * ����Ҫʱ����λ Bean ������ֵ���˷������ڿ�����������װBean������ֵ֮ǰ���õġ�
     *
     * The default implementation does nothing. In practice, the only properties
     * that need to be reset are those which represent checkboxes on a
     * session-scoped form. Otherwise, properties can be given initial values
     * where the field is declared.
     * Ĭ�ϵ�ʵ�֣���û�����κ��¡�ʵ���ϣ�Ψһ��Ҫ���õ���������Щ����
     * session ������ĸ�ѡ��ҳ��Ԫ�ء�������ЩԪ�ؽ�ʹ��ҳ����������Ĭ��ֵ��
     * �ǹ�ѡ����δ��ѡ��
     *
     * If the form is stored in session-scope so that values can be collected
     * over multiple requests (a "wizard"), you must be very careful of which properties,
     * if any, are reset. As mentioned, session-scope checkboxes must be reset to
     * false for any page where this property is set. This is because the client
     * does not submit a checkbox value when it is clear (false).
     * If a session-scoped checkbox is not proactively reset, it can never be set to false.
     * ������Ǵ����� Session ��������(�磺
     * <action path="/shop/signon" type="org.springframework.samples.jpetstore.web.struts.SignonAction"
     * name="accountForm" scope="session" validate="false">
     * ������Ϊ session ��Χ�� formBean)�Ļ�����Ԫ�ص�ֵ�����ڶ������(����ҳ����ҳ��)
     * �б��ռ�����ʱ����С�ĶԵ���Щ������������á�������ǰ��������session
     * ������Χ�ڵ� checkbox(��ѡ��ť)����Ϊ��������ֵ֮ǰ��������Ϊ false��
     * ��Ϊ�ͻ���(�������)�ڸ�ѡ��ťδ����ѡʱ�����ᷢ���κ�ֵ���������ˡ�(����
     * �ͳ������������⣺���֮ǰ�ø�ѡ��ť�ǹ�ѡ״̬�������û�������һҳ��
     * �ð�ť��ʾΪ��ѡ״̬���ں����Ĳ����У��û�ȡ��ѡ��״̬��������Ϊ checkbox
     * ��ȡ��ѡ��״̬����������������κι�������ؼ�����Ϣ���� ActionForm ��
     * Ҫ�ı���Ƶ�״̬������Ƚ��������������״̬�͵�ǰ״̬������Ϊ�������δ
     * ��֪�������� ActionForm ��Ϊ����ؼ���״̬��δ�ı䡣��Ϊ����ʱ��ʼ������
     * �û���ô��������ؼ�����Զ����Ϊѡ��״̬)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setUsername(null);
        setPassword(null);
        setRepeatedPassword(null);
        
        // BUG here: by pprun
        // ���մ˷����� api �ĵ�˵����˵ checkbox ��ֵ�����ڴ˸�λ��
        //���� NewAccountForm.jsp �� Enable MyList �� Enable MyBanner ȴû��
        // ���Ե��û���һ��ѡ�к��Ժ����Ϊδѡ����û���ˣ�(������������������
        // ���磺      acctForm.getAccount().setDisplayMylist(
        //          request.getParameter("account.displayMylist") != null);
        //   acctForm.getAccount().setDisplayBanner(
        //          request.getParameter("account.displayBanner") != null);)
        //
        // ���ǵ��������ʱ������ʾ��ǰҳ��ʱ���ϴ�ѡΪδѡ��״̬����ʧ�ˣ�
        //
        // ��Ϊ���� api ��˵��
        // �� checkbox Ϊδѡ��״̬ʱ��������ǲ��ᷢ��Ϣ���������˵ģ�����
        // struts �޷�������ֵ
        // ����취��
        if (getAccount() != null) {
            getAccount().setDisplayMylist(false);
            getAccount().setDisplayBanner(false);
        }
    }
}
