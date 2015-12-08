
package cchao.org.weatherapp.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherMsg {

    @SerializedName("heweather")
    @Expose
    private Heweather heweather;

    /**
     * 
     * @return
     *     The heweather
     */
    public Heweather getHeweather() {
        return heweather;
    }

    /**
     * 
     * @param heweather
     *     The heweather
     */
    public void setHeweather(Heweather heweather) {
        this.heweather = heweather;
    }

}
