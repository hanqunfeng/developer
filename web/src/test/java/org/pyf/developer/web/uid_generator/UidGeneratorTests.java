package org.pyf.developer.web.uid_generator;

import com.baidu.fsg.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/11/14 17:55.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UidGeneratorTests {

    @Autowired
    private UidGenerator uidGenerator;

    @Test
    public void getUid() {
        System.out.println(uidGenerator.getUID());
    }
}
