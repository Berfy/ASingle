package cn.berfy.looking.db.base;

/**
 * 所有表的字段
 *
 * @author Berfy
 */
public class OpenDBUtil {

    public static final String TAB_CONTENTS = "tab_content";// 内容表
    public static final String TAB_URLS = "tab_urls";// 网址表
    public static final String TAB_KEYWORDS = "tab_keywords";// 关键字表

    // 内容表字段
    public static final String[] KEYS_TAB_CONTENTS = new String[]{"_id", "fromUrl", "url", "thumb",
            "title", "content", "keyWords", "updateTime"};

    /**
     * 创建内容表
     */
    public static final String CREATE_TAB_CONTENTS = "CREATE TABLE if not exists "
            + TAB_CONTENTS
            + "("
            + KEYS_TAB_CONTENTS[0] + " integer primary key autoincrement," + KEYS_TAB_CONTENTS[1] + " txt," + KEYS_TAB_CONTENTS[2] + " txt,"
            + KEYS_TAB_CONTENTS[3] + " txt," + KEYS_TAB_CONTENTS[4] + " txt," + KEYS_TAB_CONTENTS[5]
            + " txt," + KEYS_TAB_CONTENTS[6] + " txt," + KEYS_TAB_CONTENTS[7] + " long not null);";

    // 网址表字段
    public static final String[] KEYS_TAB_URLS = new String[]{"_id", "url", "updateTime"};

    /**
     * 创建网址表
     */
    public static final String CREATE_TAB_URLS = "CREATE TABLE if not exists "
            + TAB_URLS
            + "( " + KEYS_TAB_URLS[0] + " integer primary key autoincrement," + KEYS_TAB_URLS[1] + " txt, " + KEYS_TAB_URLS[2] + " long not null);";

    // 关键字表字段
    public static final String[] KEYS_TAB_KEYWORDS = new String[]{"_id", "keyWords", "updateTime"};

    /**
     * 创建关键字表
     */
    public static final String CREATE_TAB_KEYWORDS = "CREATE TABLE if not exists "
            + TAB_KEYWORDS
            + " (" + KEYS_TAB_KEYWORDS[0] + " integer primary key autoincrement," + KEYS_TAB_KEYWORDS[1] + " txt, " + KEYS_TAB_KEYWORDS[2] + " long not null);";

}
