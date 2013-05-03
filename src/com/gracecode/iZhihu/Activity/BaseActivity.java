package com.gracecode.iZhihu.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import com.gracecode.iZhihu.R;


public abstract class BaseActivity extends Activity {
    protected static ActionBar actionBar;
    protected static Context context;
    protected static SharedPreferences sharedPreferences;
    protected static PackageInfo packageInfo;

    public BaseActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preference:
                Intent intent = new Intent(this, Preference.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_feedback:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.author_email)});
                String subject =
                    String.format(getString(R.string.feedback_title), getString(R.string.app_name), packageInfo.versionName);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(i, getString(R.string.choose_email_client)));
                return true;

            case R.id.menu_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
