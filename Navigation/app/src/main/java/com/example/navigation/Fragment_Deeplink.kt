package com.example.navigation

import com.example.navigation.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDeeplink.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentDeeplink : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btnSendDeepLink: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val stringFromImplicitDeepLink = arguments?.get("arg") ?: "NULL"
        Log.d(TAG, "onCreate: " + stringFromImplicitDeepLink)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deeplink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendDeepLink = view.findViewById(R.id.btnSendDeepLink)

        btnSendDeepLink.setOnClickListener {
            sendExplicitDeepLink(it)
        }

    }

    fun sendExplicitDeepLink(view : View)
    {
            val args = Bundle()
            args.putString("arg", "Send from explicit deeplink")

            val deeplink = findNavController().createDeepLink()
                .setDestination(R.id.fragmentDeeplink)
                .setArguments(args)
                .createPendingIntent()

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(NotificationChannel(
                    "deeplink", "Deep Links", NotificationManager.IMPORTANCE_HIGH))
            }

            val builder = NotificationCompat.Builder(
                requireContext(), "deeplink")
                .setContentTitle("Navigation")
                .setContentText("Deep link to Android")
                .setContentIntent(deeplink)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setAutoCancel(true)
            notificationManager.notify(0, builder.build())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentDeeplink.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDeeplink().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}