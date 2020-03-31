package com.guigui.perona;

import com.guigui.perona.entity.*;
import com.guigui.perona.entity.gencode.GenTable;
import com.guigui.perona.manage.tool.gencode.service.IGenTableService;
import com.guigui.perona.manage.web.service.ConfigService;
import com.guigui.perona.manage.web.service.DictService;
import com.guigui.perona.service.IArtTagService;
import com.guigui.perona.service.IMenuInfoService;
import com.guigui.perona.service.IUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeronaApplicationTests {

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IMenuInfoService menuInfoService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private DictService dictService;

    @Autowired
    private IArtTagService artTagService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGenCode() throws Exception {
        byte[] bytes = genTableService.generatorCode(new String[]{"role_info", "dept_info", "menu_info",
                "user_info", "dict_type", "param_config", "dict_data", "art_tag", "article", "category",
                "comment", "article_category", "article_tag", "friend_link", "login_log"});

        OutputStream out = new FileOutputStream("/Users/11101215/Desktop/temp/manage.zip");
        InputStream is = new ByteArrayInputStream(bytes);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();

    }

    @Test
    public void testImportTable() {
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(new String[]{"login_log"});

        genTableService.importGenTable(tableList);

        System.out.println(tableList);
    }

    @Test
    public void testMenuService() {
        MenuInfo menuInfo = menuInfoService.selectMenuInfoById(100L);
        System.out.println(menuInfo);
    }

    @Test
    public void testUserService () {
        UserInfo userInfo = userInfoService.findByName("perona");
        System.out.println(userInfo);
        List<MenuInfo> menuInfos = menuInfoService.selectMenuInfosByUser(userInfo);
        System.out.println(menuInfos);

    }

    @Test
    public void testCommon() {
//        System.out.println(configService.getKey("index.skinName"));
//        List<DictData> list = dictService.getType("user_sex");
//        String label = dictService.getLabel("user_sex", "0");
//        System.out.println(list);
//        System.out.println(label);
//        List<ArtTag> list = artTagService.selectByArticleId(15L);
//        System.out.println(list);
//        List<ArtTag> listBat = artTagService.selectByArticleIds(Arrays.asList(11L, 13L, 15L));
//        System.out.println(listBat);

    }

}
