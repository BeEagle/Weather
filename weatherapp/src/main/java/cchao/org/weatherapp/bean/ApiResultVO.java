package cchao.org.weatherapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResultVO {
    /**
     * aqi : {"city":{"aqi":"59","co":"1","no2":"70","o3":"27","pm10":"55","pm25":"41","qlty":"良","so2":"21"}}
     * basic : {"city":"上海","cnty":"中国","id":"CN101020100","lat":"31.213000","lon":"121.445000","update":{"loc":"2015-12-19 09:53","utc":"2015-12-19 01:53"}}
     * daily_forecast : [{"astro":{"sr":"06:47","ss":"16:54"},"cond":{"code_d":"101","code_n":"104","txt_d":"多云","txt_n":"阴"},"date":"2015-12-19","hum":"55","pcpn":"0.0","pop":"0","pres":"1033","tmp":{"max":"11","min":"6"},"vis":"10","wind":{"deg":"94","dir":"东北风","sc":"微风","spd":"8"}},{"astro":{"sr":"06:47","ss":"16:55"},"cond":{"code_d":"104","code_n":"305","txt_d":"阴","txt_n":"小雨"},"date":"2015-12-20","hum":"84","pcpn":"3.4","pop":"89","pres":"1026","tmp":{"max":"14","min":"8"},"vis":"2","wind":{"deg":"132","dir":"东南风","sc":"微风","spd":"3"}},{"astro":{"sr":"06:48","ss":"16:55"},"cond":{"code_d":"104","code_n":"305","txt_d":"阴","txt_n":"小雨"},"date":"2015-12-21","hum":"72","pcpn":"0.3","pop":"63","pres":"1026","tmp":{"max":"13","min":"9"},"vis":"10","wind":{"deg":"74","dir":"北风","sc":"微风","spd":"3"}},{"astro":{"sr":"06:48","ss":"16:56"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2015-12-22","hum":"86","pcpn":"0.7","pop":"71","pres":"1021","tmp":{"max":"13","min":"11"},"vis":"10","wind":{"deg":"294","dir":"东风","sc":"微风","spd":"1"}},{"astro":{"sr":"06:49","ss":"16:56"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2015-12-23","hum":"78","pcpn":"0.5","pop":"57","pres":"1020","tmp":{"max":"11","min":"7"},"vis":"10","wind":{"deg":"359","dir":"北风","sc":"微风","spd":"5"}},{"astro":{"sr":"06:49","ss":"16:57"},"cond":{"code_d":"300","code_n":"101","txt_d":"阵雨","txt_n":"多云"},"date":"2015-12-24","hum":"70","pcpn":"5.8","pop":"56","pres":"1026","tmp":{"max":"7","min":"2"},"vis":"10","wind":{"deg":"6","dir":"北风","sc":"3-4","spd":"13"}},{"astro":{"sr":"06:50","ss":"16:57"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2015-12-25","hum":"49","pcpn":"0.0","pop":"0","pres":"1033","tmp":{"max":"7","min":"2"},"vis":"10","wind":{"deg":"349","dir":"北风","sc":"微风","spd":"3"}}]
     * hourly_forecast : [{"date":"2015-12-19 10:00","hum":"59","pop":"0","pres":"1035","tmp":"11","wind":{"deg":"74","dir":"东北风","sc":"微风","spd":"14"}},{"date":"2015-12-19 13:00","hum":"55","pop":"0","pres":"1033","tmp":"12","wind":{"deg":"90","dir":"东风","sc":"微风","spd":"15"}},{"date":"2015-12-19 16:00","hum":"65","pop":"0","pres":"1032","tmp":"11","wind":{"deg":"87","dir":"东风","sc":"微风","spd":"14"}},{"date":"2015-12-19 19:00","hum":"75","pop":"0","pres":"1032","tmp":"9","wind":{"deg":"79","dir":"东北风","sc":"微风","spd":"12"}},{"date":"2015-12-19 22:00","hum":"79","pop":"0","pres":"1031","tmp":"4","wind":{"deg":"79","dir":"东风","sc":"微风","spd":"10"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"7","hum":"61","pcpn":"0","pres":"1035","tmp":"8","vis":"10","wind":{"deg":"80","dir":"东风","sc":"3-4","spd":"13"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"},"sport":{"brf":"较适宜","txt":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。"},"trav":{"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
     */

    @SerializedName("HeWeather data service 3.0")
    private List<HeWeather> heWeather;

    public List<HeWeather> getHeWeather() {
        return heWeather;
    }

    public void setHeWeather(List<HeWeather> heWeather) {
        this.heWeather = heWeather;
    }

    public static class HeWeather {
        /**
         * city : {"aqi":"59","co":"1","no2":"70","o3":"27","pm10":"55","pm25":"41","qlty":"良","so2":"21"}
         */

        private Aqi aqi;
        /**
         * city : 上海
         * cnty : 中国
         * id : CN101020100
         * lat : 31.213000
         * lon : 121.445000
         * update : {"loc":"2015-12-19 09:53","utc":"2015-12-19 01:53"}
         */

        private Basic basic;
        /**
         * cond : {"code":"101","txt":"多云"}
         * fl : 7
         * hum : 61
         * pcpn : 0
         * pres : 1035
         * tmp : 8
         * vis : 10
         * wind : {"deg":"80","dir":"东风","sc":"3-4","spd":"13"}
         */

        private Now now;
        private String status;
        /**
         * comf : {"brf":"较舒适","txt":"白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。"}
         * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
         * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
         * flu : {"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"}
         * sport : {"brf":"较适宜","txt":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。"}
         * trav : {"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。"}
         * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
         */

        private Suggestion suggestion;
        /**
         * astro : {"sr":"06:47","ss":"16:54"}
         * cond : {"code_d":"101","code_n":"104","txt_d":"多云","txt_n":"阴"}
         * date : 2015-12-19
         * hum : 55
         * pcpn : 0.0
         * pop : 0
         * pres : 1033
         * tmp : {"max":"11","min":"6"}
         * vis : 10
         * wind : {"deg":"94","dir":"东北风","sc":"微风","spd":"8"}
         */

        @SerializedName("daily_forecast")
        private List<DailyForecast> dailyForecast;
        /**
         * date : 2015-12-19 10:00
         * hum : 59
         * pop : 0
         * pres : 1035
         * tmp : 11
         * wind : {"deg":"74","dir":"东北风","sc":"微风","spd":"14"}
         */

        @SerializedName("hourly_forecast")
        private List<HourlyForecast> hourlyForecast;

        public Aqi getAqi() {
            return aqi;
        }

        public void setAqi(Aqi aqi) {
            this.aqi = aqi;
        }

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Suggestion getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecast> getDailyForecast() {
            return dailyForecast;
        }

        public void setDailyForecast(List<DailyForecast> dailyForecast) {
            this.dailyForecast = dailyForecast;
        }

        public List<HourlyForecast> getHourlyForecast() {
            return hourlyForecast;
        }

        public void setHourlyForecast(List<HourlyForecast> hourlyForecast) {
            this.hourlyForecast = hourlyForecast;
        }

        public static class Aqi {
            /**
             * aqi : 59
             * co : 1
             * no2 : 70
             * o3 : 27
             * pm10 : 55
             * pm25 : 41
             * qlty : 良
             * so2 : 21
             */

            private City city;

            public City getCity() {
                return city;
            }

            public void setCity(City city) {
                this.city = city;
            }

            public static class City {
                private String aqi;
                private String co;
                private String no2;
                private String o3;
                private String pm10;
                private String pm25;
                private String qlty;
                private String so2;

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }
            }
        }

        public static class Basic {
            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            /**
             * loc : 2015-12-19 09:53
             * utc : 2015-12-19 01:53
             */

            private Update update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public Update getUpdate() {
                return update;
            }

            public void setUpdate(Update update) {
                this.update = update;
            }

            public static class Update {
                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class Now {
            /**
             * code : 101
             * txt : 多云
             */

            private Cond cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            /**
             * deg : 80
             * dir : 东风
             * sc : 3-4
             * spd : 13
             */

            private Wind wind;

            public Cond getCond() {
                return cond;
            }

            public void setCond(Cond cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public static class Cond {
                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Wind {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class Suggestion {
            /**
             * brf : 较舒适
             * txt : 白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。
             */

            private Comf comf;
            /**
             * brf : 较适宜
             * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
             */

            private Cw cw;
            /**
             * brf : 较冷
             * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
             */

            private Drsg drsg;
            /**
             * brf : 较易发
             * txt : 天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。
             */

            private Flu flu;
            /**
             * brf : 较适宜
             * txt : 天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。
             */

            private Sport sport;
            /**
             * brf : 适宜
             * txt : 天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。
             */

            private Trav trav;
            /**
             * brf : 最弱
             * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
             */

            private Uv uv;

            public Comf getComf() {
                return comf;
            }

            public void setComf(Comf comf) {
                this.comf = comf;
            }

            public Cw getCw() {
                return cw;
            }

            public void setCw(Cw cw) {
                this.cw = cw;
            }

            public Drsg getDrsg() {
                return drsg;
            }

            public void setDrsg(Drsg drsg) {
                this.drsg = drsg;
            }

            public Flu getFlu() {
                return flu;
            }

            public void setFlu(Flu flu) {
                this.flu = flu;
            }

            public Sport getSport() {
                return sport;
            }

            public void setSport(Sport sport) {
                this.sport = sport;
            }

            public Trav getTrav() {
                return trav;
            }

            public void setTrav(Trav trav) {
                this.trav = trav;
            }

            public Uv getUv() {
                return uv;
            }

            public void setUv(Uv uv) {
                this.uv = uv;
            }

            public static class Comf {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Cw {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Drsg {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Flu {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Sport {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Trav {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Uv {
                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
        }

        public static class DailyForecast {
            /**
             * sr : 06:47
             * ss : 16:54
             */

            private Astro astro;
            /**
             * code_d : 101
             * code_n : 104
             * txt_d : 多云
             * txt_n : 阴
             */

            private Cond cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            /**
             * max : 11
             * min : 6
             */

            private Tmp tmp;
            private String vis;
            /**
             * deg : 94
             * dir : 东北风
             * sc : 微风
             * spd : 8
             */

            private Wind wind;

            public Astro getAstro() {
                return astro;
            }

            public void setAstro(Astro astro) {
                this.astro = astro;
            }

            public Cond getCond() {
                return cond;
            }

            public void setCond(Cond cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public Tmp getTmp() {
                return tmp;
            }

            public void setTmp(Tmp tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public static class Astro {
                private String sr;
                private String ss;

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class Cond {
                @SerializedName("code_d")
                private String codeD;
                @SerializedName("code_n")
                private String codeN;
                @SerializedName("txt_d")
                private String txtD;
                @SerializedName("txt_n")
                private String txtN;

                public String getCodeD() {
                    return codeD;
                }

                public void setCodeD(String codeD) {
                    this.codeD = codeD;
                }

                public String getCodeN() {
                    return codeN;
                }

                public void setCodeN(String codeN) {
                    this.codeN = codeN;
                }

                public String getTxtD() {
                    return txtD;
                }

                public void setTxtD(String txtD) {
                    this.txtD = txtD;
                }

                public String getTxtN() {
                    return txtN;
                }

                public void setTxtN(String txtN) {
                    this.txtN = txtN;
                }
            }

            public static class Tmp {
                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class Wind {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class HourlyForecast {
            private String date;
            private String hum;
            private String pop;
            private String pres;
            private String tmp;
            /**
             * deg : 74
             * dir : 东北风
             * sc : 微风
             * spd : 14
             */

            private Wind wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public static class Wind {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}
