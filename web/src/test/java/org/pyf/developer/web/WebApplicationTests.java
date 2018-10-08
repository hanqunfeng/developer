package org.pyf.developer.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyf.developer.bean.one.model.auth.SystemAuthority;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.service.auth.ISystemAuthorityService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Autowired
    private ISystemAuthorityService systemAuthorityService;

    @Autowired
    private ISystemUserService systemUserService;

    //@Test
    public void test1() {
        SystemAuthority systemAuthority = new SystemAuthority();
        systemAuthority.setName("用户");
        systemAuthority.setDescription("用户");

        CP_Sorter sorter = new CP_Sorter();
        sorter.setSortName("id");
        sorter.setSortType(CP_Sorter.DESC);

        CP_Page page = new CP_Page();
        page.setIndex(0);

        List<SystemAuthority> list = systemAuthorityService.findByPage(systemAuthority,sorter,page);
        System.out.println("list.size=="+list.size());

        systemAuthority = new SystemAuthority();

        list = systemAuthorityService.findByPage(systemAuthority,sorter,page);
        System.out.println("list.size=="+list.size());
    }

    @Test
    public void test2(){
        SystemUser systemUser = systemUserService.findById("admin");
        System.out.println(systemUser.getName());
    }

    @Test
    public void test3(){

        String[] a = { "0", "1", "2", "3","4" };

        String[] b = { "4", "5", "6", "1", "2" };
        LinkedHashSet set = new LinkedHashSet();
        set.addAll(Arrays.asList(a));
        set.addAll(Arrays.asList(b));

        String[] c = (String[])set.toArray(new String[0]);
        System.out.println(c.length);
    }

}
