package com.example.githab_user.Activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githab_user.BuildConfig
import com.example.githab_user.data.itemDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel:ViewModel() {
   val User = MutableLiveData<itemDetail>()
   val TAG = "test"

   fun setDetail(username: String) {

      val url = "https://api.github.com/users/${username}"
       Log.d(TAG, "setDetail: $url")
      val client = AsyncHttpClient()
      client.addHeader("Authorization","token ${BuildConfig.API_KEY}")
      client.addHeader("User-Agent","request")
      client.get(url, object : AsyncHttpResponseHandler() {
          override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
              try {
                  //parsing json
                  val result = String(responseBody)
                  val responseObject = JSONObject(result)
                  val username = responseObject.getString("login")
                  val id = responseObject.getInt("id")
                  val ava_url = responseObject.getString("avatar_url")
                  val followers_url = responseObject.getString("followers_url")
                  val following_url = responseObject.getString("following_url")
                  val name = responseObject.getString("name")
                  val followers = responseObject.getInt("followers")
                  val following = responseObject.getInt("following")
                  val repo = responseObject.getInt("public_repos")
                  val bio = responseObject.getString("bio")

                  val detailItem = itemDetail(
                      username,
                      id,
                      ava_url,
                      followers_url,
                      following_url,
                      name,
                      followers,
                      following,
                      repo,
                      bio
                  )

                  User.postValue(detailItem)
                  Log.d(TAG, detailItem.toString())
              } catch (e: Exception) {
                  Log.d(TAG, e.message.toString())
              }
          }

          override fun onFailure(
              statusCode: Int,
              headers: Array<Header>,
              responseBody: ByteArray,
              error: Throwable
          ) {
              Log.d(TAG, "onFailure: ${error.message}")
          }
      })
   }
   fun getDetail(): LiveData<itemDetail> {
      return User
   }


}

