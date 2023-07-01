package com.example.commondata_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commondata_android.databinding.ActivityMainBinding
import com.example.commondata_android.network.CyApiService
import com.example.commondata_android.network.RetrofitClient
import com.example.commondata_android.network.responseDTO.AptLttotPblancDetailDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var cyListAdapter: CyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()

    }

    private fun initView() {

        cyListAdapter = CyListAdapter()
        binding.rvCyList.apply {
            adapter = cyListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }

        binding.tvBtnSearch.setOnClickListener {
            requestAptCyList()
        }

    }


    private fun requestAptCyList() {
        val cyListService = RetrofitClient.getInstance().create(CyApiService::class.java)
        val listCall = cyListService.getAPTLttotPblancDetail()

        listCall.enqueue(object : Callback<AptLttotPblancDetailDTO> {
            override fun onResponse(
                call: Call<AptLttotPblancDetailDTO>,
                response: Response<AptLttotPblancDetailDTO>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    cyListAdapter?.let {
                        it.setAptListItem(response.body()?.data ?: mutableListOf())
                    }

                } else {
                    Toast.makeText(this@MainActivity, "response Code : " + response.code() + "\n발급받은 서비스 키를 새로이 입력하세요.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<AptLttotPblancDetailDTO>, t: Throwable) {
                // 실패
                Log.e("onFail", t.stackTrace.toString());

            }

        })
    }
}