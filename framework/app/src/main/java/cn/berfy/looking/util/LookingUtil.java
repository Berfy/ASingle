package cn.berfy.looking.util;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.http.VolleyCallBack;
import cn.berfy.framework.http.VolleyHttp;
import cn.berfy.framework.utils.CheckUtil;
import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ReflexUtil;
import cn.berfy.looking.bean.ContentBean;
import cn.berfy.looking.bean.KeyWordsBean;
import cn.berfy.looking.bean.TestBean;
import cn.berfy.looking.bean.UrlsBean;
import cn.berfy.looking.db.TabContent;
import cn.berfy.looking.db.TabKeyWords;
import cn.berfy.looking.db.TabUrls;

/**
 * Created by Berfy on 2017/4/10.
 */

public class LookingUtil {

    private Context mContext;
    private float mProgress;

    public LookingUtil() {
    }

    public LookingUtil(Context context) {
        mContext = context;
    }

    public void look(String tg) {
        LogUtil.e("反射成功", tg);
    }

    public void look(final OnLookListener onLookListener) {
        ReflexUtil fansheUtil = new ReflexUtil();
        fansheUtil.getClassFields(ContentBean.class);
        TestBean testBean = new TestBean();
        testBean.setTitle("测试通过");
        ContentBean contentBean = new ContentBean();
        contentBean.setId(123);
        contentBean.setTitle("测试通过");
        contentBean.setContent("测试通过");
        testBean.setData(contentBean);
        List<ContentBean> contentBeens = new ArrayList<>();
        contentBeens.add(contentBean);
        testBean.setList(contentBeens);
        TestBean contentBean1 = (TestBean) fansheUtil.fromJson(TestBean.class, GsonUtil.getInstance().toJson(testBean));
        LogUtil.e("啊啊啊",GsonUtil.getInstance().toJson(contentBean1));

        List<UrlsBean> urlsBeen = TabUrls.getInstances().getAllData();
        List<KeyWordsBean> keyWordsBeens = TabKeyWords.getInstances().getAllData();
        onLookListener.progress(0, "分析中...");
        if (urlsBeen.size() == 0) {
            onLookListener.suc();
        } else {
            look(urlsBeen, keyWordsBeens, 0, new OnLookListener() {
                @Override
                public void suc() {
                    onLookListener.suc();
                }

                @Override
                public void progress(float progress, String currentTitle) {
                    onLookListener.progress(progress, currentTitle);
                    if (progress >= 100) {
                        onLookListener.suc();
                    }
                }

                @Override
                public void error() {
                    onLookListener.error();
                }
            });
        }
    }

    public void look(final List<UrlsBean> urlsBeens, final List<KeyWordsBean> keyWordsBeens, final int position, final OnLookListener onLookListener) {
        final int size = urlsBeens.size();
        final float totalProgress = (float) ((position + 1) * 100 / size);
        mProgress = totalProgress;
        if (size > position) {
            UrlsBean urlsBean = urlsBeens.get(position);
            final String url = urlsBean.getUrl();
            LogUtil.d("查找网址", url);
            if (url.startsWith("http") && !checkFile(url)) {
                VolleyHttp.getInstances().post(url, null, new VolleyCallBack() {
                    @Override
                    public void finish(String result) {
                        if (result.length() < 1024 * 1024 * 4) {
                            Document document = Jsoup.parse(result);
                            LogUtil.e("源代码", document.html());
                            Elements elements = document.getElementsByTag("a");
                            look(keyWordsBeens, url, elements, 0, new OnLookProgressListener() {
                                @Override
                                public void progress(float progress, String currentTitle) {
                                    mProgress = totalProgress * progress / 100;
                                    LogUtil.e("总进度", totalProgress + "   当前子进度   " + currentTitle + "   " + progress + "   计算后进度 " + mProgress);
                                    onLookListener.progress(mProgress, currentTitle);
                                    if (progress >= 100) {
                                        look(urlsBeens, keyWordsBeens, position + 1, onLookListener);
                                    }
                                }
                            });
                        } else {
                            onLookListener.progress(mProgress, "");
                            look(urlsBeens, keyWordsBeens, position + 1, onLookListener);
                        }
                    }

                    @Override
                    public void error(VolleyError volleyError) {
                        onLookListener.progress(mProgress, "");
                        look(urlsBeens, keyWordsBeens, position + 1, onLookListener);
                    }
                }, false);
            } else {
                onLookListener.progress(mProgress, "");
                look(urlsBeens, keyWordsBeens, position + 1, onLookListener);
            }
        }
    }

    private void look(final List<KeyWordsBean> keyWordsBeens, String url, final Elements elements, final int position, final OnLookProgressListener onLookProgressListener) {
        int size = elements.size();
        LogUtil.e("递归查询", position + "/" + size);
        final float totalProgress = (float) ((position + 1) * 100 / size);
        if (size > position) {
            Element element = elements.get(position);
            if (!TextUtils.isEmpty(element.text())) {
                final ContentBean contentBean = new ContentBean();
                final String title = element.text();
                boolean isHasKey = false;
                for (KeyWordsBean keyWordsBean : keyWordsBeens) {
                    if (title.contains(keyWordsBean.getKeyWords())) {
                        contentBean.setKeyWord(keyWordsBean.getKeyWords());
                        isHasKey = true;
                    }
                }
//                if (isHasKey) {
                contentBean.setTitle(title);
                contentBean.setFromUrl(url);
                String contentUrl = element.attr("href");
                LogUtil.e("检索内容   链接: ", contentUrl + "   文字: " + title);
                if (CheckUtil.checkUrl(contentUrl)) {
                    contentBean.setUrl(contentUrl);
                    lookThumb(contentBean.getUrl(), new OnLookThumbListener() {
                        @Override
                        public void suc(String url, String content, String imageUrl) {
                            contentBean.setThumb(imageUrl);
                            contentBean.setContent(content);
                            LogUtil.e("检索到内容 suc", GsonUtil.getInstance().toJson(contentBean));
                            onLookProgressListener.progress(totalProgress, title);
                            look(keyWordsBeens, url, elements, position + 1, onLookProgressListener);
                            TabContent.getInstances().addContent(contentBean);
                        }

                        @Override
                        public void error(String url) {
                            LogUtil.e("检索到内容 err", GsonUtil.getInstance().toJson(contentBean));
                            onLookProgressListener.progress(totalProgress, title);
                            look(keyWordsBeens, url, elements, position + 1, onLookProgressListener);
                            TabContent.getInstances().addContent(contentBean);
                        }
                    });
                } else {
                    onLookProgressListener.progress(totalProgress, "");
                    look(keyWordsBeens, url, elements, position + 1, onLookProgressListener);
                }
            } else {
                onLookProgressListener.progress(totalProgress, "");
                look(keyWordsBeens, url, elements, position + 1, onLookProgressListener);
            }
        }
    }

    private void lookThumb(final String url, final OnLookThumbListener onLookListener) {
        LogUtil.d("查找图片", url);
        if (url.startsWith("http") && !checkFile(url)) {
            VolleyHttp.getInstances().post(url, null, new VolleyCallBack() {
                @Override
                public void finish(String result) {
                    if (result.length() < 1024 * 1024 * 4) {
                        Document document = Jsoup.parse(result);
                        Elements elements = document.getElementsByTag("img");
                        Elements contents = document.head().getElementsByTag("title");
                        LogUtil.e("查找标题", contents.text());
                        String content = "", imageUrl = "";
                        if (contents.size() > 0) {
                            content = contents.get(0).text();
                            LogUtil.e("内容", content);
                        }
                        for (Element element : elements) {
                            if (element.attr("href").lastIndexOf("png") > 0) {
                                imageUrl = element.attr("href");
                                LogUtil.e("图片", element.attr("href"));
                            }
                        }
                        onLookListener.suc(url, content, imageUrl);
                    } else {
                        onLookListener.error(url);
                    }
                }

                @Override
                public void error(VolleyError volleyError) {
                    onLookListener.error(url);
                }
            }, false);
        } else {
            onLookListener.error(url);
        }
    }

    public interface OnLookProgressListener {
        void progress(float progress, String currentTitle);
    }

    public interface OnLookListener {
        void suc();

        void progress(float progress, String currentTitle);

        void error();
    }

    public interface OnLookThumbListener {
        void suc(String url, String content, String imageUrl);

        void error(String url);
    }

    private boolean checkFile(String url) {
        LogUtil.e("检查文件", url);
        if (url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        LogUtil.e("检查文件", url);
        if (url.endsWith(".apk")) {
            return true;
        } else if (url.endsWith(".apk")) {
            return true;
        } else if (url.endsWith(".exe")) {
            return true;
        } else if (url.endsWith(".rar")) {
            return true;
        } else if (url.endsWith(".zip")) {
            return true;
        }
        return false;
    }
}
