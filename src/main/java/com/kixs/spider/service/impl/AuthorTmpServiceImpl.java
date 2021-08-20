package com.kixs.spider.service.impl;

import com.kixs.spider.dao.AuthorTmpDao;
import com.kixs.spider.entity.AuthorTmpEntity;
import com.kixs.spider.service.AuthorTmpService;
import com.kixs.spider.utils.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (AuthorTmp)表服务实现类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/8/20 09:15
 */
@Service("authorTmpService")
public class AuthorTmpServiceImpl extends BaseServiceImpl<AuthorTmpDao, AuthorTmpEntity> implements AuthorTmpService {

    /**
     * 匹配类型数据
     * <p>
     * eg：李清照（1084年3月13日—约1155年），号易安居士，汉族，齐州济南（今山东省济南市章丘区）人。宋代女词人，婉约词派代表，有“千古第一才女”之称。 李清照出生于书香门第，早期生活优裕，其父李格非藏书甚富，她小时候就在良好的家庭环境中打下文学基础。出嫁后与夫赵明诚共同致力于书画金石的搜集整理。金兵入据中原时，流寓南方，境遇孤苦。所作词，前期多写其悠闲生活，后期多悲叹身世，情调感伤。形式上善用白描手法，自辟途径，语言清丽。论词强调协律，崇尚典雅，提出词“别是一家”之说，反对以作诗文之法作词。能诗，留存不多，部分篇章感时咏史，情辞慷慨，与其词风不同。 有《易安居士文集》《易安词》，已散佚。后人有《漱玉词》辑本。今有《李清照集校注》。
     * result：（1084年3月13日—约1155年）
     */
    private final static Pattern pattern_110 = Pattern.compile("（(.*?)——(.*?)）");
    private final static Pattern pattern_120 = Pattern.compile("（(.*?)—(.*?)）");
    private final static Pattern pattern_130 = Pattern.compile("（(.*?)-(.*?)）");
    private final static Pattern pattern_140 = Pattern.compile("（(.*?)～(.*?)）");
    private final static Pattern pattern_141 = Pattern.compile("（(.*?)~(.*?)）");
    private final static Pattern pattern_150 = Pattern.compile("（(.*?)―(.*?)）");
    private final static Pattern pattern_160 = Pattern.compile("（(.*?)－(.*?)）");
    private final static Pattern pattern_170 = Pattern.compile("（(.*?)一(.*?)）");
    private final static Pattern pattern_180 = Pattern.compile("\\((.*?)—(.*?)\\)");
    private final static Pattern pattern_190 = Pattern.compile("\\((.*?)～(.*?)\\)");
    private final static Pattern pattern_200 = Pattern.compile("（(.*?)---(.*?)）");

    private final static Pattern pattern = Pattern.compile("[（|\\\\(](\\S{1,14})[－|—|\\\\-|～|一|―|~|-]{1,3}(\\S{1,14})[\\\\)|）]");

    private final static Pattern pattern_0 = Pattern.compile("（(.*?)至(.*?)）");
    // private final static Pattern pattern_7 = Pattern.compile("（(.*?)[—|－|-|～|至|——](.*?)）");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dataScrubbing() {
        List<AuthorTmpEntity> entities = this.baseDao.queryAll(new AuthorTmpEntity());

        entities.stream().parallel().forEach(this::dataScrubbing);
    }

    private void dataScrubbing(AuthorTmpEntity author) {
        handleDynasty(author);
        handleDescription(author);
        handleBirthAndObit(author);
        this.baseDao.updateById(author);
    }

    /**
     * 朝代处理
     * 1、默认先取author表数据，没有则取poetry_author表数据；
     * 2、若a与pa皆不为空，则两者‘|’拼接处理
     * 3、一样表示数据一致，直接采用一致数据
     *
     * @param author 作者信息
     */
    private void handleDynasty(AuthorTmpEntity author) {
        String dynasty = author.getADynasty();
        if (StringUtils.isNotBlank(dynasty) && StringUtils.isNotBlank(author.getPaDynasty())) {
            if (!dynasty.equals(author.getPaDynasty())) {
                dynasty += "|" + author.getPaDynasty();
            }
        } else {
            dynasty = author.getPaDynasty();
        }

        author.setDynasty(dynasty);
    }

    /**
     * 生平简介处理
     * poetry_author表生平简介数据较完整，格式较统一，优先poetry_author表数据
     *
     * @param author 作者信息
     */
    private void handleDescription(AuthorTmpEntity author) {
        String description = author.getADescription();
        if (StringUtils.isNotBlank(author.getPaDescription())) {
            description = author.getPaDescription();
        }

        author.setDescription(description);
    }

    /**
     * 生卒年处理
     * <p>
     * 解析生平简介
     *
     * @param author 作者信息
     */
    private void handleBirthAndObit(AuthorTmpEntity author) {
        String description = author.getDescription();
        if (StringUtils.isNotBlank(description)) {
            // 避免长文本模式匹配过多，故截取数据长度，生卒年数据一般均在字符串前半段，故截取数据最长64个字符长度
            Matcher matcher = matcher1(description);
            // 若匹配成功，取首位匹配结果处理
            if (Objects.nonNull(matcher)) {
                int count = matcher.groupCount();
                String birthDate = null;
                String obitDate = null;
                if (count >= 2) {
                    birthDate = matcher.group(1);
                    obitDate = matcher.group(2);
                    if (birthDate.length() > 13 || obitDate.length() > 13) {
                        // 数据过长，异常不处理
                        birthDate = null;
                        obitDate = null;
                    }
                } else if (count == 1) {
                    birthDate = matcher.group(1);
                    if (birthDate.length() > 13) {
                        // 数据过长，异常
                        birthDate = null;
                    }
                }
                author.setBirthDate(birthDate);
                author.setObitDate(obitDate);
            }
        }
    }

    private static Matcher matcher1(String data) {
        if (data.contains("生卒年不详") || data.contains("生卒不详")) {
            return null;
        }

        Matcher matcher;
        matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher;
        }
        return null;
    }

    private static Matcher matcher(String data) {
        if (data.contains("生卒年不详") || data.contains("生卒不详")) {
            return null;
        }

        Matcher matcher;
        matcher = pattern_110.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_120.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_130.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_140.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_141.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_150.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_160.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_170.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_180.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_190.matcher(data);
        while (matcher.find()) {
            return matcher;
        }
        matcher = pattern_200.matcher(data);
        while (matcher.find()) {
            return matcher;
        }

        /*matcher = pattern_0.matcher(data);
        if (matcher.find()) {
            return matcher;
        }*/
        return null;
    }

    public static void main(String[] args) {
        String data = "白珽（一二四八～一三二八），字廷玉，号湛渊、栖霞山人，钱塘（今浙江杭州）人。理宗景定元年（一二六○）入太学。度宗咸淳中以诗著，与同邑仇远合称仇白。宋亡，以教授生徒为业。后以荐为太平路学正，历常州路教授、江浙等处儒学副提举，以兰溪州判官致仕。元文宗天历元年卒，年八十一。有《湛渊集》八卷，已佚。清杭州沈嵩町辑为一卷。事见本集附录明宋濂《湛渊先生白公墓志铭》。　白珽诗，以影印文渊阁《四库全书》本为底本，与新辑集外诗合编为一卷。";
        Pattern pattern = Pattern.compile("[（|\\\\(](\\S{1,14})[－|—|\\\\-|～|一|―|~|-]{1,3}(\\S{1,14})[\\\\)|）]");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            System.out.println(matcher.group());
            int count = matcher.groupCount();
            if (count >= 2) {
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));

            } else if (count == 1) {
                System.out.println(matcher.group(1));
            }
            System.out.println("-----------------------");
        }
    }
}