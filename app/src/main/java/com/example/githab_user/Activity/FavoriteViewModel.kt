package com.example.githab_user.Activity

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githab_user.Database.UserContract.UserColums.Companion.CONTENT_URI
import com.example.githab_user.data.userItems
import com.example.githab_user.provider.MappingHelper

class FavoriteViewModel:ViewModel() {
   val listUser = MutableLiveData<ArrayList<userItems>>()
   val TAG = "favVM"
   
   fun setFav(context: Context) {
         val cursor = context.contentResolver.query(CONTENT_URI,null,null,null,null)
         val result = MappingHelper.mapCursorToArrayList(cursor)
         Log.d(TAG, "setFav: $result")
         if (result.isNotEmpty()){
            listUser.postValue(result)
         }else{
            Log.d(TAG, "setFav: Data kosong")
         }
       
   }
   fun getFav(): LiveData<ArrayList<userItems>> {
      return listUser
   }


}