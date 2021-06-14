package com.example.githab_user.Setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.githab_user.Activity.MainActivity
import com.example.githab_user.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver:BroadcastReceiver() {
    companion object{
        const val ID_REPEATING = 123
        const val REPEAT_TIME = "09:00"
        const val TITLE = "Github Social"
        const val MESSAGE = "Ayo segera kunjungi github social kamu"
        private const val TIME_FORMAT = "HH:mm"
    }
    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
        
    }
    private fun showAlarmNotification(context: Context){
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
    
        val intent = Intent(context,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, ID_REPEATING, intent, 0)
    
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setAutoCancel(true)
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)
        
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
        
            builder.setChannelId(CHANNEL_ID)
        
            notificationManagerCompat.createNotificationChannel(channel)
         }
        val notification = builder.build()
        notificationManagerCompat.notify(ID_REPEATING, notification)
    }
    fun setRepeatingAlarm(context: Context) {
        
        // Validasi inputan waktu terlebih dahulu
        if (isDateInvalid(REPEAT_TIME, TIME_FORMAT)) return
        
        val setMessageRepeat = R.string.set_repeating_alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        
        val timeArray = REPEAT_TIME.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        
        Toast.makeText(context, setMessageRepeat, Toast.LENGTH_SHORT).show()
    }
    fun cancelAlarm(context: Context) {
        val cancelAlarmMessage = R.string.cancel_alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        pendingIntent.cancel()
        
        alarmManager.cancel(pendingIntent)
        
        Toast.makeText(context, cancelAlarmMessage, Toast.LENGTH_SHORT).show()
    }
    
    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }
}