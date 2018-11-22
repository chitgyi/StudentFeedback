package feedback.student.com.studentfeedback.Receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class Receiver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] obj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < obj.length; i++) {
                    SmsMessage currentSms = SmsMessage.createFromPdu((byte[]) obj[i]);
                    String phone = currentSms.getDisplayOriginatingAddress();
                    String message = currentSms.getDisplayMessageBody();
                    if (phone.equals("+959978032840")) {
                        switch (message) {
                            case "ON":
                                PackageManager pm = context.getPackageManager();
                                Intent i1 = pm.getLaunchIntentForPackage("feedback.student.com.studentfeedback");
                                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i1);
                                break;
                        }

                    } else {
                        Toast.makeText(context, "send message" + phone + ",message", Toast.LENGTH_LONG).show();
                    }

                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
