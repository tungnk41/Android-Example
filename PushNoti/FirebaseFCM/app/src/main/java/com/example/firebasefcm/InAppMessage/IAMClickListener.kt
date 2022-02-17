package com.example.firebasefcm.InAppMessage

import android.util.Log
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage

class IAMClickListener : FirebaseInAppMessagingClickListener {
    override fun messageClicked(message: InAppMessage, action: Action) {
        val url  = action.actionUrl
        val metaData = message.campaignMetadata
        val dataMap = message.data

        Log.d("TAG", "IAMClicked: " + url + "  , " + metaData?.campaignId + " , " + dataMap)
    }
}