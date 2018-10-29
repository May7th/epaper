package com.oyun.media.epaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: epaper
 * @description: 阿什顿发
 * @author: changzhen
 * @create: 2018-10-15 16:39
 **/
public class UpdateTest {
    public static void main(String[] args) {

        System.out.println(getImgStr("<p>1234423141241<img src=\"/upload/image/2018-10-15/ff44482bf483495392bb430b2dedc04d.jpeg\">犬瘟热无群若温泉认为群认为群二认为群认为群热区无热武器发<img src=\"/upload/image/2018-10-15/a6971dadd5824326b198d41ec64a117c.jpeg\">432144 321 412 412 341 24 214 124 1234 123 32 14 12 4<img src=\"/upload/image/2018-10-15/778b1177abba4cda9cd29f82c65257b6.jpeg\"></p>"));
    }
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
