package com.village.villageupload;

import cn.hutool.core.util.ObjectUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@RestController
public class test1 {
    public static void main1(String[] args) {
        List<Map<String,Object>> list = new ArrayList<>();
        HashMap hashMap1 = new HashMap();
        hashMap1.put("id","1d73fca9-7770-4824-accd-a80207f71702");
        hashMap1.put("toid","4e6ea72c-61c7-429d-80f4-860d1fd14291,");
        hashMap1.put("code","|449|");
        hashMap1.put("type","is_step");
        HashMap hashMap3 = new HashMap();
        hashMap3.put("id","2530e0db-fc36-4812-8f95-bfc23c48b9ab");
        hashMap3.put("toid","4e6ea72c-61c7-429d-80f4-860d1fd14291,");
        hashMap3.put("code","|449|");
        hashMap3.put("type","is_step");

        HashMap hashMap4 = new HashMap();
        hashMap4.put("id","3e6591c8-62df-4565-b36a-4d433b140e62");
        hashMap4.put("toid","a2c7649d-2f47-4a07-9ec9-e08a99b376b0,");
        hashMap4.put("code","");
        hashMap4.put("type","is_one");

        HashMap hashMap5 = new HashMap();
        hashMap5.put("id","4e6ea72c-61c7-429d-80f4-860d1fd14291");
        hashMap5.put("toid","");
        hashMap5.put("code","");
        hashMap5.put("type","is_finish");

        HashMap hashMap6 = new HashMap();
        hashMap6.put("id","684be164-9ca5-4ac4-9dcb-49967f8aff8b");
        hashMap6.put("toid","ef23dd72-e5df-4b25-9b6a-31114cc0a1a3,");
        hashMap6.put("code","|438|");
        hashMap6.put("type","is_step");

        HashMap hashMap7 = new HashMap();
        hashMap7.put("id","6a720323-725b-455b-b937-c059e00347af");
        hashMap7.put("toid","4e6ea72c-61c7-429d-80f4-860d1fd14291,");
        hashMap7.put("code","|449|");
        hashMap7.put("type","is_step");

        HashMap hashMap8 = new HashMap();
        hashMap8.put("id","7265f218-b6e2-44d4-99dc-8c609d9ca36c");
        hashMap8.put("toid","2530e0db-fc36-4812-8f95-bfc23c48b9ab,6a720323-725b-455b-b937-c059e00347af,1d73fca9-7770-4824-accd-a80207f71702,");
        hashMap8.put("code","|362|");
        hashMap8.put("type","is_step");

        HashMap hashMap9 = new HashMap();
        hashMap9.put("id","a2c7649d-2f47-4a07-9ec9-e08a99b376b0");
        hashMap9.put("toid","cd2b43d8-0ff2-4852-84d4-b8a6d2f10e77,");
        hashMap9.put("code","|492|");
        hashMap9.put("type","is_step");

        HashMap hashMap10 = new HashMap();
        hashMap10.put("id","cd2b43d8-0ff2-4852-84d4-b8a6d2f10e77");
        hashMap10.put("toid","684be164-9ca5-4ac4-9dcb-49967f8aff8b,");
        hashMap10.put("code","|489|");
        hashMap10.put("type","is_step");

        HashMap hashMap11 = new HashMap();
        hashMap11.put("id","ef23dd72-e5df-4b25-9b6a-31114cc0a1a3");
        hashMap11.put("toid","7265f218-b6e2-44d4-99dc-8c609d9ca36c,");
        hashMap11.put("code","|413|");
        hashMap11.put("type","is_step");
        list.add(hashMap1);
        list.add(hashMap3);
        list.add(hashMap4);
        list.add(hashMap5);
        list.add(hashMap6);
        list.add(hashMap7);
        list.add(hashMap8);
        list.add(hashMap9);
        list.add(hashMap10);
        list.add(hashMap11);

        String fristId = "";
        for (int i = 0;i<list.size();i++){
            if("is_one".equals(list.get(i).get("type"))){
                fristId = (String) list.get(i).get("id");
                break;
            }
        }
        List<String> listString = new ArrayList<>();
        // 第一个节点id
        String code = "";
        listString.add(fristId);
        for (int i = 0;i<list.size();i++){
            for (int j = 0; j<list.size();j++){
                if(fristId.equals(list.get(j).get("id"))){
                    String toid = list.get(j).get("toid").toString();
                    String[] toids = toid.split(",");
                    listString.addAll(Arrays.asList(toids));
                    fristId=listString.get(listString.size()-1);
                    break;
                }
            }
        }
        for (int i = 0;i<list.size();i++){
            for (int j = 0; j<list.size();j++){
                if(listString.get(i).equals(list.get(j).get("id"))){
                    code += list.get(j).get("code") + ",";
                }
            }
        }
        System.out.println(code);
    }

    @GetMapping("/getrili")
    public void getrili(String day) throws IOException, ParseException {
        Document doc = Jsoup.connect("https://www.rili.com.cn/rili/default/2022/"+day)
                .userAgent("Mozilla")
                .timeout(30000)
                .post();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromdata = simpleDateFormat.parse("2022-"+day.substring(0,2)+"-"+day.substring(2));
        Date qmdata = simpleDateFormat.parse("2022-04-05");
        Date wydata = simpleDateFormat.parse("2022-05-01");
        Date dwdata = simpleDateFormat.parse("2022-06-03");
        Date zqdata = simpleDateFormat.parse("2022-09-10");
        Date gqdata = simpleDateFormat.parse("2022-10-01");
        long from = fromdata.getTime();
        long qm = qmdata.getTime();
        long wy = wydata.getTime();
        long dw = dwdata.getTime();
        long zq = zqdata.getTime();
        long gq = gqdata.getTime();
        int qmday = (int)((qm-from)/(1000*60*60*24));
        int wyday = (int)((wy-from)/(1000*60*60*24));
        int dwday = (int)((dw-from)/(1000*60*60*24));
        int zqday = (int)((zq-from)/(1000*60*60*24));
        int gqday = (int)((gq-from)/(1000*60*60*24));
        String yi = doc.select("#today_yi").html().toString();
        String ji = doc.select("#today_ji").html().toString();


        String today_24jie = doc.select("#today_24jie").html();
        String today_dlleft = doc.select("#today_dlleft").html();
        String today_dlright = doc.select("#today_dlright").html();
        String today_week_id = doc.select("#today_week_id").html();
        String today_nongli = doc.select("#today_nongli").html();
        String month = day.substring(1,2);
        String day1 = day.substring(2);
        String html = "<html>\n" +
                "<head></head>\n" +
                "\t<body>\n" +
                "\t\t<div class=\"wrap\">\n" +
                "\t\t\t<span id=\"today_24jie\">"+today_24jie+"</span>\n" +
                "\t\t\t<div class=\"dayBox\">\n" +
                "\t\t\t\t<span class=\"fl\" id=\"today_dlleft\">"+today_dlleft+"</span>\n" +
                "\t\t\t\t<span class=\"yf\">"+month+"</span>\n" +
                "\t\t\t\t<span class=\"fr\" id=\"today_dlright\">"+today_dlright+"</span>\n" +
                "\t\t\t\t<div class=\"date\" id=\"today_date_id\">\n" +
                "\t\t\t\t\t<span class=\"day\" id=\"today_day\">\n" +
                "\t\t\t\t\t\t<img src=\"./day_"+day1+".png\" alt=\""+day1+"日\" id=\"today_day_img\">\n" +
                "\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t<span class=\"week\" id=\"today_week_id\">"+today_week_id+"</span>\n" +
                "\t\t\t\t\t<span class=\"nongLi\" id=\"today_nongli\">"+today_nongli+"</span>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"yiJi\"><table cellspacing=\"0\" cellpadding=\"0\">\n" +
                "\t\t\t\t<thead><tr><th class=\"thyi\"><div>宜</div></th><th class=\"thji\"><div>忌</div></th></tr></thead>\n" +
                "\t\t\t\t<tbody><tr><td><ul id=\"today_yi\">"+yi+"</ul></td><td><ul id=\"today_ji\">"+ji+"</ul></td></tr></tbody></table>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"btnList\">\n" +
                "\t\t\t\t<span>距清明节"+qmday+"天</span><span>距劳动节"+wyday+"天</span><span>距端午节"+dwday+"天</span><span>距中秋节"+zqday+"天</span><span>距国庆节"+gqday+"天</span>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "\t<style>\n" +
                "\t\t* {\n" +
                "\t\t\tfont-family: \"微软雅黑\",\"苹方\",\"黑体\",Simsun;\n" +
                "\t\t\tborder: none;\n" +
                "\t\t\tmargin: 0px;\n" +
                "\t\t\tpadding: 0px;\n" +
                "\t\t}\n" +
                "\t\t.wrap {\n" +
                "\t\t\twidth: 348px;\n" +
                "\t\t\theight: 619px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tmargin: 0px auto;\n" +
                "\t\t\tbackground-color: #ffffff;\n" +
                "\t\t\tbackground-image: url(./today_bg.png);\n" +
                "\t\t\tbackground-repeat: no-repeat;\n" +
                "\t\t\tbackground-position: center center;\n" +
                "\t\t\tborder: 5px solid #c5ba35;\n" +
                "\t\t}\n" +
                ".yf{\n" +
                "\t\t\tcolor: #cc6600;\n" +
                "\t\t}" +
                "\t\t.wrap>span {\n" +
                "\t\t\tdisplay: block;\n" +
                "\t\t\twidth: 270px;\n" +
                "\t\t\theight: 27px;\n" +
                "\t\t\tmargin: 37px auto 26px;\n" +
                "\t\t\tline-height: 27px;\n" +
                "\t\t\tfont-size: 21px;\n" +
                "\t\t\tfont-weight: bold;\n" +
                "\t\t\tcolor: #cc6600;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\t\t.wrap .dayBox {\n" +
                "\t\t\twidth: 270px;\n" +
                "\t\t\theight: 199px;\n" +
                "\t\t\tmargin: 0px auto;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tposition: relative;\n" +
                "\t\t}\n" +
                "\t   .dayBox>span {\n" +
                "\t\t\tdisplay: inline-block;\n" +
                "\t\t\twidth: 36px;\n" +
                "\t\t\theight: 186px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tfont-family: LiSu,sans-serif;\n" +
                "\t\t\tfont-size: 21px;\n" +
                "\t\t\tline-height: 22px;\n" +
                "\t\t\tcolor: #7e7b77;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                "\t\t.fr {\n" +
                "\t\t\tfloat: right;\n" +
                "\t\t}\n" +
                "\t\t.date span {\n" +
                "\t\t\tdisplay: block;\n" +
                "\t\t}\n" +
                "\t\t.dayBox>span {\n" +
                "\t\t\tdisplay: inline-block;\n" +
                "\t\t\twidth: 36px;\n" +
                "\t\t\theight: 186px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tfont-family: LiSu,sans-serif;\n" +
                "\t\t\tfont-size: 21px;\n" +
                "\t\t\tline-height: 22px;\n" +
                "\t\t\tcolor: #7e7b77;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                "\t\t.date {\n" +
                "\t\t\twidth: 164px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tposition: absolute;\n" +
                "\t\t\ttop: 0px;\n" +
                "\t\t\tleft: 50%;\n" +
                "\t\t\tmargin-left: -84px;\n" +
                "\t\t}\n" +
                "\t\t.day {\n" +
                "\t\t\twidth: 165px;\n" +
                "\t\t\theight: 130px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tfont-family: SimHei,sans-serif;\n" +
                "\t\t\tfont-size: 130px;\n" +
                "\t\t\tcolor: #cc6600;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tline-height: 130px;\n" +
                "\t\t}\n" +
                "\t\t.week {\n" +
                "\t\t\twidth: 165px;\n" +
                "\t\t\theight: 29px;\n" +
                "\t\t\tbackground-image: url(./week_bg.png);\n" +
                "\t\t\tbackground-repeat: no-repeat;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tfont-size: 20px;\n" +
                "\t\t\tcolor: #ffffff;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tline-height: 29px;\n" +
                "\t\t\tmargin-bottom: 6px;\n" +
                "\t\t}\n" +
                "\t\t.nongLi {\n" +
                "\t\t\twidth: 166px;\n" +
                "\t\t\theight: 31px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tfont-size: 18px;\n" +
                "\t\t\tcolor: #cc6600;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tline-height: 31px;\n" +
                "\t\t}\n" +
                "\t\tul {\n" +
                "\t\t\tlist-style: none;\n" +
                "\t\t}\n" +
                "\t\t.yiJi {\n" +
                "\t\t\twidth: 267px;\n" +
                "\t\t\tborder: 1px solid #c60;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t\tmargin: 0px auto;\n" +
                "\t\t}\n" +
                "\t\t.yiJi table {\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t}\n" +
                "\t\t.yiJi td, .yiJi th {\n" +
                "\t\t\tborder: 2px solid #c60;\n" +
                "\t\t}\n" +
                "\t\t.yiJi td {\n" +
                "\t\t\tpadding-left: 14px;\n" +
                "\t\t}\n" +
                "\t\t.yiJi ul {\n" +
                "\t\t\twidth: 100px;\n" +
                "\t\t\theight: 109px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\t\t.yiJi ul li {\n" +
                "\t\t\tfloat: left;\n" +
                "\t\t\twidth: 50px;\n" +
                "\t\t\theight: 27px;\n" +
                "\t\t\tfont-size: 15px;\n" +
                "\t\t\tcolor: #000000;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tline-height: 27px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\t\t.btnList {\n" +
                "\t\t\twidth: 278px;\n" +
                "\t\t\theight: 120px;\n" +
                "\t\t\tmargin: 14px auto 0px;\n" +
                "\t\t\toverflow: hidden;\n" +
                "\t\t}\n" +
                "\t\t.btnList span {\n" +
                "\t\t\tdisplay: inline-block;\n" +
                "\t\t\tfloat: left;\n" +
                "\t\t\twidth: 125px;\n" +
                "\t\t\theight: 31px;\n" +
                "\t\t\tmargin: 0px 7px 9px;\n" +
                "\t\t\tbackground-image: url(./btnlist_bg.png);\n" +
                "\t\t\tbackground-repeat: no-repeat;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tline-height: 31px;\n" +
                "\t\t\tfont-size: 16px;\n" +
                "\t\t\tcolor: #000000;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</html>";
        System.out.println(html);
    }


    //https://m.ishuyin.com/ AI听书网

    // 爱听书  2uxs
    @GetMapping("/getUrl")
    public void getUrl(String pag) throws IOException {
//        for (int i=1;i<=28;i++){
            Document doc = Jsoup.connect("https://www.2uxs.com/youshengxiaoshuo/3232/"+pag+".html")
                    .userAgent("Mozilla")
                    .timeout(30000)
                    .get();
//        Elements pages = doc.select(".hd-sel select option");
//        List<String> listPage = new ArrayList<>();
//        for(Element page:pages){
//            listPage.add("https://www.2uxs.com"+page.val());
//        }


            Elements as = doc.select(".playlist a");
            List<HashMap<String,String>> aList = new ArrayList<>();
            for (Element a : as){
                HashMap<String,String> map = new HashMap<>();
                map.put("url","https://www.2uxs.com"+a.attr("href"));
                map.put("js",a.text());
                aList.add(map);
            }
            for(HashMap<String,String> map:aList){
                Document doc2 = Jsoup.connect(map.get("url"))
                        .userAgent("Mozilla")
                        .timeout(30000)
                        .get();
                String iframe = "https://www.2uxs.com/"+doc2.select("#xplayer").attr("src");

                Document doc3 = Jsoup.connect(iframe)
                        .userAgent("Mozilla")
                        .timeout(30000)
                        .get();
                List<String> doc3List = Arrays.asList(doc3.toString().replace("\n","").replace("\t","").split(";"));
                for(String str : doc3List){
//                    if(-1 != str.indexOf("key")){
//                        String qh = str;
//                        qh = qh.substring(qh.indexOf("key"));
//                        qh = qh.substring(0,qh.indexOf("'})"));
//                        System.out.println("https://mp3-ec.itingshu.net/%E6%81%90%E6%80%96%E6%83%8A%E6%82%9A/%E6%AD%BB%E7%A5%9E%E7%9A%84%E5%93%88%E5%A3%AB%E5%A5%87/%E6%9C%89%E5%A3%B0%E7%9A%84%E7%B4%AB%E8%A5%9F/"+map.get("js")+".mp3?"+qh);
//                    }
                    if(-1 != str.indexOf("mp3:")){
                        String qh = str;
                        qh = qh.substring(qh.indexOf("mp3:"));
                        qh = qh.substring(0,qh.indexOf("'})"));
                        System.out.println(qh);
                        qh = qh.substring(qh.indexOf("/"));

                        System.out.println("https://vkceyugu.cdn.bspapp.com"+qh);
                    }
                }
            }
//        }
        System.out.println("完成+++++++++++++++++++");
    }

    // 56听书
    @GetMapping("/get56Url")
    public void get56Url(String pag) throws IOException {
        for (int n=809;n<=818;n++){
            Document doc = Jsoup.connect("https://www.ting56.com/video/11332-0-"+n+".html")
                    .userAgent("Mozilla")
                    .timeout(30000)
                    .get();
            Elements e = doc.select("script");
            String data = "";
            for(Element el : e){
                String elStr = el.toString();
                if(-1 != elStr.indexOf("FonHen_JieMa")){
                    elStr = elStr.substring(elStr.indexOf("FonHen_JieMa")+14);
                    data = elStr.substring(0,elStr.indexOf("')"));
                }
            }
            String[] url = data.split("[*]");
            String urlDow = "";
            for (String ch:url){
                if (ObjectUtil.isEmpty(ch)) continue;
                int i = Integer.valueOf(ch).intValue();
                urlDow += (char)i;
            }
            downLoadFromUrl(urlDow.split("&")[0],n+".m4a","E:\\迅雷下载");
//            System.out.println("<a href=\""+urlDow.split("&")[0]+"\" download=\""+n+".m4a"+"\">"+n+".m4a</a>");
            System.out.println(n);
        }

    }

    public static String downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(30 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取字节数组
            byte[] getData = readInputStream(inputStream);
            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
// System.out.println("info:"+url+" download success");
            return saveDir + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();

    }




    public static class JsoupParserUtil {

        /**
         * 根据元素id获取HTML元素
         * @param document
         * @param id
         * @return
         */
        public static Element getElementById(Document document,String id)
        {
            Element element=null;
            if(document!=null&&id!=null&&!"".equals(id.trim()))
            {
                element=document.getElementById(id);
            }
            return element;
        }

        /**
         * 根据id获取HTML元素
         * @param element
         * @param id
         * @return
         */
        public static Element getElementById(Element element,String id)
        {
            Element resultElement=null;
            if(element!=null)
            {
                resultElement=element.getElementById(id);
            }
            return resultElement;
        }

        /**
         * 根据元素标签(tagName)获取HTMl元素
         * @param document
         * @param tagName
         * @return
         */
        public static Elements getElementsByTagName(Document document,String tagName)
        {
            Elements elements=null;
            if(document!=null&&tagName!=null&&!"".equals(tagName))
            {
                elements=document.getElementsByTag(tagName);
            }
            return elements;
        }

        /**
         * 根据元素标签(tagName)获取HTMl元素
         * @param element
         * @param tagName
         * @return
         */
        public static Elements getElementsByTagName(Element element,String tagName)
        {
            Elements resultElements=null;
            if(element!=null&&tagName!=null&&!"".equals(tagName))
            {
                resultElements=element.getElementsByTag(tagName);
            }
            return resultElements;
        }

        /**
         * 根据className(样式名称)获取HTML元素集合
         * @param document
         * @param className
         * @return
         */
        public static Elements getElementsByClassName(Document document,String className)
        {
            Elements elements=null;
            if(document!=null&&className!=null&&!"".equals(className.trim()))
            {
                elements=document.getElementsByClass(className);
            }
            return elements;
        }

        /**
         * 根据className(样式名称)获取HTML元素集合
         * @param element
         * @param className
         * @return
         */
        public static Elements getElementsByClassName(Element element,String className)
        {
            Elements resultElements=null;
            if(element!=null&&className!=null&&!"".equals(className))
            {
                resultElements=element.getElementsByClass(className);
            }
            return resultElements;
        }
        /**
         * 根据元素是否具有属性元素key返回元素集合
         * @param document
         * @param attributeNameKey 元素属性key值
         * @return
         */
        public static Elements getElementsByAttributeNameKey(Document document,String attributeNameKey)
        {
            Elements elements=null;
            if(document!=null&&attributeNameKey!=null&&!"".equals(attributeNameKey))
            {
                elements=document.getElementsByAttribute(attributeNameKey);
            }
            return elements;
        }

        /**
         * 根据元素是否具有属性元素key返回元素集合
         * @param element
         * @param attributeNameKey 元素属性key值
         * @return
         */
        public static Elements getElementsByAttributeNameKey(Element element,String attributeNameKey)
        {
            Elements resultElements=null;
            if(element!=null&&attributeNameKey!=null&&!"".equals(attributeNameKey))
            {
                resultElements=element.getElementsByAttribute(attributeNameKey);
            }
            return resultElements;
        }
        /**
         * 根据元素是否具有属性元素key并且key对应的值为value获取元素集合
         * @param document
         * @param attributeKey
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeNameValue(Document document,String attributeKey,String attributeValue)
        {
            Elements elements=null;
            if(document!=null&&attributeKey!=null&&!"".equals(attributeKey.trim())&&attributeValue!=null&&!"".equals(attributeValue.trim()))
            {
                elements=document.getElementsByAttributeValue(attributeKey, attributeValue);
            }
            return elements;
        }
        /**
         * 根据元素是否具有属性元素key并且key对应的值为value获取元素集合
         * @param element
         * @param attributeKey
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeNameValue(Element element,String attributeKey,String attributeValue)
        {
            Elements resultElements=null;
            if(element!=null&&attributeKey!=null&&!"".equals(attributeKey.trim())&&attributeValue!=null&&!"".equals(attributeValue.trim()))
            {
                resultElements=element.getElementsByAttributeValue(attributeKey, attributeValue);
            }
            return resultElements;
        }

        /**
         * 根据属性key值是否以特定字符串开头获取元素集合
         * @param document
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeNameStartWithValue(Document document,String keyValue)
        {
            Elements elements=null;
            if(document!=null&&keyValue!=null&&!"".equals(keyValue.trim()))
            {
                elements=document.getElementsByAttributeStarting(keyValue);
            }
            return elements;
        }
        /**
         * 根据属性key值是否以特定字符串开头获取元素集合
         * @param element
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeNameStartWithValue(Element element,String keyValue)
        {
            Elements elements=null;
            if(element!=null&&keyValue!=null&&!"".equals(keyValue.trim()))
            {
                elements=element.getElementsByAttributeStarting(keyValue);
            }
            return elements;
        }
        /**
         * 根据属性value值是否被包含在某个元素的某个属性中获取元素集合
         * @param document
         * @param containValue
         * @return
         */
        public static Elements getElementsByAttributeValueContaining(Document document,String attributeKey,String containValue)
        {
            Elements elements=null;
            if(document!=null&&containValue!=null&&!"".equals(containValue))
            {
                elements=document.getElementsByAttributeValueContaining(attributeKey, containValue);
            }
            return elements;
        }

        /**
         * 根据属性value值是否被包含在某个元素的某个属性中获取元素集合
         * @param element
         * @param attributeKey
         * @param containValue
         * @return
         */
        public static Elements getElementsByAttributeValueContaining(Element element,String attributeKey,String containValue)
        {
            Elements elements=null;
            if(element!=null&&containValue!=null&&!"".equals(containValue))
            {
                elements=element.getElementsByAttributeValueContaining(attributeKey, containValue);
            }
            return elements;
        }
        /**
         * 根据属性的value值是否以某个字符串结尾获取元素集合
         * @param document
         * @param attributeKey
         * @param valueSuffix
         * @return
         */
        public static Elements getElementsByAttributeValueEnding(Document document,String attributeKey,String valueSuffix)
        {
            Elements elements=null;
            if(document!=null&&attributeKey!=null&&!"".equals(attributeKey)&&valueSuffix!=null&&!"".equals(valueSuffix))
            {
                elements=document.getElementsByAttributeValueEnding(attributeKey, valueSuffix);
            }
            return elements;
        }

        /**
         * 根据属性的value值是否以某个字符串结尾获取元素集合
         * @param element
         * @param attributeKey
         * @param valueSuffix
         * @return
         */
        public static Elements getElementsByAttributeValueEnding(Element element,String attributeKey,String valueSuffix)
        {
            Elements elements=null;
            if(element!=null&&attributeKey!=null&&!"".equals(attributeKey)&&valueSuffix!=null&&!"".equals(valueSuffix))
            {
                elements=element.getElementsByAttributeValueEnding(attributeKey, valueSuffix);
            }
            return elements;
        }
        /**
         * 根据属性值value的正则表达式获取元素集合
         * @param document
         * @param attributeKey
         * @param pattern
         * @return
         */
        public static Elements getElementsByAttributeValueMatching(Document document,String attributeKey,Pattern pattern)
        {
            Elements elements=null;
            if(document!=null&&attributeKey!=null&&!"".equals(attributeKey)&&pattern!=null)
            {
                elements=document.getElementsByAttributeValueMatching(attributeKey, pattern);
            }
            return elements;
        }

        /**
         * 根据属性值value的正则表达式获取元素集合
         * @param element
         * @param attributeKey
         * @param pattern
         * @return
         */
        public static Elements getElementsByAttributeValueMatching(Element element,String attributeKey,Pattern pattern)
        {
            Elements elements=null;
            if(element!=null&&attributeKey!=null&&!"".equals(attributeKey)&&pattern!=null)
            {
                elements=element.getElementsByAttributeValueMatching(attributeKey, pattern);
            }
            return elements;
        }

        /**
         * 根据属性值的value的正则表达式获取元素集合
         * @param document
         * @param attributeKey
         * @param regualRegx
         * @return
         */
        public static Elements getElementsByAttributeValueMatching(Document document,String attributeKey,String regualRegx)
        {
            Elements elements=null;
            if(document!=null&&attributeKey!=null&&!"".equals(attributeKey)&&regualRegx!=null&&!"".equals(regualRegx))
            {
                elements=document.getElementsByAttributeValueMatching(attributeKey, regualRegx);
            }
            return elements;
        }

        /**
         * 根据属性值的value的正则表达式获取元素集合
         * @param element
         * @param attributeKey
         * @param regualRegx
         * @return
         */
        public static Elements getElementsByAttributeValueMatching(Element element,String attributeKey,String regualRegx)
        {
            Elements elements=null;
            if(element!=null&&attributeKey!=null&&!"".equals(attributeKey)&&regualRegx!=null&&!"".equals(regualRegx))
            {
                elements=element.getElementsByAttributeValueMatching(attributeKey, regualRegx);
            }
            return elements;
        }
        /**
         * 返回属性键attributeKey不等于值attributeValue的元素集合
         * @param document
         * @param attributeKey
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeValueNot(Document document,String attributeKey,String attributeValue)
        {
            Elements elements=null;
            if(document!=null&&attributeKey!=null&&!"".equals(attributeKey)&&attributeValue!=null&&!"".equals(attributeValue))
            {
                elements=document.getElementsByAttributeValueNot(attributeKey,attributeValue);
            }
            return elements;
        }

        /**
         * 返回属性键attributeKey不等于值attributeValue的元素集合
         * @param element
         * @param attributeKey
         * @param attributeValue
         * @return
         */
        public static Elements getElementsByAttributeValueNot(Element element,String attributeKey,String attributeValue)
        {
            Elements elements=null;
            if(element!=null&&attributeKey!=null&&!"".equals(attributeKey)&&attributeValue!=null&&!"".equals(attributeValue))
            {
                elements=element.getElementsByAttributeValueNot(attributeKey,attributeValue);
            }
            return elements;
        }
        /**
         * 根据选择器匹配的字符串返回
         * Elements(元素集合)
         * @param document
         * @param selectStr 选择器(类似于JQuery)
         * @return
         */
        public static Elements getMoreElementsBySelectStr(Document document,String selectStr)
        {
            if(document==null||selectStr==null||"".equals(selectStr.trim()))
            {
                return null;
            }
            else
            {
                Elements elements=document.select(selectStr);
                if(elements!=null&&elements.size()>0)
                {
                    return elements;
                }
                else
                {
                    return null;
                }
            }
        }

        /**
         *根据选择器匹配的字符串返回
         * Elements(元素集合)
         * @param element
         * @param selectStr
         * @return
         */
        public static Elements getMoreElementsBySelectStr(Element element,String selectStr)
        {
            if(element==null||selectStr==null||"".equals(selectStr.trim()))
            {
                return null;
            }
            else
            {
                Elements elements=element.select(selectStr);
                if(elements!=null&&elements.size()>0)
                {
                    return elements;
                }
                else
                {
                    return null;
                }
            }
        }
        /**
         * 根据选择器匹配的字符串返回
         * Element(单个元素)
         * @param document
         * @param selectStr 选择器(类似于JQuery)
         * @return
         */
        public static Element getSingleElementBySelectStr(Document document,String selectStr)
        {
            Elements elements=getMoreElementsBySelectStr(document,selectStr);
            if(elements!=null&&elements.size()>0){
                return elements.get(0);
            }
            else
            {
                return null;
            }
        }

        /**
         * 根据选择器匹配的字符串返回
         * Element(单个元素)
         * @param element
         * @param selectStr
         * @return
         */
        public static Element getSingleElementBySelectStr(Element element,String selectStr)
        {
            Elements elements=getMoreElementsBySelectStr(element,selectStr);
            if(elements!=null&&elements.size()>0){
                return elements.get(0);
            }
            else
            {
                return null;
            }
        }
        /**
         * 根据选择器匹配的字符串返回单个元素的Html字符串
         * @param document
         * @param selectStr 选择器(类似于JQuery)
         * @return
         */
        public static String getSingleElementHtmlBySelectStr(Document document,String selectStr)
        {
            Element element=getSingleElementBySelectStr(document,selectStr);
            if(element!=null)
            {
                return element.html();
            }
            else
            {
                return null;
            }
        }

        /**
         * 根据选择器匹配的字符串返回单个元素的Html字符串
         * @param element
         * @param selectStr
         * @return
         */
        public static String getSingleElementHtmlBySelectStr(Element element,String selectStr)
        {
            Element ele=getSingleElementBySelectStr(element,selectStr);
            if(ele!=null)
            {
                return ele.html();
            }
            else
            {
                return null;
            }
        }

        /**
         * 根据元素属性名key获取元素属性名value
         * @param element
         * @param attributeName
         * @return
         */
        public static String getAttributeValue(Element element,String attributeName)
        {
            String attributeValue=null;
            if(element!=null&&attributeName!=null&&!"".equals(attributeName))
            {
                attributeValue=element.attr(attributeName);
            }
            return attributeValue;
        }

        /**
         * 从elements集合中获取element并解析成HTML字符串
         * @param elements
         * @return
         */
        public static String getSingElementHtml(Elements elements){
            Element ele=null;
            String htmlStr=null;
            if(elements!=null&&elements.size()>0)
            {
                ele=elements.get(0);
                htmlStr=ele.html();
            }
            return htmlStr;
        }
        /**
         * 从elements集合中获取element并解析成Text字符串
         * @param elements
         * @return
         */
        public static String getSingElementText(Elements elements){
            Element ele=null;
            String htmlStr=null;
            if(elements!=null&&elements.size()>0)
            {
                ele=elements.get(0);
                htmlStr=ele.text();
            }
            return htmlStr;
        }
        public static void main(String[] args)
        {
            File file=new File("F:/example.htm");
            try {
                Document document=Jsoup.parse(file,"GB2312");
                Pattern pattern= Pattern.compile("");
// document.getElementsByAttributeValueMatching("", pattern);
                Element element=getElementById(document,"personal-uplayer");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字符串与unicode的相互转换工具类
     * @author poterliu
     */
    public static class UnicodeConvertUtil {

        /**
         * 将字符串转成unicode
         * @param str 待转字符串
         * @return unicode字符串
         */
        public static String convert(String str) {
            str = (str == null ? "" : str);
            String tmp;
            StringBuffer sb = new StringBuffer(1000);
            char c;
            int i, j;
            sb.setLength(0);
            for (i = 0; i < str.length(); i++) {
                c = str.charAt(i);
                sb.append("\\u");
                j = (c >>>8); //取出高8位
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
                j = (c & 0xFF); //取出低8位
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);

            }
            return (new String(sb));
        }

        /**
         * 将unicode转成字符串
         * @param str 待转字符串
         * @return 普通字符串
         */
        public static String revert(String str)     {
            str = (str == null ? "" : str);
            if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回
                return str;

            StringBuffer sb = new StringBuffer(1000);

            for (int i = 0; i < str.length() - 6;) {
                String strTemp = str.substring(i, i + 6);
                String value = strTemp.substring(2);
                int c = 0;
                for (int j = 0; j < value.length(); j++) {
                    char tempChar = value.charAt(j);
                    int t = 0;
                    switch (tempChar)
                    {
                        case 'a':
                            t = 10;
                            break;
                        case 'b':
                            t = 11;
                            break;
                        case 'c':
                            t = 12;
                            break;
                        case 'd':
                            t = 13;
                            break;
                        case 'e':
                            t = 14;
                            break;
                        case 'f':
                            t = 15;
                            break;
                        default:
                            t = tempChar - 48;
                            break;
                    }
                    c += t * ((int) Math.pow(16, (value.length() - j - 1)));
                }
                sb.append((char) c);
                i = i + 6;
            }
            return sb.toString();
        }

    }
}


