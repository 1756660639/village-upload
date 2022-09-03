package com.village.villageupload.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinyinUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PinyinUtils.class);

    /**
     * 将文字转为汉语拼音
     *
     * @param chinese 要转成拼音的中文
     */
    public static String toPinyin(String chinese) {
        char[] cl_chars = chinese.trim().toCharArray();
        StringBuilder hanyupinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = format();
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                // 如果字符是中文,则将中文转为汉语拼音
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {
                    hanyupinyin.append(PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0]);
                } else {// 如果字符不是中文,则不转换
                    hanyupinyin.append(cl_chars[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LOGGER.error("字符不能转成汉语拼音", e);
        }
        return hanyupinyin.toString();
    }

    /**
     * 将文字转为汉语拼音
     *
     * @param chinese 要转成拼音的中文
     */
    public static String toPinyinOfFirstLetter(String chinese) {
        char[] cl_chars = chinese.trim().toCharArray();
        StringBuilder hanyupinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = format();
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);
                // 如果字符是中文,则将中文转为汉语拼音
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    //多音字只取第一个
                    String s = PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                    char firstLetter = s.charAt(0);
                    String pinyin = String.valueOf(firstLetter).toUpperCase().concat(s.substring(1));
                    hanyupinyin.append(pinyin);
                } else {
                    // 如果字符不是中文,则不转换
                    hanyupinyin.append(cl_chars[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LOGGER.error("字符不能转成汉语拼音", e);
        }
        return hanyupinyin.toString();
    }

    public static String getFirstLettersUpper(String chinese) {
        return getFirstLetters(chinese, HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstLettersLower(String chinese) {
        return getFirstLetters(chinese, HanyuPinyinCaseType.LOWERCASE);
    }

    private static String getFirstLetters(String chinese, HanyuPinyinCaseType caseType) {
        char[] cl_chars = chinese.trim().toCharArray();
        StringBuilder hanyupinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);

                if (str.matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin.append(
                            PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1));
                } else if (str.matches("[0-9]+")) {
                    // 如果字符是数字,取数字
                    hanyupinyin.append(cl_chars[i]);
                } else if (str.matches("[a-zA-Z]+")) {
                    // 如果字符是字母,取字母
                    hanyupinyin.append(cl_chars[i]);
                } else {// 否则不转换
                    hanyupinyin.append(cl_chars[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LOGGER.error("字符不能转成汉语拼音", e);
        }
        return hanyupinyin.toString();
    }

    private static HanyuPinyinOutputFormat format() {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        return defaultFormat;
    }


    public static void main(String[] args) {
        String s = "大师兄师傅被(妖怪抓走了)";
        System.out.println(PinyinUtils.toPinyin(s));
        System.out.println(PinyinUtils.toPinyinOfFirstLetter(s));

        System.out.println(PinyinUtils.getFirstLettersUpper(s));
        System.out.println(PinyinUtils.getFirstLettersLower(s));

    }
}
