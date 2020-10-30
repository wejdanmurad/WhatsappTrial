package com.example.whatsapptrial;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
//        String url = "https://chat.whatsapp.com/ItdiMbcI7jd5cq4G2kNlZ3";
//        intentWhatsapp.setData(Uri.parse(url));
//        intentWhatsapp.setPackage("com.whatsapp");
//        startActivity(intentWhatsapp);


        String toNumber = "+972597727313"; // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "");

        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "wejdan " + getResources().getString(R.string.whatsapp_suffix));
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

        if (!isAccessibilityOn(this, WhatsappAccessibilityService.class)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }


//////////////////////////////////////////////////////////////////////////////////////

//        openWhatsAppTrial();
//        trial1();
//        trial2();
//        trial3();
//        openWhatsApp();
//        trial4();
//        openWhatsAppTrial2();


//        //This class provides applications access to the content model.
//        ContentResolver cr = getContentResolver();
//
//        //RowContacts for filter Account Types
//        // ContactsContract.Contacts.markAsContacted()
//        Cursor contactCursor = cr.query(
//                ContactsContract.RawContacts.CONTENT_URI,
//                new String[]{ContactsContract.RawContacts._ID,
//                        ContactsContract.RawContacts.CONTACT_ID},
//                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
//                new String[]{"com.whatsapp"},
//                null);
//
//        //ArrayList for Store Whatsapp Contact
//        ArrayList<String> myWhatsappContacts = new ArrayList<>();
//
//        if (contactCursor != null) {
//            if (contactCursor.getCount() > 0) {
//                if (contactCursor.moveToFirst()) {
//                    do {
//                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
//                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
//
//                        if (whatsappContactId != null) {
//                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
//                            Cursor whatsAppContactCursor = cr.query(
//                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
//                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
//                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                                    new String[]{whatsappContactId}, null);
//
//                            if (whatsAppContactCursor != null) {
//                                whatsAppContactCursor.moveToFirst();
//                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//                                String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                                whatsAppContactCursor.close();
//
//                                //Add Number to ArrayList
//                                myWhatsappContacts.add(number);
//
//                                System.out.println(" WhatsApp contact id  :  " + id);
//                                System.out.println(" WhatsApp contact name :  " + name);
//                                System.out.println(" WhatsApp contact number :  " + number);
//                            }
//                        }
//                    } while (contactCursor.moveToNext());
//                    contactCursor.close();
//                }
//            }
//        }
//
//        System.out.println(" WhatsApp contact size :  " + myWhatsappContacts.size());
//
    }


    private boolean isAccessibilityOn(Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName() + "/" + clazz.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {
        }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString(settingValue);
                while (colonSplitter.hasNext()) {
                    String accessibilityService = colonSplitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    private void openWhatsAppTrial() {
        String smsNumber = "00972597727313"; // E164 format without '+' sign
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Error/n", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(sendIntent);
    }

    private void trial1() {
        String smsNumber = "00972597727313";
        Uri uri = Uri.parse("smsto:" + smsNumber);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        i.putExtra("sms_body", "wejdan trying to send from another app");
        i.putExtra("jid", smsNumber + "@s.whatsapp.net");
        i.setPackage("com.whatsapp");
        if (i.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "Error/n", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(i);
    }

    private void trial2() {
// the bestttttttttttttttttt sooooooooooo faaaaaaaaaaaar
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+972597727313" + "&text=" + URLEncoder.encode("wejdan trying to send from another app", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trial3() {
        // works

        String toNumber = "+972597727313"; // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "");

        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "wejdan ");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void openWhatsApp() {
        // did not work
        String smsNumber = "00972597727313";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void trial4() {
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setAction(Intent.ACTION_VIEW);
        sendIntent.setPackage("com.whatsapp");
        String url = "https://api.whatsapp.com/send?phone=" + "+972597727313" + "&text=" + "your message";
        sendIntent.setData(Uri.parse(url));
        if(sendIntent.resolveActivity(getPackageManager()) != null){
            startActivity(sendIntent);
        }

    }

    //    +972597727313
    private void openWhatsAppTrial2() {
        String mPhoneNumber = "+972597727313";
        String mMessage = "Hello world";
        String mSendToWhatsApp = "https://wa.me/" + mPhoneNumber + "?text=" + mMessage;
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(
                        mSendToWhatsApp
                )));
    }

}