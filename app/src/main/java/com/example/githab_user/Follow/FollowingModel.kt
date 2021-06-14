package com.example.githab_user.Follow


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githab_user.BuildConfig
import com.example.githab_user.data.userItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingModel:ViewModel() {
   val listUser = MutableLiveData<ArrayList<userItems>>()
   val TAG = "Following"

   fun setFollowing(username: String) {
      val listItems  = ArrayList<userItems>()
      val url = "https://api.github.com/users/${username}/following"
      Log.d(TAG, "setSearch: $url")
      val client = AsyncHttpClient()
      client.addHeader("Authorization","token ${BuildConfig.API_KEY}")
      client.addHeader("User-Agent","request")
      client.get(url, object : AsyncHttpResponseHandler() {
         override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
            try {
               //parsing json
               val result = String(responseBody)
               val responseObject = JSONArray(result)
               for (i in 0 until responseObject.length()) {
                  val listUser = responseObject.getJSONObject(i)
                  val userItems = userItems()
                  userItems.username = listUser.getString("login")
                  userItems.id = listUser.getInt("id")
                  userItems.ava_url = listUser.getString("avatar_url")

                  listItems.add(userItems)

               }

               listUser.postValue(listItems)
               Log.d(TAG, "onSuccess: $listUser")
            } catch (e: Exception) {
               Log.d("Exception", e.message.toString())
            }
         }

         override fun onFailure(
            statusCode: Int,
            headers: Array<Header>,
            responseBody: ByteArray,
            error: Throwable
         ) {
            Log.d("onFailure", error.message.toString())
         }
      })
   }
   fun getFollowing(): LiveData<ArrayList<userItems>> {
      return listUser
   }


}