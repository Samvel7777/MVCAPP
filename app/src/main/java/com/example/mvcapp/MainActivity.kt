package com.example.mvcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mvcapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiKey = "e31782e89d414efb81d134132222406"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newTemp: String? =  null

        binding.btGet.setOnClickListener {
            binding.apply {
                getWeatherResult(edCity.text.toString())
                tvCity.text = newTemp

            }

        }
    }

    fun getWeatherResult(name: String) {
        val url =
            "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val obj = JSONObject(response)
                val location = obj.getJSONObject("location")
                val current = obj.getJSONObject("current")
                val condition = current.getJSONObject("condition")

                val temp = current.getString("temp_c")
                val localTime = location.getString("localtime")
                val conditionText = condition.getString("text")
                val city = location.getString("name")
                binding.apply {
                    tvCity.text = city
                    tvTemp.text = temp
                    tvDate.text = localTime
                    tvvCondition.text = conditionText
                }
                Log.d("MyLog", "Current: $conditionText")
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
}