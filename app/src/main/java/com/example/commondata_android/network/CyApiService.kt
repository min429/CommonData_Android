package com.example.commondata_android.network

import com.example.commondata_android.BuildConfig
import com.example.commondata_android.network.responseDTO.AptLttotPblancDetailDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 청약 API
 */
interface CyApiService {

    companion object {
        // 발급받은 서비스 키
        private const val authKey = BuildConfig.APT_API_KEY

    }

    // 주택관리번호, 공고번호, 공고지역코드, 모집공고일 값을 이용하여 APT 분양정보의 상세정보를 제공
    @GET("getAPTLttotPblancDetail")
    fun getAPTLttotPblancDetail(
        @Query("page")
        page: Int = 1,
        @Query("perPage")
        perPage: Int = 10,
        @Query("serviceKey")
        serviceKey: String = authKey
    ) : Call<AptLttotPblancDetailDTO>
}